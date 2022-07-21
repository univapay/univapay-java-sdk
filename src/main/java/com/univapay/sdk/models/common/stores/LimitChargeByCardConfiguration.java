package com.univapay.sdk.models.common.stores;

import com.google.gson.annotations.SerializedName;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitChargeByCardConfiguration {
  @SerializedName("quantity_of_charges")
  private Integer quantityOfCharges;

  @SerializedName("duration_window")
  private Duration durationWindow;
}
