package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Konbini;
import java.time.Duration;

public class KonbiniPaymentData {

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

  public KonbiniPaymentData(
      String customerName,
      Konbini convenienceStore,
      Duration expirationPeriod,
      PhoneNumber phoneNumber) {
    this.customerName = customerName;
    this.convenienceStore = convenienceStore;
    this.expirationPeriod = expirationPeriod;
    this.phoneNumber = phoneNumber;
  }
}
