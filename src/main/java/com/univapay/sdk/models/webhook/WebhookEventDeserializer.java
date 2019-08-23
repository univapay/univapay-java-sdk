package com.univapay.sdk.models.webhook;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.univapay.sdk.types.PaymentSystemEvent;
import java.lang.reflect.Type;

public class WebhookEventDeserializer implements JsonDeserializer<WebhookEvent> {

  @Override
  public WebhookEvent deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    final JsonElement eventElement = jsonElement.getAsJsonObject().get("event");
    PaymentSystemEvent event =
        jsonDeserializationContext.deserialize(eventElement, PaymentSystemEvent.class);
    if (event == null) {
      throw new JsonParseException("Unknown event type: " + eventElement.toString());
    }

    switch (event) {
      case CHARGE_FINISHED:
      case CHARGE_UPDATED:
        return jsonDeserializationContext.deserialize(jsonElement, ChargeEvent.class);
      case SUBSCRIPTION_PAYMENT:
      case SUBSCRIPTION_FAILURE:
      case SUBSCRIPTION_CANCELED:
      case SUBSCRIPTION_COMPLETED:
      case SUBSCRIPTION_SUSPENDED:
        return jsonDeserializationContext.deserialize(jsonElement, SubscriptionEvent.class);
      case REFUND_FINISHED:
        return jsonDeserializationContext.deserialize(jsonElement, RefundEvent.class);
      case TRANSFER_FINALIZED:
        return jsonDeserializationContext.deserialize(jsonElement, TransferEvent.class);
      case CANCEL_FINISHED:
        return jsonDeserializationContext.deserialize(jsonElement, CancelEvent.class);
      default:
        throw new JsonParseException("Unknown event type: " + event.toString());
    }
  }
}
