package com.univapay.sdk.merchant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.VerificationPhoneNumber;
import com.univapay.sdk.models.response.merchant.MerchantVerificationData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.MerchantsFakeRR;
import java.net.URL;
import org.junit.jupiter.api.Test;

class UpdateMerchantVerificationTest extends GenericTest {

  @Test
  void shouldRequestAndReturnUpdatedMerchantVerificationData() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/verification",
        jwt,
        200,
        MerchantsFakeRR.updateMerchantVerificationFakeResponse,
        MerchantsFakeRR.updateMerchantVerificationFakeRequest);
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .updateMerchantVerification()
        .withHomepageUrl(new URL("http://www.montavistatedx.com/updated"))
        .withCompanyDescription("Coast Guard Updated")
        .withName("Ivory R. Grace Updated")
        .withCompanyName("Pomeroy's Updated")
        .withAddressLine1("3124 Catherine Drive Updated")
        .withAddressLine2("address line 2 Updated")
        .withPhoneNumber(new VerificationPhoneNumber(503, "701-268-1160"))
        .withSystemManagerEmail("RachaelRSimon@dayrep.com")
        .withCountry(Country.JAPAN)
        .withRecurringTokenRequest(RecurringTokenPrivilege.BOUNDED)
        .withRecurringTokenRequestReason("updating")
        .withAllowEmptyCvv(false)
        .build()
        .dispatch(
            new UnivapayCallback<MerchantVerificationData>() {
              @Override
              public void getResponse(MerchantVerificationData response) {
                assertEquals("0198f0a2-d5f7-4888-817d-947cc44250ed", response.getId().toString());
                assertEquals(
                    "http://www.montavistatedx.com/updated", response.getHomepageUrl().toString());
                assertEquals("Coast Guard Updated", response.getCompanyDescription());
                assertEquals(
                    "3124 Catherine Drive Updated",
                    response.getCompanyContactInfo().getAdressLine1());
                assertThat(
                    response.getCompanyContactInfo().getAdressLine2(),
                    is("address line 2 Updated"));
                assertEquals(
                    "701-268-1160",
                    response.getCompanyContactInfo().getPhoneNumber().getLocalNumber());
                assertThat(response.getCompanyContactInfo().getCountryEnum(), is(Country.JAPAN));
                assertThat(response.getSystemManagerEmail(), is("RachaelRSimon@dayrep.com"));
                assertEquals(RecurringTokenPrivilege.BOUNDED, response.getRecurringTokenRequest());
                assertEquals("updating", response.getRecurringTokenRequestReason());
                assertFalse(response.getAllowEmptyCvv());
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
  void shouldUpdateLegacyCountry() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/verification",
        jwt,
        200,
        MerchantsFakeRR.updateMerchantVerificationFakeResponse,
        MerchantsFakeRR.updateMerchantVerificationFakeRequest);
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .updateMerchantVerification()
        .withHomepageUrl(new URL("http://www.montavistatedx.com/updated"))
        .withCompanyDescription("Coast Guard Updated")
        .withName("Ivory R. Grace Updated")
        .withCompanyName("Pomeroy's Updated")
        .withAddressLine1("3124 Catherine Drive Updated")
        .withAddressLine2("address line 2 Updated")
        .withPhoneNumber(new VerificationPhoneNumber(503, "701-268-1160"))
        .withCountry("JP")
        .withRecurringTokenRequest(RecurringTokenPrivilege.BOUNDED)
        .withRecurringTokenRequestReason("updating")
        .withAllowEmptyCvv(false)
        .withSystemManagerEmail("RachaelRSimon@dayrep.com")
        .build()
        .dispatch(
            new UnivapayCallback<MerchantVerificationData>() {
              @Override
              public void getResponse(MerchantVerificationData response) {
                assertThat(response.getCompanyContactInfo().getCountry(), is("JP"));
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
  void shouldRemoveEmailFromMerchantVerificationData() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/verification",
        jwt,
        200,
        MerchantsFakeRR.updateMerchantVerificationFakeResponse,
        MerchantsFakeRR.updateMerchantVerificationWithEmptyEmailFakeRequest);
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .updateMerchantVerification()
        .removeSystemManagerEmail()
        .build()
        .dispatch(
            new UnivapayCallback<MerchantVerificationData>() {
              @Override
              public void getResponse(MerchantVerificationData response) {
                assertNull(response.getSystemManagerEmail());
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
