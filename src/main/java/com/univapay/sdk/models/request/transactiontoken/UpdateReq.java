package com.univapay.sdk.models.request.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.types.MetadataMap;

public class UpdateReq {
  @SerializedName("email")
  private UnivapayEmailAddress email;

  @SerializedName("metadata")
  private MetadataMap metadata;

  @SerializedName("data")
  private UpdateCreditCardReq cardData;

  public UpdateReq(UnivapayEmailAddress email, MetadataMap metadata, Integer cvv) {
    this.email = email;
    this.metadata = metadata;
    this.cardData = new UpdateCreditCardReq(cvv);
  }
}
