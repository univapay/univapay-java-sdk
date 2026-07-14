#!/usr/bin/env bash
#
# Automated release script for univapay-java-sdk.
#
# Flow:
#   1. Cut release/vX.Y.Z from origin/develop
#   2. Bump version in build.gradle, README.md, README.en.md
#   3. Commit "Release X.Y.Z", push, open PR into master
#   4. Wait for CI checks to pass, merge with a merge commit
#   5. Create GitHub release vX.Y.Z targeting master (creates the tag,
#      which triggers the CircleCI publish job -> Maven Central)
#   6. Merge master back into develop via PR (satisfies branch rulesets;
#      no bypass permission needed)
#   7. Wait for the CircleCI publish job on the tag to succeed
#      (Maven Central upload)
#
# The script is resumable: if a release PR for the computed version already
# exists (open or merged), re-running continues from where it left off.
#
# Usage:
#   ./scripts/release.sh [patch|minor|major|X.Y.Z] [--dry-run] [--yes]
#
#   patch|minor|major   bump the version from build.gradle on origin/develop
#                       (default: patch)
#   X.Y.Z               release an explicit version instead
#   --dry-run           create the branch and bump files locally, show the
#                       diff, then roll everything back; nothing is pushed
#   --yes               skip the confirmation prompt
#
# Requires: git, perl, gh (authenticated; not needed for --dry-run)

set -euo pipefail

info() { printf '\033[1;34m==>\033[0m %s\n' "$*"; }
die()  { printf '\033[1;31merror:\033[0m %s\n' "$*" >&2; exit 1; }

# --- argument parsing -------------------------------------------------------

BUMP="patch"
DRY_RUN=0
ASSUME_YES=0

for arg in "$@"; do
  case "$arg" in
    patch|minor|major) BUMP="$arg" ;;
    [0-9]*.[0-9]*.[0-9]*) BUMP="$arg" ;;
    --dry-run) DRY_RUN=1 ;;
    --yes|-y) ASSUME_YES=1 ;;
    -h|--help)
      sed -n '2,28p' "$0" | sed 's/^# \{0,1\}//'
      exit 0
      ;;
    *) die "unknown argument: $arg (see --help)" ;;
  esac
done

# --- preflight ---------------------------------------------------------------

command -v git >/dev/null || die "git is required"
command -v perl >/dev/null || die "perl is required"

REPO_ROOT="$(git -C "$(dirname "$0")" rev-parse --show-toplevel)"
cd "$REPO_ROOT"

if [ "$DRY_RUN" -eq 0 ]; then
  command -v gh >/dev/null || die "gh CLI is required (https://cli.github.com)"
  gh auth status >/dev/null 2>&1 || die "gh is not authenticated; run 'gh auth login'"
fi

[ -z "$(git status --porcelain --untracked-files=no)" ] || die "working tree has uncommitted changes; commit or stash first"

info "Fetching origin..."
git fetch origin --prune --quiet

CURRENT="$(git show origin/develop:build.gradle | perl -ne "print \$1 if /^version = '([^']+)'/")"
[ -n "$CURRENT" ] || die "could not read version from build.gradle on origin/develop"

case "$BUMP" in
  patch|minor|major)
    [[ "$CURRENT" =~ ^([0-9]+)\.([0-9]+)\.([0-9]+)$ ]] \
      || die "current version '$CURRENT' is not X.Y.Z; pass an explicit version instead"
    MAJOR="${BASH_REMATCH[1]}" MINOR="${BASH_REMATCH[2]}" PATCH="${BASH_REMATCH[3]}"
    case "$BUMP" in
      patch) NEW="$MAJOR.$MINOR.$((PATCH + 1))" ;;
      minor) NEW="$MAJOR.$((MINOR + 1)).0" ;;
      major) NEW="$((MAJOR + 1)).0.0" ;;
    esac
    ;;
  *)
    [[ "$BUMP" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]] || die "invalid version '$BUMP' (expected X.Y.Z)"
    NEW="$BUMP"
    ;;
esac

TAG="v$NEW"
BRANCH="release/$TAG"

# Existing tag only blocks a fresh release; resume paths (release created but
# back-merge/publish verification still pending) must get past this.
TAG_EXISTS=0
[ -n "$(git ls-remote --tags origin "$TAG")" ] && TAG_EXISTS=1
if [ "$DRY_RUN" -eq 1 ] && [ "$TAG_EXISTS" -eq 1 ]; then
  die "tag $TAG already exists on origin — is $NEW already released?"
fi

