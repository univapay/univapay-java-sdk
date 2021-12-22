package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.RefundReason;
import com.univapay.sdk.types.TransactionType;
import java.util.List;

public class TransactionUserData {

  @SerializedName("brand")
  private String brand;

  @SerializedName("card_brand")
  private CardBrand cardBrand;

  @Deprecated
  @SerializedName("gateway")
  private Gateway gateway;

  @SerializedName("type")
  private TransactionType transactionType;

  @SerializedName("cardholder_email_address")
  private String cardholderEmailAddress;

  @SerializedName("cardholder_phone_number")
  private String cardholderPhoneNumber;

  @SerializedName("cardholder_name")
  private String cardHolderName;

  @SerializedName("customer_name")
  private String customerName;

  @SerializedName("convenience_store")
  private Konbini convenienceStore;

  @SerializedName("refunds")
  private List<RefundDetails> refunds;

  @SerializedName("reason")
  private RefundReason refundReason;

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public CardTransactionData asCardTransactionData() {
    return new CardTransactionData(cardHolderName, gateway, brand, cardBrand);
  }

  public ApplePayTransactionData asApplePayTransactionData() {
    return new ApplePayTransactionData(cardHolderName, gateway, brand, cardBrand);
  }

  public QRScanTransactionData asQRScanTransactionData() {
    return new QRScanTransactionData(cardholderEmailAddress, gateway, brand);
  }

  public KonbiniTransactionData asKonbiniTransactionData() {
    return new KonbiniTransactionData(customerName, gateway, brand, convenienceStore);
  }

  public PaidyTransactionData asPaidyTransactionData() {
    return new PaidyTransactionData(cardholderEmailAddress, cardholderPhoneNumber, gateway, brand);
  }

  public ChargeUserData asChargeUserData() {
    return new ChargeUserData(refunds);
  }

  public RefundUserData asRefundUserData() {
    return new RefundUserData(refundReason);
  }
}
