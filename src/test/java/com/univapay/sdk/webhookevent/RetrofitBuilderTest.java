package com.univapay.sdk.webhookevent;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.models.webhook.WebhookEvent;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.RetrofitBuilder;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import com.univapay.sdk.utils.mockcontent.TransfersFakeRR;
import org.junit.jupiter.api.Test;

class RetrofitBuilderTest extends GenericTest {

  private final RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

  @Test
  void shouldParseSubscriptionPaymentEvent() {

    String input =
        "{\"event\":\"subscription_payment\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.SUBSCRIPTION_PAYMENT, actual.getEvent());
    assertEquals(Subscription.class, actual.getData().getClass());
    assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    assertNotNull(sub.getPeriod());
    assertNotNull(sub.getStatus());
  }

  @Test
  void shouldParseSubscriptionFailureEvent() {

    String input =
        "{\"event\":\"subscription_failure\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.SUBSCRIPTION_FAILURE, actual.getEvent());
    assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    assertNotNull(sub.getPeriod());
    assertNotNull(sub.getStatus());
  }

  @Test
  void shouldParseSubscriptionCanceledEvent() {
    String input =
        "{\"event\":\"subscription_canceled\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.SUBSCRIPTION_CANCELED, actual.getEvent());
    assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    assertNotNull(sub.getPeriod());
    assertNotNull(sub.getStatus());
  }

  @Test
  void shouldParseSubscriptionCompletedEvent() {
    String input =
        "{\"event\":\"subscription_completed\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.SUBSCRIPTION_COMPLETED, actual.getEvent());
    assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    assertNotNull(sub.getPeriod());
    assertNotNull(sub.getStatus());
  }

  @Test
  void shouldParseSubscriptionSuspendedEvent() {
    String input =
        "{\"event\":\"subscription_suspended\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.SUBSCRIPTION_SUSPENDED, actual.getEvent());
    assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    assertNotNull(sub.getPeriod());
    assertNotNull(sub.getStatus());
  }

  @Test
  void shouldParseChargeFinishedEvent() {
    String input =
        "{\"event\":\"charge_finished\","
            + "\"data\":"
            + ChargesFakeRR.getStoreChargeFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.CHARGE_FINISHED, actual.getEvent());
    assertEquals(Charge.class, actual.getData().getClass());
    Charge charge = (Charge) actual.getData();
    assertNotNull(charge.getStatus());
  }

  @Test
  void shouldParseChargeUpdatedEvent() {
    String input =
        "{\"event\":\"charge_updated\","
            + "\"data\":"
            + ChargesFakeRR.getStoreChargeFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.CHARGE_UPDATED, actual.getEvent());
    assertEquals(Charge.class, actual.getData().getClass());
    Charge charge = (Charge) actual.getData();
    assertNotNull(charge.getStatus());
  }

  @Test
  void shouldParseRefundFinishedEvent() {
    String input =
        "{\"event\":\"refund_finished\"," + "\"data\":" + ChargesFakeRR.getRefundFakeResponse + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.REFUND_FINISHED, actual.getEvent());
    assertEquals(Refund.class, actual.getData().getClass());
    Refund refund = (Refund) actual.getData();
    assertNotNull(refund.getStatus());
  }

  @Test
  void shouldParseTransferFinilizedEvent() {
    String input =
        "{\"event\":\"transfer_finalized\","
            + "\"data\":"
            + TransfersFakeRR.getTransferFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.TRANSFER_FINALIZED, actual.getEvent());
    assertEquals(Transfer.class, actual.getData().getClass());
    Transfer transfer = (Transfer) actual.getData();
    assertNotNull(transfer.getStatus());
  }

  @Test
  void shouldParseCancelFinishedEvent() {
    String input =
        "{\"event\":\"cancel_finished\"," + "\"data\":" + ChargesFakeRR.getCancelFakeResponse + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    assertEquals(PaymentSystemEvent.CANCEL_FINISHED, actual.getEvent());
    assertEquals(Cancel.class, actual.getData().getClass());
    Cancel cancel = (Cancel) actual.getData();
    assertNotNull(cancel.getCancelStatus());
  }

  @Test
  void shouldThrowJsonParseExceptionIfUnknownType() {
    String input = "{\"event\":\"new_event_type\"," + "\"data\": {}}";

    Gson gson = retrofitBuilder.getGson();
    try {
      WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
      fail("Exception is not thrown");
    } catch (JsonParseException e) {
      assertTrue(e.getMessage().startsWith("Unknown event type"));
    }
  }
}
