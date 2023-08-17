package com.univapay.sdk.models.request.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import java.util.Map;
import java.util.Optional;

public class UpdateReq {
  @SerializedName("email")
  private UnivapayEmailAddress email;

  @SerializedName("metadata")
  private Map<String, Object> metadata;

  @SerializedName("data")
  private UpdateCreditCardReq cardData;

  public UpdateReq(UnivapayEmailAddress email, Map<String, Object> metadata, Integer cvv) {
    this.email = email;
    this.metadata = metadata;
    this.cardData = Optional.ofNullable(cvv).map(UpdateCreditCardReq::new).orElse(null);
  }
}
