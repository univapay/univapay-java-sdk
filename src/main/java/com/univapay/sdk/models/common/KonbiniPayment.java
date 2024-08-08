package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.types.Konbini;
import com.univapay.sdk.types.PaymentTypeName;
import java.time.Duration;

public class KonbiniPayment implements PaymentData {
  @SerializedName("customer_name")
  private String customerName;

  @SerializedName("convenience_store")
  private Konbini convenienceStore;

  @SerializedName("expiration_period")
  private Duration expirationPeriod;

  @SerializedName("phone_number")
  private PhoneNumber phoneNumber;

  public String getCustomerName() {
    return customerName;
  }

  public Konbini getConvenienceStore() {
    return convenienceStore;
  }

  public Duration getExpirationPeriod() {
    return expirationPeriod;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public KonbiniPayment(
      String customerName,
      Konbini convenienceStore,
      Duration expirationPeriod,
      PhoneNumber phoneNumber) {
    this.customerName = customerName;
    this.convenienceStore = convenienceStore;
    this.expirationPeriod = expirationPeriod;
    this.phoneNumber = phoneNumber;
  }

  public KonbiniPayment(String customerName, Konbini convenienceStore, PhoneNumber phoneNumber) {
    this.customerName = customerName;
    this.convenienceStore = convenienceStore;
    this.phoneNumber = phoneNumber;
  }

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.KONBINI;
  }
}
