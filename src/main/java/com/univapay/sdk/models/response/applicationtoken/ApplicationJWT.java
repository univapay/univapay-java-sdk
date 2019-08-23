package com.univapay.sdk.models.response.applicationtoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.AppJWTId;
import com.univapay.sdk.models.common.MerchantId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.util.Date;
import java.util.UUID;

public abstract class ApplicationJWT extends UnivapayResponse implements SimpleModel<AppJWTId> {

  @SerializedName("merchant_id")
  private UUID merchantId;

  @SerializedName("creator_id")
  private UUID creatorId;

  @SerializedName("jti")
  private UUID jti;

  @SerializedName("secret")
  private String secret;

  @SerializedName("jwt")
  private String jwt;

  @SerializedName("created_on")
  private Date createdOn;

  public MerchantId getMerchantId() {
    return new MerchantId(merchantId);
  }

  public MerchantId getCreatorId() {
    return new MerchantId(creatorId);
  }

  public AppJWTId getId() {
    return new AppJWTId(jti);
  }

  public String getSecret() {
    return secret;
  }

  public String getJwt() {
    return jwt;
  }

  public Date getCreatedOn() {
    return createdOn;
  }
}
