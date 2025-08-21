package com.univapay.sdk.types;

import static org.junit.jupiter.api.Assertions.fail;

import com.univapay.sdk.models.common.EmailAddress;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class EmailAddressValidatorTest {

  private static final List<String> INVALID_EMAIL_ADDRESSES =
      Arrays.asList(
          "”(),:;<>[\\]@example.com",
          "just”not”right@example.com",
          "this\\ is\"really\"not\\allowed@example.com",
          "plainaddress",
          "#@%^%#$@#$@#.com",
          "@example.com",
          "Joe Smith <email@example.com>",
          "email.example.com",
          "email@example@example.com",
          ".email@example.com",
          "email.@example.com",
          "email..email@example.com",
          "あいうえお@example.com",
          "email@example.com (Joe Smith)",
          "email@example",
          "email@-example.com",
          "email@example..com",
          "Abc..123@example.com",
          "much.”more\\ unusual”@example.com",
          "very.unusual.”@”.unusual.com@example.com",
          "very.”(),:;<>[]”.VERY.”very@\\ \"very”.unusual@strange.example.com",
          "Joe Smith <email@domain.com>",
          "email.domain.com",
          "email@domain@domain.com",
          ".email@domain.com",
          "email.@domain.com",
          "email..email@domain.com",
          "あいうえお@domain.com",
          "email@domain.com (Joe Smith)",
          "email@domain",
          "email@-domain.com",
          "email@domain..com",
          "",
          null);

  @Test
  void shouldThrownIllegalArgumentException() {
    for (String email : INVALID_EMAIL_ADDRESSES) {
      try {
        new EmailAddress(email);
      } catch (IllegalArgumentException ignored) {
        continue;
      }
      fail("Email should be invalid but passed: " + email);
    }
  }
}
