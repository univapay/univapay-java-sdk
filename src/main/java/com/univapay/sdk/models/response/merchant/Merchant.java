package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MerchantId;
import com.univapay.sdk.models.common.VerificationDataId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.util.Date;
import java.util.UUID;

public class Merchant extends UnivapayResponse implements SimpleModel {

  @SerializedName("id")
  private UUID merchantId;

  @SerializedName("verification_data_id")
  private UUID verificationDataId;

  @SerializedName("name")
  private String name;

  @SerializedName("email")
  private String email;

  @SerializedName("verified")
  private Boolean verified;

  @SerializedName("created_on")
  private Date createdOn;

  public MerchantId getMerchantId() {
    return new MerchantId(merchantId);
  }

  public VerificationDataId getVerificationDataId() {
    return new VerificationDataId(verificationDataId);
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Boolean getVerified() {
    return verified;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  @Override
  public MerchantId getId() {
    return new MerchantId(merchantId);
  }
}
