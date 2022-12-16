package com.univapay.sdk.models.common.charge;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

/** This is the model to map the parameters to enable the CVV Authorization on Charge Creation */
@Data
@AllArgsConstructor
public class CvvAuthorizationEnable {

  @SerializedName("enabled")
  private Boolean enabled;
}
