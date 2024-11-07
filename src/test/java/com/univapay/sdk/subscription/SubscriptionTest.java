package com.univapay.sdk.subscription;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.request.subscription.FixedCyclePaymentPlan;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

public class SubscriptionTest extends GenericTest {

  // While other tests in this suite tests a single operation
  // This tests group multiple requests

  @Test
  public void handleInstalmentBasedCharges()
      throws UnivapayException, IOException, InterruptedException, TimeoutException {

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    // This get ready the subscription stubs for the actual test
    FullSubscription createdSubscription = generateScenario(univapay);

    // This is a simple while loop until there is items,
    // set how many attempts it will do until gives up
    // each iteration of the loop, wait a second using Thread.sleep()

    int maxRetries = 10;
    PaginatedList<Charge> listOfCharges =
        univapay
            .listSubscriptionCharges(createdSubscription.getStoreId(), createdSubscription.getId())
            .dispatch();

    int currentAttempt = 0;
    while (listOfCharges.getItems().isEmpty()) {
      listOfCharges =
          univapay
              .listSubscriptionCharges(
                  createdSubscription.getStoreId(), createdSubscription.getId())
              .dispatch();

      if (currentAttempt++ > maxRetries) {
        fail("Something went wrong, after 10 seconds list still being empty is unexpected");
      }

      Thread.sleep(Duration.ofSeconds(1));
    }

    //

    Charge charge = null;
    try {
      charge = listOfCharges.getItems().getFirst();
    } catch (NoSuchElementException e) {
      fail("The list was empty, but it was expected have at least one item");
    }

    assertEquals(ChargeStatus.SUCCESSFUL, charge.getStatus());
  }

  @Test
  public void handleInstalmentBasedChargesWithRxJava3()
      throws UnivapayException, IOException, InterruptedException, TimeoutException {
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    // This get ready the subscription stubs for the actual test
    FullSubscription createdSubscription = generateScenario(univapay);

    // Is possible to use other libraries, like rxjava3 or mutiny

    Observable<PaginatedList<Charge>> source =
        new Observable<PaginatedList<Charge>>() {

          @Override
          protected void subscribeActual(
              @NonNull Observer<? super PaginatedList<Charge>> observer) {

            // UnivapayCallback should be only when integrating with async libraries

            univapay
                .listSubscriptionCharges(
                    createdSubscription.getStoreId(), createdSubscription.getId())
                .dispatch(
                    new UnivapayCallback<PaginatedList<Charge>>() {
                      @Override
                      public void getResponse(PaginatedList<Charge> response) {
                        observer.onNext(response);
                        observer.onComplete();
                      }

                      @Override
                      public void getFailure(Throwable error) {
                        observer.onError(error);
                      }
                    });
          }
        };

    // This retries 10 times, waiting 1 second each interval
    // The filter discard empty results, take(1) get the first result from upstream
    // blockingGet() to block the thread and await for the  PaginatedList<Charge>

    try {

      Charge charge =
          Observable.intervalRange(0, 10, 0, 1, TimeUnit.SECONDS)
              .flatMap(interval -> source)
              .filter(result -> !result.getItems().isEmpty())
              .map(list -> list.getItems().getFirst())
              .take(1)
              .singleOrError()
              .blockingGet();

      assertEquals(ChargeStatus.SUCCESSFUL, charge.getStatus());

    } catch (NoSuchElementException e) {
      fail("Something went wrong, after 10 seconds list still being empty is unexpected");
    } catch (IllegalArgumentException e) {
      // See singleOrError documentation
      // This is when it emmit two items
      // since take(1) is before singleOrError, this should never happen
      fail("Something went wrong, check the code");
    } catch (RuntimeException e) {
        // rxjava3 and other reactive programming libraries loose the type at the error channel

        Throwable cause = e.getCause();
        if(cause instanceof UnivapayException) {
           UnivapayException univapayException =  (UnivapayException) cause;

           fail("HTTP error: " + univapayException);
        }else{
            fail("Unexpected error! " + e);
        }

        //rxjava3 wrap the exceptions into a runtime exception

    }
  }

