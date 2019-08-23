package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.Konbini;
import com.univapay.sdk.types.RefundReason;
import com.univapay.sdk.types.TransactionType;
import java.util.List;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.types.*;

public class TransactionUserData {
  @SerializedName("cardholder_name")
  private String cardHolderName;

  @SerializedName("card_brand")
  private CardBrand cardBrand;

  @SerializedName("gateway")
  private Gateway gateway;

  @SerializedName("cardholder_email_address")
  private String cardholderEmailAddress;

  @SerializedName("customer_name")
  private String customerName;

  @SerializedName("convenience_store")
  private Konbini convenienceStore;

  @SerializedName("cardholder_phone_number")
  private PhoneNumber cardholderPhoneNumber;

  @SerializedName("type")
  private TransactionType transactionType;

  @SerializedName("refunds")
  private List<RefundDetails> refunds;

  @SerializedName("reason")
  private RefundReason refundReason;

  private PaymentTransactionData paymentData;

  private TransactionTypeData transactionTypeData;

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public CardTransactionData asCardTransactionData() {
    if (paymentData == null) {
      paymentData = new CardTransactionData(cardHolderName, cardBrand, gateway);
    }
    return (CardTransactionData) paymentData;
  }

  public ApplePayTransactionData asApplePayTransactionData() {
    if (paymentData == null) {
      paymentData = new ApplePayTransactionData(cardHolderName, cardBrand, gateway);
    }
    return (ApplePayTransactionData) paymentData;
  }

  public QRScanTransactionData asQRScanTransactionData() {
    if (paymentData == null) {
      paymentData = new QRScanTransactionData(cardholderEmailAddress, gateway);
    }
    return (QRScanTransactionData) paymentData;
  }

  public KonbiniTransactionData asKonbiniTransactionData() {
    if (paymentData == null) {
      paymentData = new KonbiniTransactionData(customerName, convenienceStore, gateway);
    }
    return (KonbiniTransactionData) paymentData;
  }

  public PaidyTransactionData asPaidyTransactionData() {
    if (paymentData == null) {
      paymentData =
          new PaidyTransactionData(cardholderEmailAddress, cardholderPhoneNumber, gateway);
    }
    return (PaidyTransactionData) paymentData;
  }

  public ChargeUserData asChargeUserData() {
    if (transactionTypeData == null) {
      transactionTypeData = new ChargeUserData(refunds);
    }
    return (ChargeUserData) transactionTypeData;
  }

  public RefundUserData asRefundUseData() {
    if (transactionTypeData == null) {
      transactionTypeData = new RefundUserData(refundReason);
    }
    return (RefundUserData) transactionTypeData;
  }
}
