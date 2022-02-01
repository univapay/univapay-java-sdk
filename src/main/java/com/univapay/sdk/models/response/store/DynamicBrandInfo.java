package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.util.ArrayList;
import java.util.List;

public class DynamicBrandInfo extends UnivapayResponse {

  @SerializedName("brands")
  private List<DynamicBrand> brands;

  public List<DynamicBrand> getBrands() {
    if (brands == null) {
      this.brands = new ArrayList<>();
    }

    return brands;
  }

  public static class DynamicBrand {

    @SerializedName("brand_name")
    private String brandName;

    @SerializedName("brand_logo")
    private String brandLogo;

    @SerializedName("sub_brand_name")
    private String subBrandName;

    @SerializedName("sub_brand_logo")
    private String subBrandLogo;

    public DynamicBrand(
        String brandName, String brandLogo, String subBrandName, String subBrandLogo) {
      this.brandName = brandName;
      this.brandLogo = brandLogo;
      this.subBrandName = subBrandName;
      this.subBrandLogo = subBrandLogo;
    }

    public DynamicBrand() {}

    public String getBrandName() {
      return brandName;
    }

    public String getBrandLogo() {
      return brandLogo;
    }

    public String getSubBrandName() {
      return subBrandName;
    }

    public String getSubBrandLogo() {
      return subBrandLogo;
    }
  }
}
