package com.univapay.sdk.store;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.store.*;
import com.univapay.sdk.models.response.store.DynamicBrandInfo.DynamicBrand;
import com.univapay.sdk.models.response.store.checkoutInfo.*;
import com.univapay.sdk.types.*;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.Test;

public class DynamicBrandInfoTest extends GenericTest {
  UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

  @Test
  public void shouldReturnDynamicBrandsForAlipayPlusOnline() throws UnivapayException, IOException {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/checkout_info/gateway/alipay_plus_online",
        appToken,
        secret,
        200,
        StoreFakeRR.getDynamicInfoAlipayPlusResponse,
        StoreFakeRR.getDynamicInfoAlipayPlusRequest);

    DynamicBrandInfo response =
        univapay
            .getDynamicBrandInfo(Gateway.ALIPAY_PLUS_ONLINE)
            .withCallMethod(CallMethod.APP)
            .withOsType(OsType.iOS)
            .withRequestedMoney(MoneyLike.of("JPY", 1000))
            .dispatch();

    // Seems that dynamic brands list for Alipay+ Online is upper case CONNECT_WALLET

    // So to check if is possible to process with Alipay+, gather a list of any item that have the
    // brand name as "CONNECT_WALLET"
    List<DynamicBrand> alipayPlusDynamicBrands =
        response.getBrands().stream()
            .filter(dynamicBrand -> "CONNECT_WALLET".equals(dynamicBrand.getBrandName()))
            .collect(Collectors.toList());

    // If there are elements in this list, yes is possible
    assertThat(alipayPlusDynamicBrands, hasSize(7));

    // Start to render the Pay with Alipay+ Online button
    DynamicBrand brand = alipayPlusDynamicBrands.get(0);
    // Is easier to the Logo provided - brand.getBrandLogo()

    assertThat(brand.getBrandName(), is("CONNECT_WALLET"));
    assertThat(brand.getBrandLogo(), is("http://localhost/CONNECT_WALLET.svg"));

    // Then for each sub brand
    Set<SubBrand> subBrands =
        alipayPlusDynamicBrands.stream()
            .map(
                dynamicBrand ->
                    new SubBrand(dynamicBrand.getSubBrandName(), dynamicBrand.getSubBrandLogo()))
            .collect(Collectors.toSet());

    // Render each sub brand as a valid choice for the user to click on

    assertThat(
        subBrands,
        containsInAnyOrder(
            new SubBrand("ALIPAY_HK", "http://localhost/icon/ALIPAY_HK.svg"),
            new SubBrand("BPI", "http://localhost/icon/BPI.svg"),
            new SubBrand("GCASH", "http://localhost/icon/GCASH.svg"),
            new SubBrand("KAKAOPAY", "http://localhost/icon/KAKAOPAY.svg"),
            new SubBrand("RABBIT_LINE_PAY", "http://localhost/icon/RABBIT_LINE_PAY.svg"),
            new SubBrand("TNG", "http://localhost/icon/TNG.svg"),
            new SubBrand("TRUEMONEY", "http://localhost/icon/TRUEMONEY.svg")));

    // any option would be used to create a valid transaction token

  }

  @Test
  public void couldReturnNoBrandsIfIsNotSupported() throws UnivapayException, IOException {

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/checkout_info/gateway/alipay_plus_online",
        appToken,
        secret,
        200,
        StoreFakeRR.getDynamicInfoEmpty,
        StoreFakeRR.getDynamicInfoOtherInput);

    // There is a chance that Alipay+ could return no brands for this query
    DynamicBrandInfo response =
        univapay
            .getDynamicBrandInfo(Gateway.ALIPAY_PLUS_ONLINE)
            .withCallMethod(CallMethod.APP)
            .withOsType(OsType.iOS)
            .withRequestedMoney(MoneyLike.of("JPY", 12000))
            .dispatch();

    // Check if is possible to process with Alipay+, gather a list of any item that have the brand
    // name as "CONNECT_WALLET"
    List<DynamicBrand> alipayPlusDynamicBrands =
        response.getBrands().stream()
            .filter(dynamicBrand -> "CONNECT_WALLET".equals(dynamicBrand.getBrandName()))
            .collect(Collectors.toList());

    assertThat(alipayPlusDynamicBrands, hasSize(0));

    // Is this case is not possible to render the Pay with Alipay+ button

  }

  @Test
  public void returnNoBrandsIfNotSupported() throws UnivapayException, IOException {

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/checkout_info/gateway/we_chat_online",
        appToken,
        secret,
        200,
        StoreFakeRR.getDynamicInfoEmpty,
        StoreFakeRR.getDynamicInfoWithoutOsTypeInput);

    DynamicBrandInfo response =
        univapay
            .getDynamicBrandInfo(Gateway.WE_CHAT_ONLINE)
            .withCallMethod(CallMethod.APP)
            .withRequestedMoney(MoneyLike.of("JPY", 12000))
            .dispatch();

    assertThat(response.getBrands(), hasSize(0));
  }

  // Just a helper class to group up the response to make easier assertions
  class SubBrand {
    private String subBrandName;
    private String subBrandLogo;

    SubBrand(String subBrandName, String subBrandLogo) {
      this.subBrandName = subBrandName;
      this.subBrandLogo = subBrandLogo;
    }

    public String getSubBrandLogo() {
      return subBrandLogo;
    }

    public String getSubBrandName() {
      return subBrandName;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      SubBrand subBrand = (SubBrand) o;

      if (!Objects.equals(subBrandName, subBrand.subBrandName)) return false;
      return Objects.equals(subBrandLogo, subBrand.subBrandLogo);
    }

    @Override
    public int hashCode() {
      int result = subBrandName != null ? subBrandName.hashCode() : 0;
      result = 31 * result + (subBrandLogo != null ? subBrandLogo.hashCode() : 0);
      return result;
    }
  }
}
