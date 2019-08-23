package com.univapay.sdk.models.response.applicationtoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.AppTokenId;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.auth.AppTokenStrategy;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.ProcessingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ApplicationToken extends UnivapayResponse implements SimpleModel<AppTokenId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("token")
  private String token;

  @SerializedName("secret")
  private String secret;

  @SerializedName("domains")
  private List<String> domains;

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("created_on")
  private Date createdOn;

  public AppTokenId getId() {
    return new AppTokenId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public String getToken() {
    return token;
  }

  public String getSecret() {
    return secret;
  }

  public List<Domain> getDomains() {
    List<Domain> formattedDomains = new ArrayList<>();
    for (String domain : domains) {
      formattedDomains.add(new Domain(domain));
    }
    return formattedDomains;
  }

  public ProcessingMode getMode() {
    return mode;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public AppTokenStrategy getAppTokenAuthStrategy() {
    return new AppTokenStrategy(token, secret);
  }
}
