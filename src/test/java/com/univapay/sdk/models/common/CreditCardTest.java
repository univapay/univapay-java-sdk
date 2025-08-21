package com.univapay.sdk.models.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.univapay.sdk.types.CardBrand;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

class CreditCardTest {

  @Data
  @AllArgsConstructor
  private class TestCase {

    private String inputCardNumber;
    private CardBrand expectedCardBrand;
  }

  @Test
  void shouldBeAbleToParseMostCardBrands() {

    // This feature will be removed in a future date

    List<TestCase> testCases =
        Arrays.asList(
            new TestCase("4242424242424242", CardBrand.VISA),
            new TestCase("5555555555554444", CardBrand.MASTERCARD),
            new TestCase("371449635398431", CardBrand.AMERICAN_EXPRESS),
            new TestCase("30569309025904", CardBrand.DINERS_CLUB),
            new TestCase("3530111333300000", CardBrand.JCB),
            new TestCase("6759649826438453", CardBrand.MAESTRO),
            new TestCase("6210946888140006", CardBrand.UNIONPAY),
            new TestCase("6011111111111117", CardBrand.DISCOVER),
            new TestCase("1111111111111111", CardBrand.UNKNOWN));

    testCases.forEach(
        testCase -> {
          CardBrand result = CardBrand.forCardNumber(testCase.inputCardNumber);

          assertEquals(testCase.expectedCardBrand, result, "Test: " + testCase.inputCardNumber);
        });
  }
}
