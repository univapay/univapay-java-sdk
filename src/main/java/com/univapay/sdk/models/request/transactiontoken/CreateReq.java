package com.univapay.sdk.models.request.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.RecurringTokenInterval;
import com.univapay.sdk.types.TransactionTokenType;

@SuppressWarnings("FieldCanBeLocal")
public class CreateReq {
  @SerializedName("payment_type")
  private PaymentTypeName paymentType;

  @SerializedName("email")
  private UnivapayEmailAddress email;

  @SerializedName("type")
  private TransactionTokenType type;

  @SerializedName("usage_limit")
  private RecurringTokenInterval usageLimit;

  @SerializedName("metadata")
  private MetadataMap metadata;

  @SerializedName("use_confirmation")
  private Boolean useConfirmation;

  @SerializedName("ip_address")
  private String ipAddress;

  @SerializedName("data")
  private PaymentData data;

  public CreateReq(
      UnivapayEmailAddress email,
      TransactionTokenType type,
      RecurringTokenInterval usageLimit,
      MetadataMap metadata,
      Boolean useConfirmation,
      String ipAddress,
      PaymentData data) {
    this.paymentType = data.getPaymentType();
    this.email = email;
    this.type = type;
    this.usageLimit = usageLimit;
    this.metadata = metadata;
    this.useConfirmation = useConfirmation;
    this.ipAddress = ipAddress;
    this.data = data;
  }
}
