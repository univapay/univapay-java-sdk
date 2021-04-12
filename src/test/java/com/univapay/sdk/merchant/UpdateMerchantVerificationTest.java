package com.univapay.sdk.merchant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
import java.io.IOException;
import java.net.URL;
import org.junit.Test;

public class UpdateMerchantVerificationTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnUpdatedMerchantVerificationData()
      throws InterruptedException, IOException {

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
                assertEquals(response.getId().toString(), "0198f0a2-d5f7-4888-817d-947cc44250ed");
                assertEquals(
                    response.getHomepageUrl().toString(), "http://www.montavistatedx.com/updated");
                assertEquals(response.getCompanyDescription(), "Coast Guard Updated");
                assertEquals(
                    response.getCompanyContactInfo().getAdressLine1(),
                    "3124 Catherine Drive Updated");
                assertThat(
                    response.getCompanyContactInfo().getAdressLine2(),
                    is("address line 2 Updated"));
                assertEquals(
                    response.getCompanyContactInfo().getPhoneNumber().getLocalNumber(),
                    "701-268-1160");
                assertThat(response.getCompanyContactInfo().getCountryEnum(), is(Country.JAPAN));
                assertThat(response.getSystemManagerEmail(), is("RachaelRSimon@dayrep.com"));
                assertEquals(response.getRecurringTokenRequest(), RecurringTokenPrivilege.BOUNDED);
                assertEquals(response.getRecurringTokenRequestReason(), "updating");
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
  public void shouldUpdateLegacyCountry() throws InterruptedException, IOException {

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
  public void shouldRemoveEmailFromMerchantVerificationData() throws InterruptedException {

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
                assertThat(response.getSystemManagerEmail(), is(nullValue()));
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
