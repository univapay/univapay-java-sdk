package com.univapay.sdk.webhookevent;

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
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class RetrofitBuilderTest extends GenericTest {

  private final RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

  @Test
  public void shouldParseSubscriptionPaymentEvent() {

    String input =
        "{\"event\":\"subscription_payment\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.SUBSCRIPTION_PAYMENT, actual.getEvent());
    Assert.assertEquals(Subscription.class, actual.getData().getClass());
    Assert.assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    Assert.assertNotNull(sub.getPeriod());
    Assert.assertNotNull(sub.getStatus());
  }

  @Test
  public void shouldParseSubscriptionFailureEvent() {

    String input =
        "{\"event\":\"subscription_failure\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.SUBSCRIPTION_FAILURE, actual.getEvent());
    Assert.assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    Assert.assertNotNull(sub.getPeriod());
    Assert.assertNotNull(sub.getStatus());
  }

  @Test
  public void shouldParseSubscriptionCanceledEvent() {
    String input =
        "{\"event\":\"subscription_canceled\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.SUBSCRIPTION_CANCELED, actual.getEvent());
    Assert.assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    Assert.assertNotNull(sub.getPeriod());
    Assert.assertNotNull(sub.getStatus());
  }

  @Test
  public void shouldParseSubscriptionCompletedEvent() {
    String input =
        "{\"event\":\"subscription_completed\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.SUBSCRIPTION_COMPLETED, actual.getEvent());
    Assert.assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    Assert.assertNotNull(sub.getPeriod());
    Assert.assertNotNull(sub.getStatus());
  }

  @Test
  public void shouldParseSubscriptionSuspendedEvent() {
    String input =
        "{\"event\":\"subscription_suspended\","
            + "\"data\":"
            + ChargesFakeRR.getSubscriptionFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.SUBSCRIPTION_SUSPENDED, actual.getEvent());
    Assert.assertEquals(Subscription.class, actual.getData().getClass());
    Subscription sub = (Subscription) actual.getData();
    Assert.assertNotNull(sub.getPeriod());
    Assert.assertNotNull(sub.getStatus());
  }

  @Test
  public void shouldParseChargeFinishedEvent() {
    String input =
        "{\"event\":\"charge_finished\","
            + "\"data\":"
            + ChargesFakeRR.getStoreChargeFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.CHARGE_FINISHED, actual.getEvent());
    Assert.assertEquals(Charge.class, actual.getData().getClass());
    Charge charge = (Charge) actual.getData();
    Assert.assertNotNull(charge.getStatus());
  }

  @Test
  public void shouldParseChargeUpdatedEvent() {
    String input =
        "{\"event\":\"charge_updated\","
            + "\"data\":"
            + ChargesFakeRR.getStoreChargeFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.CHARGE_UPDATED, actual.getEvent());
    Assert.assertEquals(Charge.class, actual.getData().getClass());
    Charge charge = (Charge) actual.getData();
    Assert.assertNotNull(charge.getStatus());
  }

  @Test
  public void shouldParseRefundFinishedEvent() {
    String input =
        "{\"event\":\"refund_finished\"," + "\"data\":" + ChargesFakeRR.getRefundFakeResponse + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.REFUND_FINISHED, actual.getEvent());
    Assert.assertEquals(Refund.class, actual.getData().getClass());
    Refund refund = (Refund) actual.getData();
    Assert.assertNotNull(refund.getStatus());
  }

  @Test
  public void shouldParseTransferFinilizedEvent() {
    String input =
        "{\"event\":\"transfer_finalized\","
            + "\"data\":"
            + TransfersFakeRR.getTransferFakeResponse
            + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.TRANSFER_FINALIZED, actual.getEvent());
    Assert.assertEquals(Transfer.class, actual.getData().getClass());
    Transfer transfer = (Transfer) actual.getData();
    Assert.assertNotNull(transfer.getStatus());
  }

  @Test
  public void shouldParseCancelFinishedEvent() {
    String input =
        "{\"event\":\"cancel_finished\"," + "\"data\":" + ChargesFakeRR.getCancelFakeResponse + "}";

    Gson gson = retrofitBuilder.getGson();
    WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
    Assert.assertEquals(PaymentSystemEvent.CANCEL_FINISHED, actual.getEvent());
    Assert.assertEquals(Cancel.class, actual.getData().getClass());
    Cancel cancel = (Cancel) actual.getData();
    Assert.assertNotNull(cancel.getCancelStatus());
  }

  @Test
  public void shouldThrowJsonParseExceptionIfUnknownType() {
    String input = "{\"event\":\"new_event_type\"," + "\"data\": {}}";

    Gson gson = retrofitBuilder.getGson();
    try {
      WebhookEvent actual = gson.fromJson(input, WebhookEvent.class);
      Assert.fail("Exception is not thrown");
    } catch (JsonParseException e) {
      Assert.assertThat(e.getMessage(), CoreMatchers.startsWith("Unknown event type"));
    }
  }
}