  @NotNull
  private FullSubscription generateScenario(UnivapaySDK univapay)
      throws IOException, UnivapayException, InterruptedException, TimeoutException {
    // Create a subscription
    String createSubscriptionInstalmentRequest =
        JsonLoader.loadJson("/subscription-test/001/create-subscription.json");
    String createSubscriptionInstallmentResponse =
        JsonLoader.loadJson("/subscription-test/001/create-subscription-response.json");

    MockRRGenerator mockRRGenerator = new MockRRGenerator();

    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/subscriptions",
        jwt,
        201,
        createSubscriptionInstallmentResponse,
        createSubscriptionInstalmentRequest);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId(UUID.fromString("565f4106-ee07-4853-a4a0-df810e7ce9a7"));

    Map<String, Object> subscriptionMetadata = new HashMap<>();
    subscriptionMetadata.put("test-key", "123456");

    // Dispatch throw IOException or UnivapayException, not relevant for the test, but in the
    // production code
    // surround with try catch and handle the subscription creation failure accordingly
    FullSubscription createdSubscription =
        univapay
            .createSubscription(
                transactionTokenId, MoneyLike.of("JPY", 10000), SubscriptionPeriod.MONTHLY)
            .withInstallmentPlan(new FixedCyclePaymentPlan(3))
            .withMetadata(subscriptionMetadata)
            .dispatch();

    assertEquals(SubscriptionStatus.UNVERIFIED, createdSubscription.getStatus());

    String subscriptionGetResponse =
        JsonLoader.loadJson("/subscription-test/001/get-subscription-response.json");

    String subscriptionGetExpectedPath =
        "/stores/"
            + createdSubscription.getStoreId()
            + "/subscriptions/"
            + createdSubscription.getId();

    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", subscriptionGetExpectedPath + "?polling=true", jwt, 200, subscriptionGetResponse);

    ResourceMonitor<FullSubscription> subscriptionCompletionMonitor =
        univapay.subscriptionCompletionMonitor(
            createdSubscription.getStoreId(), createdSubscription.getId());

    FullSubscription subscription = subscriptionCompletionMonitor.await();

    // At this point the subscription can be deemed paid
    assertEquals(SubscriptionStatus.COMPLETED, subscription.getStatus());
    assertEquals(BigInteger.valueOf(0), subscription.getAmountLeft());

    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", subscriptionGetExpectedPath + "?polling=true", jwt, 200, subscriptionGetResponse);

    // Look up the charges until there is a charge

    // API will return an empty list once or twice
    // After a while, the API should return the charge

    LoginJWTStrategy loginToken = new LoginJWTStrategy(jwt);

    String emptyList = JsonLoader.loadJson("/subscription-test/001/empty-list.json");

    String listChargesForSubscription = subscriptionGetExpectedPath + "/charges";
    String scenario = "List Subscription's Charges";

    stubFor(
        getStub(
            loginToken,
            listChargesForSubscription,
            "GET",
            scenario,
            Scenario.STARTED,
            null,
            emptyList,
            "001",
            200));

    stubFor(
        getStub(
            loginToken,
            listChargesForSubscription,
            "GET",
            scenario,
            "001",
            null,
            emptyList,
            "002",
            200));

    String chargesList = JsonLoader.loadJson("/subscription-test/001/get-charges-list.json");

    stubFor(
        getStub(
            loginToken,
            listChargesForSubscription,
            "GET",
            scenario,
            "002",
            null,
            chargesList,
            null,
            200));
    return createdSubscription;
  }

  private MappingBuilder getStub(
      LoginJWTStrategy jwt,
      String route,
      String method,
      String scenario,
      String state,
      String request,
      String response,
      String nextState,
      int status) {

    ResponseDefinitionBuilder responseBuilder =
        aResponse()
            .withStatus(status)
            .withHeader("Content-Type", "application/json")
            .withHeader("Content-Length", String.valueOf(response.length()))
            .withBody(response);

    MappingBuilder builder;
    if ("GET".equals(method)) {

      builder = get(urlEqualTo(route));
    } else {
      builder = post(urlEqualTo(route)).withRequestBody(equalToJson(request));
    }

    ScenarioMappingBuilder stub =
        builder
            .withHeader("Authorization", containing(jwt.getAuthHeader().getValue()))
            .inScenario(scenario)
            .whenScenarioStateIs(state)
            .willReturn(responseBuilder);

    if (nextState != null) {
      stub.willSetStateTo(nextState);
    }

    return stub;
  }
}
