package com.univapay.sdk.models.response.cancel;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaymentError;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.CancelStatus;
import com.univapay.sdk.types.ProcessingMode;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public class Cancel extends UnivapayResponse implements SimpleModel<CancelId> {

  @SerializedName("id")
  private UUID cancelId;

  @SerializedName("charge_id")
  private UUID chargeId;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("status")
  private CancelStatus cancelStatus;

  @SerializedName("error")
  private PaymentError error;

  @SerializedName("metadata")
  private Map<String, String> metadata;

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  public CancelId getCancelId() {
    return new CancelId(cancelId);
  }

  public ChargeId getChargeId() {
    return new ChargeId(chargeId);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public CancelStatus getCancelStatus() {
    return cancelStatus;
  }

  public PaymentError getError() {
    return error;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public ProcessingMode getMode() {
    return mode;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  @Override
  public CancelId getId() {
    return new CancelId(cancelId);
  }
}
