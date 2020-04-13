package com.univapay.sdk.merchant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.VerificationPhoneNumber;
import com.univapay.sdk.models.response.merchant.MerchantCompanyContactInfo;
import com.univapay.sdk.models.response.merchant.MerchantVerificationData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.BusinessType;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.MerchantsFakeRR;
import java.io.IOException;
import java.net.URL;
import org.junit.Test;

public class CreateMerchantVerificationTest extends GenericTest {

  @Test
  public void shouldPostAndReturnMerchantVerificationData()
      throws InterruptedException, IOException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/verification",
        token,
        200,
        MerchantsFakeRR.postMerchantVerificationFakeResponse,
        MerchantsFakeRR.postMerchantVerificationFakeRequest);
    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final MerchantCompanyContactInfo contactInfo =
        new MerchantCompanyContactInfo(
            "Ivory R. Grace",
            "Pomeroy's",
            new VerificationPhoneNumber(503, "701-268-0116"),
            "3124 Catherine Drive",
            "",
            "ND",
            "Maxbass",
            Country.US,
            "58760");

    univapay
        .createMerchantVerification(
            new URL("http://www.montavistatedx.com"),
            "Coast Guard",
            contactInfo,
            BusinessType.DIGITAL_SALES,
            "Rachael R. Simon")
        .withSystemManagerEmail("RachaelRSimon@dayrep.com")
        .withSystemManagerNumber(new VerificationPhoneNumber(503, "540-220-8465"))
        .withRecurringTokenRequest(RecurringTokenPrivilege.INFINITE)
        .withRecurringTokenRequestReason("testing")
        .withAllowEmptyCvv(true)
        .build()
        .dispatch(
            new UnivapayCallback<MerchantVerificationData>() {
              @Override
              public void getResponse(MerchantVerificationData response) {
                assertEquals(response.getId().toString(), "0198f0a2-d5f7-4888-817d-947cc44250ed");
                assertEquals(response.getHomepageUrl().toString(), "http://www.montavistatedx.com");
                assertEquals(response.getCompanyDescription(), "Coast Guard");
                assertEquals(response.getCompanyContactInfo().getName(), "Ivory R. Grace");
                assertThat(
                    response.getCompanyContactInfo().getCountryEnum(),
                    is(contactInfo.getCountryEnum()));
                assertEquals(response.getRecurringTokenRequest(), RecurringTokenPrivilege.INFINITE);
                assertEquals(response.getRecurringTokenRequestReason(), "testing");
                assertTrue(response.getAllowEmptyCvv());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  public void shouldPostLegacyCountry() throws InterruptedException, IOException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/verification",
        token,
        200,
        MerchantsFakeRR.postMerchantVerificationFakeResponse,
        MerchantsFakeRR.postMerchantVerificationFakeRequest);
    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final MerchantCompanyContactInfo contactInfo =
        new MerchantCompanyContactInfo(
            "Ivory R. Grace",
            "Pomeroy's",
            new VerificationPhoneNumber(503, "701-268-0116"),
            "3124 Catherine Drive",
            "",
            "ND",
            "Maxbass",
            "US",
            "58760");

    univapay
        .createMerchantVerification(
            new URL("http://www.montavistatedx.com"),
            "Coast Guard",
            contactInfo,
            BusinessType.DIGITAL_SALES,
            "Rachael R. Simon")
        .withSystemManagerEmail("RachaelRSimon@dayrep.com")
        .withSystemManagerNumber(new VerificationPhoneNumber(503, "540-220-8465"))
        .withRecurringTokenRequest(RecurringTokenPrivilege.INFINITE)
        .withRecurringTokenRequestReason("testing")
        .withAllowEmptyCvv(true)
        .build()
        .dispatch(
            new UnivapayCallback<MerchantVerificationData>() {
              @Override
              public void getResponse(MerchantVerificationData response) {
                assertThat(
                    response.getCompanyContactInfo().getCountry(), is(contactInfo.getCountry()));
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }
}
