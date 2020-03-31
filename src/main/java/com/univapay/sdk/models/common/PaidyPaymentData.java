package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.types.PaymentTypeName;

public class PaidyPaymentData implements PaymentData {
  @SerializedName("paidy_token")
  PaidyToken paidyToken;

  @SerializedName("phone_number")
  PhoneNumber phoneNumber;

  @SerializedName("shipping_address")
  PaidyShippingAddress shippingAddress;

  public PaidyPaymentData(PaidyToken paidyToken, PaidyShippingAddress shippingAddress) {
    this.paidyToken = paidyToken;
    this.shippingAddress = shippingAddress;
  }

  public PaidyPaymentData(String paidyToken, PaidyShippingAddress shippingAddress) {
    this.paidyToken = new PaidyToken(paidyToken);
    this.shippingAddress = shippingAddress;
  }

  public PaidyPaymentData withPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.PAIDY;
  }

  public PaidyToken getPaidyToken() {
    return paidyToken;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public PaidyShippingAddress getShippingAddress() {
    return shippingAddress;
  }
}