info "Release: $CURRENT -> $NEW  (branch $BRANCH, PR into master, tag $TAG)"

if [ "$DRY_RUN" -eq 0 ] && [ "$ASSUME_YES" -eq 0 ]; then
  read -r -p "Proceed? [y/N] " reply
  [[ "$reply" =~ ^[Yy]$ ]] || die "aborted"
fi

# --- helpers ------------------------------------------------------------------

bump_files() {
  perl -pi -e "s/^version = '[^']*'/version = '$NEW'/" build.gradle
  for f in README.md README.en.md; do
    [ -f "$f" ] || continue
    # <version> inside the com.univapay dependency block
    perl -0777 -pi -e "s{(<artifactId>univapay-java-sdk</artifactId>\s*\n\s*<version>)[^<]+(</version>)}{\${1}$NEW\${2}}g" "$f"
    # javadoc.io links: .../univapay-java-sdk/X.Y.Z
    perl -pi -e "s{(univapay-java-sdk/)[0-9][0-9A-Za-z.-]*}{\${1}$NEW}g" "$f"
    # gradle-style coordinates: com.univapay:univapay-java-sdk:X.Y.Z
    perl -pi -e "s{(com\.univapay:univapay-java-sdk:)[0-9][0-9A-Za-z.-]*}{\${1}$NEW}g" "$f"
  done

  grep -q "version = '$NEW'" build.gradle || die "failed to bump build.gradle"
  for f in README.md README.en.md; do
    [ -f "$f" ] || continue
    grep -q "$NEW" "$f" || die "failed to bump $f"
  done
}

pr_state() {
  gh pr list --head "$BRANCH" --base master --state "$1" --json number --jq '.[0].number' 2>/dev/null
}

watch_and_merge() {
  local pr="$1"
  info "Waiting for CI checks on PR #$pr..."
  local n=0
  for _ in $(seq 1 60); do
    n="$(gh pr view "$pr" --json statusCheckRollup --jq '.statusCheckRollup | length')"
    [ "${n:-0}" -gt 0 ] && break
    sleep 10
  done
  [ "${n:-0}" -gt 0 ] || die "no CI checks appeared on PR #$pr after 10 minutes"

  gh pr checks "$pr" --watch --fail-fast \
    || die "CI failed on PR #$pr — fix the branch and re-run this script to resume"

  info "CI green. Merging PR #$pr into master (merge commit)..."
  gh pr merge "$pr" --merge --delete-branch \
    || die "merge failed — merge PR #$pr manually, then re-run this script to resume"
}

create_release() {
  info "Creating GitHub release $TAG on master (tag push triggers Maven Central publish)..."
  git fetch origin master --quiet
  gh release create "$TAG" --target master --title "$TAG" --generate-notes
  local repo
  repo="$(gh repo view --json nameWithOwner --jq .nameWithOwner)"
  info "Publish job: https://app.circleci.com/pipelines/github/$repo"
}

# Echoes the state of the CircleCI publish job on the given commit:
# success | failure | pending | unknown. CircleCI reports either commit
# statuses ("ci/circleci: publish") or check runs depending on integration.
publish_state() {
  local repo="$1" sha="$2" s
  # the combined-status endpoint can briefly return duplicate entries for the
  # same context; take the newest one
  s="$(gh api "repos/$repo/commits/$sha/status" \
        --jq '[.statuses[] | select(.context | test("publish"))] | sort_by(.created_at) | last | .state // empty' 2>/dev/null)"
  if [ -z "$s" ]; then
    s="$(gh api "repos/$repo/commits/$sha/check-runs" \
          --jq '.check_runs[] | select(.name | test("publish")) | if .status != "completed" then "pending" else .conclusion end' 2>/dev/null | head -1)"
  fi
  echo "${s:-unknown}"
}

wait_for_publish() {
  local repo sha state waited=0 interval=20 timeout=1800
  repo="$(gh repo view --json nameWithOwner --jq .nameWithOwner)"
  sha="$(gh api "repos/$repo/commits/$TAG" --jq .sha)"
  [ -n "$sha" ] || die "could not resolve tag $TAG to a commit"

  info "Waiting for CircleCI publish job on $TAG ($sha)..."
  while :; do
    state="$(publish_state "$repo" "$sha")"
    case "$state" in
      success)
        info "Publish job succeeded — artifact uploaded to Maven Central."
        return 0
        ;;
      failure|error|cancelled|timed_out)
        die "publish job finished with '$state' — inspect and re-run the workflow on CircleCI:
  https://app.circleci.com/pipelines/github/$repo
