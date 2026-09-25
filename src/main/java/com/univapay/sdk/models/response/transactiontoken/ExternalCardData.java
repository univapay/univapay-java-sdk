package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.CardCategory;
import com.univapay.sdk.types.CardSubBrand;
import com.univapay.sdk.types.Country;

/**
 * Card details for an external card payment. Populated only after the external charge succeeds;
 * before that the API omits the {@code card} block entirely.
 */
public class ExternalCardData {

  @SerializedName("cardholder")
  private String cardholder;

  @SerializedName("card_bin")
  private String cardBin;

  @SerializedName("last_four")
  private int lastFour;

  @SerializedName("brand")
  private CardBrand brand;

  @SerializedName("sub_brand")
  private CardSubBrand subBrand;

  @SerializedName("country")
  private Country country;

  @SerializedName("category")
  private CardCategory category;

  @SerializedName("issuer")
  private String issuer;

  public String getCardholder() {
    return cardholder;
  }

  public String getCardBin() {
    return cardBin;
  }

  public int getLastFour() {
    return lastFour;
  }

  /**
   * The returned type will be changed to {@link CardBrand} on later release
   *
   * @return card brand
   */
  public String getBrand() {
    if (brand == null) {
      return null;
    }
    return brand.getBrandName();
  }

  /**
   * This method will be deleted when the returned type by "getBrand(String)" is changed to {@link
   * CardBrand}
   *
   * @return card brand enum
   */
  public CardBrand getBrandEnum() {
    return brand;
  }

  public CardSubBrand getSubBrand() {
    return subBrand;
  }

  /**
   * The returned type will be changed to {@link Country} on later release
   *
   * @return country
   */
  public String getCountry() {
    if (country == null) {
      return null;
    }
    return country.getAlpha2();
  }

  /**
   * This method will be deleted when the returned type by "getCountry(String)" is changed to {@link
   * Country}
   *
   * @return country enum
   */
  public Country getCountryEnum() {
    return country;
  }

  public CardCategory getCategory() {
    return category;
  }

  public String getIssuer() {
    return issuer;
  }
}
