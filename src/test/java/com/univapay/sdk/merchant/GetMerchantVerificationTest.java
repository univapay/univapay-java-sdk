package com.univapay.sdk.merchant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
import java.io.IOException;
import java.text.ParseException;
import java.time.OffsetDateTime;
import org.hamcrest.Matchers;
import org.junit.Test;

public class GetMerchantVerificationTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnMerchantVerificationData()
      throws InterruptedException, IOException, ParseException {

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
                assertEquals(response.getId().toString(), "0198f0a2-d5f7-4888-817d-947cc44250ed");
                assertEquals(response.getHomepageUrl().toString(), "http://www.montavistatedx.com");
                assertEquals(response.getCompanyDescription(), "Coast Guard");
                assertEquals(response.getCompanyContactInfo().getName(), "Ivory R. Grace");
                assertEquals(response.getCompanyContactInfo().getCompanyName(), "Pomeroy's");
                assertEquals(
                    response.getCompanyContactInfo().getPhoneNumber().getLocalNumber(),
                    "701-268-0116");
                assertEquals(
                    response.getCompanyContactInfo().getPhoneNumber().getCountryCode(),
                    (Integer) 503);
                assertEquals(
                    response.getCompanyContactInfo().getAdressLine1(), "3124 Catherine Drive");
                assertNull(response.getCompanyContactInfo().getAdressLine2());
                assertEquals(response.getCompanyContactInfo().getState(), "ND");
                assertEquals(response.getCompanyContactInfo().getCity(), "Maxbass");
                assertEquals(response.getCompanyContactInfo().getCountry(), "US");
                assertThat(
                    response.getCompanyContactInfo().getCountryEnum(), Matchers.is(Country.US));
                assertEquals(response.getCompanyContactInfo().getZip(), "58760");
                assertEquals(response.getBusinessType(), BusinessType.DIGITAL_SALES);
                assertEquals(response.getSystemManagerName(), "Rachael R. Simon");
                assertEquals(response.getSystemManagerNumber().getLocalNumber(), "540-220-8465");
                assertEquals(response.getSystemManagerNumber().getCountryCode(), (Integer) 503);
                assertEquals(response.getSystemManagerEmail(), "RachaelRSimon@dayrep.com");
                assertEquals(response.getRecurringTokenRequest(), RecurringTokenPrivilege.INFINITE);
                assertEquals(response.getRecurringTokenRequestReason(), "testing");
                assertTrue(response.getAllowEmptyCvv());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertEquals(response.getUpdatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail();
                notifyCall();
              }
            });
    waitCall();
  }
}
