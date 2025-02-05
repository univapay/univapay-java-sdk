package com.univapay.sdk.subscription;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.TestCase.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsMode;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.request.subscription.FixedCyclePaymentPlan;
import com.univapay.sdk.models.response.IssuerToken;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

public class SubscriptionThreeDsTest extends GenericTest {

  // Three DS related testing
  // When creating a subscription, if the payment method is credit card
  // If trigger the three ds flow, the subscription status end up being unverified
  // So the subscription processing need to check the charge for the awaiting status
  // Then pick keep processing as "charge"
  // After the three ds is complete, then the subscription status
  // will be updated accordingly upon charge completion

  // For subscription with initial amount as 0, there aren't any charges

  @Test
  public void shouldBeAbleToEnableProcessThreeDs()
      throws IOException, UnivapayException, InterruptedException, TimeoutException {

    // With a Transaction Token that the three ds is disabled

    // Create Subscription

    String createSubscriptionRequest =
        JsonLoader.loadJson("/requests/subscription/subscription-infinite.json");
    String createSubscriptionResponse =
        JsonLoader.loadJson("/responses/subscription/subscription-infinite-unverified.json");
    stubFor(
        post(urlPathEqualTo("/subscriptions"))
            .withRequestBody(equalToJson(createSubscriptionRequest))
            .willReturn(okJson(createSubscriptionResponse)));

    // List Charges for Subscription

    String listChargesForSubscription =
        JsonLoader.loadJson("/responses/subscription/charges/subscription-infinite-charges.json");
    String storesPath = "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6";
    stubFor(
        get(urlPathEqualTo(
                storesPath + "/subscriptions/11efe2c6-fc23-5c48-a27d-4f855f8bc050/charges"))
            .willReturn(okJson(listChargesForSubscription)));

    // Charge Monitor (GET /charges)
    String getChargeResponse =
        JsonLoader.loadJson(
            "/responses/subscription/charges/subscription-infinite-charge-awaiting.json");
    stubFor(
        get(urlPathEqualTo(storesPath + "/charges/11efe2c6-fd73-57c4-ae43-6b473b249515"))
            .willReturn(okJson(getChargeResponse)));

    String getChargeIssuerToken =
        JsonLoader.loadJson(
            "/responses/subscription/charges/subscription-infinite-charge-issuer.json");
    stubFor(
        get(urlPathEqualTo(
                storesPath + "/charges/11efe2c6-fd73-57c4-ae43-6b473b249515/three_ds/issuer_token"))
            .willReturn(okJson(getChargeIssuerToken)));

    // Test starts here
    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      FullSubscription subscription =
          univapay
              .createSubscription(
                  new TransactionTokenId("11efe209-2fb9-9f86-ac12-db6b95569fc9"),
                  new MoneyLike(BigInteger.valueOf(1000), "JPY"),
                  SubscriptionPeriod.MONTHLY)
              .withThreeDs("https://website.com/subscription-three-ds-callback-as-example")
              .withStartOn(LocalDate.of(2025, 2, 10))
              .withInitialAmount(BigInteger.valueOf(250))
              .dispatch();

      assertEquals(SubscriptionStatus.UNVERIFIED, subscription.getStatus());

      PaginatedList<Charge> listOfCharges =
          univapay
              .listSubscriptionCharges(subscription.getStoreId(), subscription.getId())
              .dispatch();

      // The initial amount != 0 will indicate that there are charges
      assertEquals(BigInteger.valueOf(250), subscription.getInitialAmount());
      // This route is eventually consistent, details are on ListSubscriptionChargesTest
      Charge chargeFromSubscription = listOfCharges.getItems().get(0);

      Charge charge =
          univapay
              .chargeCompletionMonitor(
                  chargeFromSubscription.getStoreId(), chargeFromSubscription.getId())
              .await();

      assertEquals(ChargeStatus.AWAITING, charge.getStatus());
      assertEquals(
          subscription.getThreeDs().getRedirectId().toUUID(),
          charge.getThreeDS().getRedirectId().toUUID());

      IssuerToken issuerToken =
          univapay.getIssuerTokenThreeDsCharge(charge.getStoreId(), charge.getId()).dispatch();

      // Execute the redirection to the landing page with the issuer token information

      assertEquals(
          "https://api.gyro-n.money/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/three_ds/issuer_token/test/response",
          issuerToken.getIssuerToken());
      assertEquals(CallMethod.HTTP_POST, issuerToken.getCallMethod());

      Map<String, String> payload = new HashMap<>();
      payload.put("resource_id", "11efe2c6-fd73-57c4-ae43-6b473b249515");
      payload.put("resource_type", "charge");

      assertEquals(payload, issuerToken.getPayload());
    }
  }

  @Test
  public void shouldBeAbleToProcessFiniteSubscription()
      throws IOException, UnivapayException, InterruptedException, TimeoutException {

    // Create Subscription

    String createSubscriptionRequest =
        JsonLoader.loadJson("/requests/subscription/subscription-finite.json");
    String createSubscriptionResponse =
        JsonLoader.loadJson("/responses/subscription/subscription-finite-unverified.json");
    stubFor(
        post(urlPathEqualTo("/subscriptions"))
            .withRequestBody(equalToJson(createSubscriptionRequest))
            .willReturn(okJson(createSubscriptionResponse)));

    // List Charges for Subscription

    String listChargesForSubscription =
        JsonLoader.loadJson("/responses/subscription/charges/subscription-finite-charges.json");
    String storesPath = "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6";
    stubFor(
        get(urlPathEqualTo(
                storesPath + "/subscriptions/11efe2d1-b782-1358-b3e0-d7b500e13963/charges"))
            .willReturn(okJson(listChargesForSubscription)));

    // Charge Monitor (GET /charges)
    String getChargeResponse =
        JsonLoader.loadJson(
            "/responses/subscription/charges/subscription-finite-charge-awaiting.json");
    stubFor(
        get(urlPathEqualTo(storesPath + "/charges/11efe2d1-b88e-f31a-a57f-b3f1359e6592"))
            .willReturn(okJson(getChargeResponse)));

    String getChargeIssuerToken =
        JsonLoader.loadJson(
            "/responses/subscription/charges/subscription-finite-charge-issuer.json");
    stubFor(
        get(urlPathEqualTo(
                storesPath + "/charges/11efe2d1-b88e-f31a-a57f-b3f1359e6592/three_ds/issuer_token"))
            .willReturn(okJson(getChargeIssuerToken)));

    // Test starts here
    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      FullSubscription subscription =
          univapay
              .createSubscription(
                  new TransactionTokenId("11efe209-2fb9-9f86-ac12-db6b95569fc9"),
                  new MoneyLike(BigInteger.valueOf(1000), "JPY"),
                  SubscriptionPeriod.MONTHLY)
              .withThreeDs("https://website.com/subscription-three-ds-callback-as-example")
              .withStartOn(LocalDate.of(2025, 2, 10))
              .withSubscriptionPlan(new FixedCyclePaymentPlan(3))
              .withInitialAmount(BigInteger.valueOf(250))
              .dispatch();

      assertEquals(SubscriptionStatus.UNVERIFIED, subscription.getStatus());

      PaginatedList<Charge> listOfCharges =
          univapay
              .listSubscriptionCharges(subscription.getStoreId(), subscription.getId())
              .dispatch();

      // The initial amount != 0 will indicate that there are charges
      assertEquals(BigInteger.valueOf(250), subscription.getInitialAmount());
      // This route is eventually consistent, details are on SubscriptionTest
      Charge chargeFromSubscription = listOfCharges.getItems().get(0);

      Charge charge =
          univapay
              .chargeCompletionMonitor(
                  chargeFromSubscription.getStoreId(), chargeFromSubscription.getId())
              .await();

      assertEquals(ChargeStatus.AWAITING, charge.getStatus());
      assertEquals(
          subscription.getThreeDs().getRedirectId().toUUID(),
          charge.getThreeDS().getRedirectId().toUUID());

      IssuerToken issuerToken =
          univapay.getIssuerTokenThreeDsCharge(charge.getStoreId(), charge.getId()).dispatch();

      // Execute the redirection to the landing page with the issuer token information

      assertEquals(
          "https://api.gyro-n.money/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/three_ds/issuer_token/test/response",
          issuerToken.getIssuerToken());
      assertEquals(CallMethod.HTTP_POST, issuerToken.getCallMethod());

      Map<String, String> payload = new HashMap<>();
      payload.put("resource_id", "11efe2d1-b88e-f31a-a57f-b3f1359e6592");
      payload.put("resource_type", "charge");

      assertEquals(payload, issuerToken.getPayload());
    }
  }

  @Test
  public void shouldBeAbleToProcessInstallmentBasedSubscription()
      throws IOException, UnivapayException, InterruptedException, TimeoutException {

    // Create Subscription

    String createSubscriptionRequest =
        JsonLoader.loadJson("/requests/subscription/subscription-finite.json");
    String createSubscriptionResponse =
        JsonLoader.loadJson("/responses/subscription/subscription-finite-unverified.json");
    stubFor(
        post(urlPathEqualTo("/subscriptions"))
            .withRequestBody(equalToJson(createSubscriptionRequest))
            .willReturn(okJson(createSubscriptionResponse)));

    // List Charges for Subscription

    String listChargesForSubscription =
        JsonLoader.loadJson("/responses/subscription/charges/subscription-finite-charges.json");
    String storesPath = "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6";
    stubFor(
        get(urlPathEqualTo(
                storesPath + "/subscriptions/11efe2d1-b782-1358-b3e0-d7b500e13963/charges"))
            .willReturn(okJson(listChargesForSubscription)));

    // Charge Monitor (GET /charges)
    String getChargeResponse =
        JsonLoader.loadJson(
            "/responses/subscription/charges/subscription-finite-charge-awaiting.json");
    stubFor(
        get(urlPathEqualTo(storesPath + "/charges/11efe2d1-b88e-f31a-a57f-b3f1359e6592"))
            .willReturn(okJson(getChargeResponse)));

    String getChargeIssuerToken =
        JsonLoader.loadJson(
            "/responses/subscription/charges/subscription-finite-charge-issuer.json");
    stubFor(
        get(urlPathEqualTo(
                storesPath + "/charges/11efe2d1-b88e-f31a-a57f-b3f1359e6592/three_ds/issuer_token"))
            .willReturn(okJson(getChargeIssuerToken)));

    // Test starts here
    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      FullSubscription subscription =
          univapay
              .createSubscription(
                  new TransactionTokenId("11efe209-2fb9-9f86-ac12-db6b95569fc9"),
                  new MoneyLike(BigInteger.valueOf(1000), "JPY"),
                  SubscriptionPeriod.MONTHLY)
              .withThreeDs("https://website.com/subscription-three-ds-callback-as-example")
              .withStartOn(LocalDate.of(2025, 2, 10))
              .withSubscriptionPlan(new FixedCyclePaymentPlan(3))
              .withInitialAmount(BigInteger.valueOf(250))
              .dispatch();

      assertEquals(SubscriptionStatus.UNVERIFIED, subscription.getStatus());

      PaginatedList<Charge> listOfCharges =
          univapay
              .listSubscriptionCharges(subscription.getStoreId(), subscription.getId())
              .dispatch();

      // The initial amount != 0 will indicate that there are charges
      assertEquals(BigInteger.valueOf(250), subscription.getInitialAmount());
      // This route is eventually consistent, details are on SubscriptionTest
      Charge chargeFromSubscription = listOfCharges.getItems().get(0);

      Charge charge =
          univapay
              .chargeCompletionMonitor(
                  chargeFromSubscription.getStoreId(), chargeFromSubscription.getId())
              .await();

      assertEquals(ChargeStatus.AWAITING, charge.getStatus());
      assertEquals(
          subscription.getThreeDs().getRedirectId().toUUID(),
          charge.getThreeDS().getRedirectId().toUUID());

      IssuerToken issuerToken =
          univapay.getIssuerTokenThreeDsCharge(charge.getStoreId(), charge.getId()).dispatch();

      // Execute the redirection to the landing page with the issuer token information

      assertEquals(
          "https://api.gyro-n.money/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/three_ds/issuer_token/test/response",
          issuerToken.getIssuerToken());
      assertEquals(CallMethod.HTTP_POST, issuerToken.getCallMethod());

      Map<String, String> payload = new HashMap<>();
      payload.put("resource_id", "11efe2d1-b88e-f31a-a57f-b3f1359e6592");
      payload.put("resource_type", "charge");

      assertEquals(payload, issuerToken.getPayload());
    }
  }

  @Test
  public void shouldBeAbleToProvideExternalThreeDsMPI() throws IOException, UnivapayException {
    // There isn't much to this, API try will accept the MPI
    // As there is no change in the flow of a regular processing
    // This test will be just the form validation with wiremock

    String createChargeRequest =
        JsonLoader.loadJson("/requests/charge/charge-create-with-three-ds-provided.json");
    String createChargeResponse =
        JsonLoader.loadJson("/responses/charge/charge-create-with-three-ds-provided.json");
    stubFor(
        post(urlPathEqualTo("/charges"))
            .withRequestBody(equalToJson(createChargeRequest))
            .willReturn(okJson(createChargeResponse)));

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      Charge createdCharge =
          univapay
              .createCharge(
                  new TransactionTokenId("11efdfad-91d5-3954-91f5-73fd10072929"),
                  new MoneyLike(BigInteger.valueOf(100L), "JPY"))
              .withProvidedThreeDs(
                  "1234567890123456789012345678",
                  "12",
                  "11efbb62-7838-0492-acd7-aaabfef2ee8d",
                  "11efbb62-7838-0492-acd7-aaabfef2ee8a",
                  "2.2.0",
                  "A")
              .dispatch();

      // The flow should be the same
      assertEquals(ChargeStatus.PENDING, createdCharge.getStatus());
      assertEquals(ChargeThreeDsMode.PROVIDED, createdCharge.getThreeDS().getMode());
    }
  }
}
