package com.univapay.sdk.models.response.transactiontoken;

import com.univapay.sdk.models.common.*;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.Konbini;
import com.univapay.sdk.types.brand.QrCpmBrand;
import com.univapay.sdk.types.brand.QrMpmBrand;
import java.time.Period;

public class PaymentData {

  public PaymentData() {}

  public PaymentData(
      TransactionTokenCardData card,
      TransactionTokenBillingData billing,
      String customerName,
      Konbini convenienceStore,
      Period expirationPeriod,
      PhoneNumber phoneNumber,
      PaidyToken paidyToken,
      PaidyShippingAddress shippingAddress,
      Gateway gateway,
      String qrImageUrl,
      QrCpmBrand qrCpmBrand,
      QrMpmBrand qrMpmBrand,
      String onlineBrand,
      CallMethod callMethod,
      String issuerToken,
      String userIdentifier) {
    this.card = card;
    this.billing = billing;
    this.customerName = customerName;
    this.convenienceStore = convenienceStore;
    this.expirationPeriod = expirationPeriod;
    this.phoneNumber = phoneNumber;
    this.paidyToken = paidyToken;
    this.shippingAddress = shippingAddress;
    this.gateway = gateway;
    this.qrImageUrl = qrImageUrl;
    this.qrCpmBrand = qrCpmBrand;
    this.qrMpmBrand = qrMpmBrand;
    this.onlineBrand = onlineBrand;
    this.callMethod = callMethod;
    this.issuerToken = issuerToken;
    this.userIdentifier = userIdentifier;
  }

  private TransactionTokenCardData card;

  private TransactionTokenBillingData billing;

  private String customerName;

  private Konbini convenienceStore;

  private Period expirationPeriod;

  private PhoneNumber phoneNumber;

  private PaidyToken paidyToken;

  private PaidyShippingAddress shippingAddress;

  private Gateway gateway;

  private String qrImageUrl;

  private QrCpmBrand qrCpmBrand;

  private QrMpmBrand qrMpmBrand;

  private String onlineBrand;

  private CallMethod callMethod;

  private String issuerToken;

  private String userIdentifier;

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
    return new QrScanPaymentData(gateway, qrCpmBrand);
  }

  public QrMerchantPaymentData asQrMerchantPaymentData() {
    return new QrMerchantPaymentData(qrImageUrl, qrMpmBrand);
  }

  public OnlinePaymentData asOnlinePaymentData() {
    return new OnlinePaymentData(onlineBrand, issuerToken, callMethod, userIdentifier);
  }
}
