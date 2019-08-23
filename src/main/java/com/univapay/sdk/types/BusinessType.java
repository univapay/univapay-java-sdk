package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum BusinessType {
  @SerializedName("textiles")
  TEXTILES,
  @SerializedName("product_sales")
  PRODUCT_SALES,
  @SerializedName("consulting")
  CONSULTING,
  @SerializedName("travel")
  TRAVEL,
  @SerializedName("digital_sales")
  DIGITAL_SALES,
  @SerializedName("aesthetic_salon")
  AESTHETIC_SALON,
  @SerializedName("other")
  OTHER
}
