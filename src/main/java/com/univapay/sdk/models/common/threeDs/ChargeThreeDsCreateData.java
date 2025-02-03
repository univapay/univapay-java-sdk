package com.univapay.sdk.models.common.threeDs;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChargeThreeDsCreateData {

  @SerializedName("mode")
  private ChargeThreeDsMode mode;

  @SerializedName("redirect_endpoint")
  private String redirectEndpoint;

  @SerializedName("authentication_value")
  private String authenticationValue;

  @SerializedName("eci")
  private String eci;

  @SerializedName("ds_transaction_id")
  private String dsTransactionId;

  @SerializedName("server_transaction_id")
  private String serverTransactionId;

  @SerializedName("message_version")
  private String messageVersion;

  @SerializedName("transaction_status")
  private String transactionStatus;
}
