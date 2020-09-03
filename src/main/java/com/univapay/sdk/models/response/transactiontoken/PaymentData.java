package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.PaidyPaymentData;
import com.univapay.sdk.models.common.PaidyShippingAddress;
import com.univapay.sdk.models.common.PaidyToken;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.Konbini;
import com.univapay.sdk.types.QRBrand;
import java.time.Period;

public class PaymentData {

  @SerializedName("card")
  private TransactionTokenCardData card;

  @SerializedName("billing")
  private TransactionTokenBillingData billing;

  @SerializedName("customer_name")
  private String customerName;

  @SerializedName("convenience_store")
  private Konbini convenienceStore;

  @SerializedName("expiration_period")
  private Period expirationPeriod;

  @SerializedName("phone_number")
  private PhoneNumber phoneNumber;

  @SerializedName("paidy_token")
  private PaidyToken paidyToken;

  @SerializedName("shipping_address")
  private PaidyShippingAddress shippingAddress;

  @SerializedName("gateway")
  private Gateway gateway;

  @SerializedName("qr_image_url")
  private String qrImageUrl;

  @SerializedName("industry")
  private String industry;

  @SerializedName("brand")
  private QRBrand brand;

  @SerializedName("call_method")
  private CallMethod callMethod;

  @SerializedName("issuer_token")
  private String issuerToken;

  public TransactionTokenCardData getCard() {
    return card;
  }

  public TransactionTokenBillingData getBilling() {
    return billing;
  }

  public String getCustomerName() {
    return customerName;
  }

  public Konbini getConvenienceStore() {
    return convenienceStore;
  }

  public Period getExpirationPeriod() {
    return expirationPeriod;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public PaidyToken getPaidyToken() {
    return paidyToken;
  }

  public PaidyShippingAddress getShippingAddress() {
    return shippingAddress;
  }

  public CardPaymentData asCardPaymentData() {
    return new CardPaymentData(card, billing);
  }

  public KonbiniPaymentData asKonbiniPaymentData() {
    return new KonbiniPaymentData(customerName, convenienceStore, expirationPeriod, phoneNumber);
  }

  public PaidyPaymentData asPaidyPaymentData() {
    return new PaidyPaymentData(paidyToken, shippingAddress).withPhoneNumber(phoneNumber);
  }

  public QrScanPaymentData asQrScanData() {
    return new QrScanPaymentData(gateway, brand);
  }

  public QrMerchantPaymentData asQrMerchantPaymentData() {
    return new QrMerchantPaymentData(qrImageUrl, industry, gateway);
  }

  public OnlinePaymentData asOnlinePaymentData() {
    return new OnlinePaymentData(gateway, issuerToken, callMethod);
  }
}
