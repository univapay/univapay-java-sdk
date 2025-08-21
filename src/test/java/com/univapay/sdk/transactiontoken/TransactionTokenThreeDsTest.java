package com.univapay.sdk.transactiontoken;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.CreditCard;
import com.univapay.sdk.models.common.threeDs.TransactionToken3dsData;
import com.univapay.sdk.models.common.threeDs.TransactionToken3dsStatus;
import com.univapay.sdk.models.response.IssuerToken;
import com.univapay.sdk.models.response.PaymentError;
import com.univapay.sdk.models.response.transactiontoken.CardPaymentData;
import com.univapay.sdk.models.response.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.*;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TransactionTokenThreeDsTest extends GenericTest {

  // Three DS related testing
  // When creating a recurring token, is possible to execute a three ds check (card registration)
  // - When the transaction token's three ds is enabled
  // - When the store/merchant configuration is set to require the three ds check

  @Test
  void shouldBeAbleToEnableProcessThreeDs() throws Exception {

    // Given a merchant & store
    // With the credit card information

    int currentState = 1;

    String createTransactionToken =
        JsonLoader.loadJson("/requests/transactiontoken/post-card-with-three-ds-enabled.json");
    String createTransactionTokenResponse =
        JsonLoader.loadJson("/responses/transactiontoken/post-card-three-ds.json");
    stubFor(
        post(urlPathEqualTo("/tokens"))
            .withRequestBody(equalToJson(createTransactionToken))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs(STARTED)
            .willReturn(okJson(createTransactionTokenResponse))
            .willSetStateTo("00" + currentState));

    String awaitingThreeDsResponse =
        JsonLoader.loadJson("/responses/transactiontoken/post-card-three-ds-awaiting.json");

    String threeDsIssuerTokenResponse =
        JsonLoader.loadJson("/responses/issuerToken/get-issuer-token-three-ds-token.json");
    for (int i = 0; i < 3; i++) {

      stubFor(
          get(urlPathEqualTo(
                  "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a"))
              .inScenario("Transaction Token Three DS")
              .whenScenarioStateIs("00" + currentState)
              .willReturn(okJson(createTransactionTokenResponse))
              .willSetStateTo("00" + (++currentState)));
    }

    stubFor(
        get(urlPathEqualTo(
                "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a"))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs("00" + currentState)
            .willReturn(okJson(awaitingThreeDsResponse))
            .willSetStateTo("00" + (++currentState)));

    stubFor(
        get(urlPathEqualTo(
                "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a/three_ds/issuer_token"))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs("00" + currentState)
            .willReturn(okJson(threeDsIssuerTokenResponse)));

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      // For three ds telephone and email is required
      PhoneNumber phoneNumber = new PhoneNumber(81, "00000000");

      CreditCard creditCard =
          new CreditCard("full name", "4556137309615276", 12, 2025, "599")
              // Set the transaction's three ds as enabled
              // Set the redirection url back to the merchant site to process the check completion
              .withThreeDsConfiguration(true, "https://my-website/three-3ds-example-callback")
              .addPhoneNumber(phoneNumber);

      TransactionTokenWithData createdTransactionToken =
          univapay
              .createTransactionToken(
                  "test@univapay.com", creditCard, TransactionTokenType.RECURRING)
              .dispatch();

      boolean threeDsEnabled =
          Optional.ofNullable(createdTransactionToken.getData())
              .map(PaymentData::asCardPaymentData)
              .map(CardPaymentData::getThreeDs)
              .map(TransactionToken3dsData::getEnabled)
              .orElse(false);

      if (threeDsEnabled) {
        TransactionTokenWithData transactionToken =
            univapay
                .transactionToken3dsAwaitingMonitor(
                    createdTransactionToken.getStoreId(), createdTransactionToken.getId())
                .await();

        TransactionToken3dsStatus transactionToken3dsStatus =
            Optional.ofNullable(transactionToken.getData())
                .map(PaymentData::asCardPaymentData)
                .map(CardPaymentData::getThreeDs)
                .map(TransactionToken3dsData::getStatus)
                .orElse(null);

        // You can fetch the issuer token when becomes awaiting (might be failed if not possible to
        // execute the ThreeDS check)
        assertEquals(TransactionToken3dsStatus.AWAITING, transactionToken3dsStatus);

        // Fetch the issuer token (the three ds validation website)
        IssuerToken issuerToken =
            univapay
                .getIssuerTokenThreeDsToken(transactionToken.getStoreId(), transactionToken.getId())
                .dispatch();

        assertEquals(CallMethod.HTTP_POST, issuerToken.getCallMethod());
        Map<String, String> payload = new HashMap<>();
        payload.put("resource_id", "11efdeb4-8e17-56de-82bd-f38742139a7a");
        payload.put("resource_type", "transaction_token");

        assertEquals(payload, issuerToken.getPayload());
        assertEquals(
            "https://api.univapay.com/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/three_ds/issuer_token/test/response",
            issuerToken.getIssuerToken());

        // Now is up to the developer to create a landing page with the issuer token
        // After the user finish the three ds check, continue with the processing at the redirect
      } else {

        // Regular processing

        // But for this testing this is a failure
        fail("This is an unexpected state");
      }
    }
  }

  @Test
  void shouldBeAbleToProcessThreeDsWhenRequired() throws Exception {

    int currentState = 1;

    String createTransactionToken =
        JsonLoader.loadJson("/requests/transactiontoken/post-card-with-three-ds-unset.json");
    String createTransactionTokenResponse =
        JsonLoader.loadJson("/responses/transactiontoken/post-card-three-ds.json");
    stubFor(
        post(urlPathEqualTo("/tokens"))
            .withRequestBody(equalToJson(createTransactionToken))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs(STARTED)
            .willReturn(okJson(createTransactionTokenResponse))
            .willSetStateTo("00" + currentState));

    String awaitingThreeDsResponse =
        JsonLoader.loadJson("/responses/transactiontoken/post-card-three-ds-awaiting.json");

    String threeDsIssuerTokenResponse =
        JsonLoader.loadJson("/responses/issuerToken/get-issuer-token-three-ds-token.json");
    for (int i = 0; i < 3; i++) {

      stubFor(
          get(urlPathEqualTo(
                  "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a"))
              .inScenario("Transaction Token Three DS")
              .whenScenarioStateIs("00" + currentState)
              .willReturn(okJson(createTransactionTokenResponse))
              .willSetStateTo("00" + (++currentState)));
    }

    stubFor(
        get(urlPathEqualTo(
                "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a"))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs("00" + currentState)
            .willReturn(okJson(awaitingThreeDsResponse))
            .willSetStateTo("00" + (++currentState)));

    stubFor(
        get(urlPathEqualTo(
                "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a/three_ds/issuer_token"))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs("00" + currentState)
            .willReturn(okJson(threeDsIssuerTokenResponse)));

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      // For three ds telephone and email is required
      PhoneNumber phoneNumber = new PhoneNumber(81, "00000000");

      CreditCard creditCard =
          new CreditCard("full name", "4556137309615276", 12, 2025, "599")
              // Set the redirection url back to the merchant site to process the check completion
              .withThreeDsConfiguration("https://my-website/three-3ds-example-callback")
              .addPhoneNumber(phoneNumber);

      TransactionTokenWithData createdTransactionToken =
          univapay
              .createTransactionToken(
                  "test@univapay.com", creditCard, TransactionTokenType.RECURRING)
              .dispatch();

      boolean threeDsEnabled =
          Optional.ofNullable(createdTransactionToken.getData())
              .map(PaymentData::asCardPaymentData)
              .map(CardPaymentData::getThreeDs)
              .map(TransactionToken3dsData::getEnabled)
              .orElse(false);

      if (threeDsEnabled) {
        TransactionTokenWithData transactionToken =
            univapay
                .transactionToken3dsAwaitingMonitor(
                    createdTransactionToken.getStoreId(), createdTransactionToken.getId())
                .await();

        TransactionToken3dsStatus transactionToken3dsStatus =
            Optional.ofNullable(transactionToken.getData())
                .map(PaymentData::asCardPaymentData)
                .map(CardPaymentData::getThreeDs)
                .map(TransactionToken3dsData::getStatus)
                .orElse(null);

        // You can fetch the issuer token when becomes awaiting (might be failed if not possible to
        // execute the ThreeDS check)
        assertEquals(TransactionToken3dsStatus.AWAITING, transactionToken3dsStatus);

        // Fetch the issuer token (the three ds validation website)
        IssuerToken issuerToken =
            univapay
                .getIssuerTokenThreeDsToken(transactionToken.getStoreId(), transactionToken.getId())
                .dispatch();

        assertEquals(CallMethod.HTTP_POST, issuerToken.getCallMethod());
        Map<String, String> payload = new HashMap<>();
        payload.put("resource_id", "11efdeb4-8e17-56de-82bd-f38742139a7a");
        payload.put("resource_type", "transaction_token");

        assertEquals(payload, issuerToken.getPayload());
        assertEquals(
            "https://api.univapay.com/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/three_ds/issuer_token/test/response",
            issuerToken.getIssuerToken());

        // Now is up to the developer to create a landing page with the issuer token
        // After the user finish the three ds check, continue with the processing at the redirect
      } else {

        // Regular processing

        // But for this testing this is a failure
        fail("This is an unexpected state");
      }
    }
  }

  @Test
  void shouldBeAbleToDeferThreeDsToTheCharge() throws Exception {

    //
    int currentState = 1;

    String createTransactionToken =
        JsonLoader.loadJson(
            "/requests/transactiontoken/post-card-with-three-ds-no-parameters.json");
    String createTransactionTokenResponse =
        JsonLoader.loadJson("/responses/transactiontoken/post-card-three-ds-disabled.json");
    stubFor(
        post(urlPathEqualTo("/tokens"))
            .withRequestBody(equalToJson(createTransactionToken))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs(STARTED)
            .willReturn(okJson(createTransactionTokenResponse))
            .willSetStateTo("00" + currentState));

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      // For three ds telephone and email is required
      PhoneNumber phoneNumber = new PhoneNumber(81, "00000000");

      CreditCard creditCard =
          new CreditCard("full name", "4556137309615276", 12, 2025, "599")
              // Set the redirection url back to the merchant site to process the check completion
              .addPhoneNumber(phoneNumber);

      TransactionTokenWithData createdTransactionToken =
          univapay
              .createTransactionToken(
                  "test@univapay.com", creditCard, TransactionTokenType.RECURRING)
              .dispatch();

      boolean threeDsEnabled =
          Optional.ofNullable(createdTransactionToken.getData())
              .map(PaymentData::asCardPaymentData)
              .map(CardPaymentData::getThreeDs)
              .map(TransactionToken3dsData::getEnabled)
              .orElse(false);

      assertFalse(threeDsEnabled);
    }
  }

  @Test
  void shouldBeAbleToParseThreeDsErrors() throws Exception {

    // Given a merchant & store
    // With the credit card information

    int currentState = 1;

    String createTransactionToken =
        JsonLoader.loadJson("/requests/transactiontoken/post-card-with-three-ds-enabled.json");
    String createTransactionTokenResponse =
        JsonLoader.loadJson("/responses/transactiontoken/post-card-three-ds.json");
    stubFor(
        post(urlPathEqualTo("/tokens"))
            .withRequestBody(equalToJson(createTransactionToken))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs(STARTED)
            .willReturn(okJson(createTransactionTokenResponse))
            .willSetStateTo("00" + currentState));

    String failedThreeDs =
        JsonLoader.loadJson("/responses/transactiontoken/post-card-three-ds-failed.json");

    for (int i = 0; i < 3; i++) {

      stubFor(
          get(urlPathEqualTo(
                  "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a"))
              .inScenario("Transaction Token Three DS")
              .whenScenarioStateIs("00" + currentState)
              .willReturn(okJson(createTransactionTokenResponse))
              .willSetStateTo("00" + (++currentState)));
    }

    stubFor(
        get(urlPathEqualTo(
                "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/tokens/11efdeb4-8e17-56de-82bd-f38742139a7a"))
            .inScenario("Transaction Token Three DS")
            .whenScenarioStateIs("00" + currentState)
            .willReturn(okJson(failedThreeDs))
            .willSetStateTo("00" + (++currentState)));

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      // For three ds telephone and email is required
      PhoneNumber phoneNumber = new PhoneNumber(81, "00000000");

      CreditCard creditCard =
          new CreditCard("full name", "4556137309615276", 12, 2025, "599")
              // Set the transaction's three ds as enabled
              // Set the redirection url back to the merchant site to process the check completion
              .withThreeDsConfiguration(true, "https://my-website/three-3ds-example-callback")
              .addPhoneNumber(phoneNumber);

      TransactionTokenWithData createdTransactionToken =
          univapay
              .createTransactionToken(
                  "test@univapay.com", creditCard, TransactionTokenType.RECURRING)
              .dispatch();

      boolean threeDsEnabled =
          Optional.ofNullable(createdTransactionToken.getData())
              .map(PaymentData::asCardPaymentData)
              .map(CardPaymentData::getThreeDs)
              .map(TransactionToken3dsData::getEnabled)
              .orElse(false);

      if (threeDsEnabled) {
        TransactionTokenWithData transactionToken =
            univapay
                .transactionToken3dsAwaitingMonitor(
                    createdTransactionToken.getStoreId(), createdTransactionToken.getId())
                .await();

        TransactionToken3dsStatus transactionToken3dsStatus =
            Optional.ofNullable(transactionToken.getData())
                .map(PaymentData::asCardPaymentData)
                .map(CardPaymentData::getThreeDs)
                .map(TransactionToken3dsData::getStatus)
                .orElse(null);

        assertEquals(TransactionToken3dsStatus.FAILED, transactionToken3dsStatus);

        PaymentError error =
            Optional.ofNullable(transactionToken.getData())
                .map(PaymentData::asCardPaymentData)
                .map(CardPaymentData::getThreeDs)
                .map(TransactionToken3dsData::getPaymentError)
                .orElse(null);

        assertNotNull(error);

        assertEquals(327, error.getCode().longValue());
        assertEquals("Confirmation period has expired", error.getMessage());
        assertNull(error.getDetail());
      } else {

        // Regular processing

        // But for this testing this is a failure
        fail("This is an unexpected state");
      }
    }
  }
}
