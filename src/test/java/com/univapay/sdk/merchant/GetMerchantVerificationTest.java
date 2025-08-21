package com.univapay.sdk.merchant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.merchant.MerchantVerificationData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.BusinessType;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.MerchantsFakeRR;
import java.time.OffsetDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class GetMerchantVerificationTest extends GenericTest {

  @Test
  void shouldRequestAndReturnMerchantVerificationData() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/verification", jwt, 200, MerchantsFakeRR.getMerchantVerificationFakeResponse);
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .getMerchantVerification()
        .build()
        .dispatch(
            new UnivapayCallback<MerchantVerificationData>() {
              @Override
              public void getResponse(MerchantVerificationData response) {
                assertEquals("0198f0a2-d5f7-4888-817d-947cc44250ed", response.getId().toString());
                assertEquals("http://www.montavistatedx.com", response.getHomepageUrl().toString());
                assertEquals("Coast Guard", response.getCompanyDescription());
                assertEquals("Ivory R. Grace", response.getCompanyContactInfo().getName());
                assertEquals("Pomeroy's", response.getCompanyContactInfo().getCompanyName());
                assertEquals(
                    "701-268-0116",
                    response.getCompanyContactInfo().getPhoneNumber().getLocalNumber());
                assertEquals(
                    response.getCompanyContactInfo().getPhoneNumber().getCountryCode(),
                    (Integer) 503);
                assertEquals(
                    "3124 Catherine Drive", response.getCompanyContactInfo().getAdressLine1());
                assertNull(response.getCompanyContactInfo().getAdressLine2());
                assertEquals("ND", response.getCompanyContactInfo().getState());
                assertEquals("Maxbass", response.getCompanyContactInfo().getCity());
                assertEquals("US", response.getCompanyContactInfo().getCountry());
                assertThat(
                    response.getCompanyContactInfo().getCountryEnum(), Matchers.is(Country.US));
                assertEquals("58760", response.getCompanyContactInfo().getZip());
                assertEquals(BusinessType.DIGITAL_SALES, response.getBusinessType());
                assertEquals("Rachael R. Simon", response.getSystemManagerName());
                assertEquals("540-220-8465", response.getSystemManagerNumber().getLocalNumber());
                assertEquals(response.getSystemManagerNumber().getCountryCode(), (Integer) 503);
                assertEquals("RachaelRSimon@dayrep.com", response.getSystemManagerEmail());
                assertEquals(RecurringTokenPrivilege.INFINITE, response.getRecurringTokenRequest());
                assertEquals("testing", response.getRecurringTokenRequestReason());
                assertTrue(response.getAllowEmptyCvv());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertEquals(response.getUpdatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {

                notifyCall();
                fail();
              }
            });
    waitCall();
  }
}
