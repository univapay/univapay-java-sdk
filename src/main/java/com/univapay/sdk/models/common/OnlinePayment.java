package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.brand.OnlineBrand;

public class OnlinePayment implements PaymentData {

  /** This defines which service will be used when creating the TransactionToken & Charge */

  // Can be a Static brand & or a ConnectWallet dynamic sub brand
  @SerializedName("brand")
  private final String brand;

  private transient OnlineBrand onlineBrand;

  @SerializedName("call_method")
  private CallMethod callMethod;

  @SerializedName("user_identifier")
  private String userIdentifier;

  @SerializedName("os_type")
  private OsType osType;

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.ONLINE;
  }

  public OnlinePayment(OnlineBrand brand) {
    if (brand != null) {
      this.brand = brand.getTypeRepresentation();
    } else {
      this.brand = null;
    }

    this.onlineBrand = brand;
  }

  public OnlinePayment(String brand) {
    this.brand = brand;
    this.onlineBrand = OnlineBrand.getInstanceByLiteralValueNullable(brand);
  }

  public OnlinePayment withCallMethod(CallMethod callMethod) {
    this.callMethod = callMethod;
    return this;
  }

  public OnlinePayment withUserIdentifier(String userIdentifier) {
    this.userIdentifier = userIdentifier;
    return this;
  }

  public OnlinePayment withOsType(OsType osType) {
    this.osType = osType;
    return this;
  }

  public OnlineBrand getOnlineBrand() {
    if (onlineBrand == null) {
      onlineBrand = OnlineBrand.getInstanceByLiteralValueNullable(brand);
    }

    return onlineBrand;
  }

  public CallMethod getCallMethod() {
    return callMethod;
  }

  public String getUserIdentifier() {
    return userIdentifier;
  }

  public String getBrand() {
    return brand;
  }

  public OsType getOsType() {
    return osType;
  }
}
