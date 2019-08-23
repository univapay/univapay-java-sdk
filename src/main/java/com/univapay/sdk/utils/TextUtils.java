package com.univapay.sdk.utils;

import java.util.regex.Pattern;

public class TextUtils {
  public static boolean isDigitsOnly(CharSequence str) {
    final int len = str.length();
    for (int i = 0; i < len; i++) {
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns true if the string is null or 0-length.
   *
   * @param str the string to be examined
   * @return true if str is null or zero length
   */
  public static boolean isEmpty(CharSequence str) {
    return (str == null || str.length() == 0);
  }

  /**
   * Helper function to verify that domains associated with application tokens satisfy the
   * requirements of the API.
   *
   * @param domainString
   * @return true if the provided string satisfies the acceptable domains regex, and false
   *     otherwise.
   */
  public static boolean URLRegexVerifier(String domainString) {
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\-\\_\\.]*$");
    return pattern.matcher(domainString).matches();
  }
}
