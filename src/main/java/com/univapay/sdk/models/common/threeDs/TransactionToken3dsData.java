package com.univapay.sdk.models.common.threeDs;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.RedirectId;
import com.univapay.sdk.models.response.PaymentError;
import java.net.URI;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionToken3dsData {

  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("status")
  private TransactionToken3dsStatus status;

  @SerializedName("redirect_endpoint")
  private URI redirectEndpoint;

  @SerializedName("redirect_id")
  private UUID redirectId;

  @SerializedName("error")
  private PaymentError paymentError;

  public RedirectId getRedirectId() {

    if (redirectId != null) {
      return new RedirectId(redirectId);
    } else {
      return null;
    }
  }
}