then verify with: ./scripts/release.sh $NEW   (resumes at the publish wait)"
        ;;
      *)
        # pending/unknown: tag pipeline runs spotless + test before publish,
        # so the publish status can take a few minutes to appear
        ;;
    esac
    waited=$((waited + interval))
    if [ "$waited" -ge "$timeout" ]; then
      die "timed out after $((timeout / 60)) min waiting for the publish job — check:
  https://app.circleci.com/pipelines/github/$repo
then verify with: ./scripts/release.sh $NEW   (resumes at the publish wait)"
    fi
    sleep "$interval"
  done
}

back_merge() {
  git fetch origin --quiet
  if git merge-base --is-ancestor origin/master origin/develop; then
    info "develop already contains master; no back-merge needed."
    return 0
  fi

  # Back-merge goes through a PR so the develop ruleset ("changes must be made
  # through a pull request") is satisfied without bypass permissions.
  local pr
  pr="$(gh pr list --head master --base develop --state open --json number --jq '.[0].number' 2>/dev/null)"
  if [ -z "$pr" ]; then
    info "Opening back-merge PR master -> develop..."
    gh pr create --base develop --head master --title "Merge master back into develop (release $NEW)" \
      --body "Automated back-merge after release \`$NEW\`."
    pr="$(gh pr list --head master --base develop --state open --json number --jq '.[0].number')"
  else
    info "Found existing back-merge PR #$pr; reusing it."
  fi
  [ -n "$pr" ] || die "back-merge PR was created but could not be found; merge master into develop via PR manually"

  # CI already ran on the master merge commit; only watch if checks are reported.
  local n
  n="$(gh pr view "$pr" --json statusCheckRollup --jq '.statusCheckRollup | length')"
  if [ "${n:-0}" -gt 0 ]; then
    gh pr checks "$pr" --watch --fail-fast \
      || die "CI failed on back-merge PR #$pr — investigate, then merge it manually"
  fi

  info "Merging back-merge PR #$pr into develop (merge commit)..."
  gh pr merge "$pr" --merge \
    || die "merge of PR #$pr failed (required approvals or conflicts?) — merge it manually on GitHub"

  git checkout -q develop && git pull --ff-only --quiet origin develop || true
  info "develop is up to date with master."
}

# --- main ---------------------------------------------------------------------

if [ "$DRY_RUN" -eq 1 ]; then
  ORIG_REF="$(git rev-parse --abbrev-ref HEAD)"
  info "[dry-run] Creating $BRANCH from origin/develop and bumping files..."
  git checkout -q -B "$BRANCH" origin/develop
  bump_files
  echo
  git --no-pager diff
  echo
  info "[dry-run] Rolling back (nothing was committed or pushed)."
  git checkout -q -- .
  git checkout -q "$ORIG_REF"
  git branch -q -D "$BRANCH"
  exit 0
fi

# Resume support: pick up from whatever already exists for this version.
if gh release view "$TAG" >/dev/null 2>&1; then
  info "GitHub release $TAG already exists; skipping to back-merge."
  back_merge
  wait_for_publish
elif merged_pr="$(pr_state merged)" && [ -n "$merged_pr" ]; then
  info "Release PR #$merged_pr already merged; resuming at release creation."
  create_release
  back_merge
  wait_for_publish
elif open_pr="$(pr_state open)" && [ -n "$open_pr" ]; then
  info "Found open release PR #$open_pr; resuming at CI watch."
  watch_and_merge "$open_pr"
  create_release
  back_merge
  wait_for_publish
else
  [ "$TAG_EXISTS" -eq 0 ] \
    || die "tag $TAG already exists on origin but no release PR or GitHub release was found — is $NEW already released?"

  info "Creating $BRANCH from origin/develop..."
  git checkout -B "$BRANCH" origin/develop
  bump_files
  git --no-pager diff --stat
  git commit -aqm "Release $NEW"
  git push -u origin "$BRANCH"

  info "Opening PR into master..."
  gh pr create --base master --head "$BRANCH" --title "Release $NEW" \
    --body "Automated release PR for version \`$NEW\`.

After merge, the \`$TAG\` GitHub release is created on \`master\`; the tag push triggers the CircleCI publish job (Maven Central)."
  pr="$(pr_state open)"
  [ -n "$pr" ] || die "PR was created but could not be found; re-run this script to resume"

  watch_and_merge "$pr"
  create_release
  back_merge
  wait_for_publish
fi

info "Release $NEW complete: published to Maven Central. Artifact: https://central.sonatype.com/artifact/com.univapay/univapay-java-sdk"
