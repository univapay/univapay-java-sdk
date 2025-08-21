package com.univapay.sdk.subscription;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.SubscriptionStatus;
import com.univapay.sdk.utils.ExponentialBackoff;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.jupiter.api.Test;

class SubscriptionCompletionMonitorTest extends GenericTest {
  private volatile AssertionError assertionError;

  @Test
  void shouldRequestAndReturnSubscriptionInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/subscriptions/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
        jwt,
        200,
        ChargesFakeRR.getSubscriptionCompletedFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    ResourceMonitor<FullSubscription> monitor =
        univapay.subscriptionCompletionMonitor(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new SubscriptionId("425e88b7-b588-4247-80ee-0ea0caff1190"));

    Subscription subscription = monitor.await(60_000, new ExponentialBackoff(0, 5000, 1.5, 0.5));
    assertEquals(SubscriptionStatus.COMPLETED, subscription.getStatus());
  }

  @Test
  void shouldRequestAndReturnSubscriptionInfoAsync() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/subscriptions/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
        jwt,
        200,
        ChargesFakeRR.getSubscriptionCompletedFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    ResourceMonitor<FullSubscription> monitor =
        univapay.subscriptionCompletionMonitor(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new SubscriptionId("425e88b7-b588-4247-80ee-0ea0caff1190"));

    monitor.await(
        new UnivapayCallback<FullSubscription>() {
          @Override
          public void getResponse(FullSubscription subscription) {
            try {
              assertEquals(SubscriptionStatus.COMPLETED, subscription.getStatus());
            } catch (AssertionError err) {
              assertionError = err;
            } finally {
              notifyCall();
            }
          }

          @Override
          public void getFailure(Throwable error) {
            notifyCall();
          }
        },
        60_000,
        new ExponentialBackoff(0, 5_000, 1.5, 0.5));

    waitCall();

    if (assertionError != null) {
      throw assertionError;
    }
  }
}
