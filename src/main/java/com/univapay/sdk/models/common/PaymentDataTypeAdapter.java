package com.univapay.sdk.models.common;

import com.google.gson.*;
import com.univapay.sdk.models.response.transactiontoken.*;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.Konbini;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.QrCpmBrand;
import com.univapay.sdk.types.brand.QrMpmBrand;
import java.lang.reflect.Type;
import java.time.Period;

public class PaymentDataTypeAdapter
    implements JsonSerializer<PaymentData>, JsonDeserializer<PaymentData> {
  @Override
  public JsonElement serialize(
      PaymentData payment, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject object = new JsonObject();

    object.add("card", context.serialize(payment.getCard()));
    object.add("billing", context.serialize(payment.getBilling()));
    object.add("customer_name", context.serialize(payment.getCustomerName()));
    object.add("convenience_store", context.serialize(payment.getConvenienceStore()));
    object.add("expiration_period", context.serialize(payment.getExpirationPeriod()));
    object.add("phone_number", context.serialize(payment.getPhoneNumber()));
    object.add("paidy_token", context.serialize(payment.getPaidyToken()));
    object.add("shipping_address", context.serialize(payment.getShippingAddress()));

    QrScanPaymentData qrScanPaymentData = payment.asQrScanData();
    object.add("gateway", context.serialize(qrScanPaymentData.getGateway()));

    if (qrScanPaymentData.getBrand() != null) {
      object.add("brand", context.serialize(qrScanPaymentData.getBrand()));
    }

    OnlinePaymentData onlinePaymentData = payment.asOnlinePaymentData();

    if (onlinePaymentData.getBrand() != null) {
      object.add("brand", context.serialize(onlinePaymentData.getBrand()));
    }

    QrMerchantPaymentData qrMerchantPaymentData = payment.asQrMerchantPaymentData();

    if (qrMerchantPaymentData.getBrand() != null) {
      object.add("brand", context.serialize(qrMerchantPaymentData.getBrand()));
    }

    object.add("qr_image_url", context.serialize(qrMerchantPaymentData.getQrImageUrl()));

    object.add("call_method", context.serialize(onlinePaymentData.getCallMethod()));
    object.add("issuer_token", context.serialize(onlinePaymentData.getIssuerToken()));

    return object;
  }

  // null safe "object.get(field).getAsString"
  private String asString(JsonObject object, String field) {
    JsonElement value = object.get(field);
    if (value != null && !value.isJsonNull()) {
      return value.getAsString();
    } else {
      return null;
    }
  }

  @Override
  public PaymentData deserialize(JsonElement json, Type type, JsonDeserializationContext context)
      throws JsonParseException {

    // Seems the only way is to actually

    JsonObject object = json.getAsJsonObject();

    if (object == null) {
      return null;
    }

    TransactionTokenCardData card =
        context.deserialize(object.get("card"), TransactionTokenCardData.class);
    TransactionTokenBillingData billing =
        context.deserialize(object.get("billing"), TransactionTokenBillingData.class);
    String customerName = asString(object, "customer_name");
    Konbini convenienceStore = context.deserialize(object.get("convenience_store"), Konbini.class);
    Period expirationPeriod = context.deserialize(object.get("expiration_period"), Period.class);
    PhoneNumber phoneNumber = context.deserialize(object.get("phone_number"), PhoneNumber.class);
    PaidyToken paidyToken = context.deserialize(object.get("paidy_token"), PaidyToken.class);
    PaidyShippingAddress shippingAddress =
        context.deserialize(object.get("shipping_address"), PaidyShippingAddress.class);
    Gateway gateway = context.deserialize(object.get("gateway"), Gateway.class);
    String qrImageUrl = asString(object, "qr_image_url");

    QrCpmBrand qrCpmBrand = context.deserialize(object.get("brand"), QrCpmBrand.class);
    QrMpmBrand qrMpmBrand = context.deserialize(object.get("brand"), QrMpmBrand.class);
    OnlineBrand onlineBrand = context.deserialize(object.get("brand"), OnlineBrand.class);
    CallMethod callMethod = context.deserialize(object.get("call_method"), CallMethod.class);
    String issuerToken = asString(object, "issuer_token");

    return new PaymentData(
        card,
        billing,
        customerName,
        convenienceStore,
        expirationPeriod,
        phoneNumber,
        paidyToken,
        shippingAddress,
        gateway,
        qrImageUrl,
        qrCpmBrand,
        qrMpmBrand,
        onlineBrand,
        callMethod,
        issuerToken);
  }
}
