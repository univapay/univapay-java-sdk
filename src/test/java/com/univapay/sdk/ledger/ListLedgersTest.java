package com.univapay.sdk.ledger;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.ledger.Ledger;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.LedgerOrigin;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.LedgersFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ListLedgersTest extends GenericTest {

  @Test
  void shouldRequestListOfLedgers() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers",
        jwt,
        204,
        LedgersFakeRR.getMerchantBalanceFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listLedgers(new TransferId("8e5269b8-ec32-11e6-88ca-fb17c7a225eb"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Ledger>>());

    waitCall();

    verify(getRequestedFor(urlEqualTo("/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers")));
  }

  @Test
  void shouldReturnListOfLedgers() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers",
        jwt,
        200,
        LedgersFakeRR.getMerchantBalanceFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listLedgers(new TransferId("8e5269b8-ec32-11e6-88ca-fb17c7a225eb"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Ledger>>() {
              @Override
              public void getResponse(PaginatedList<Ledger> response) {
                assertFalse(response.getHasMore());
                assertFalse(response.getItems().isEmpty());
                Ledger sample = response.getItems().get(1);
                assertEquals("f8dda07a-18f3-11e7-915f-7782f7d3f527", sample.getId().toString());
                assertEquals(
                    "f1055366-18f3-11e7-ac9a-df664f5d7adc", sample.getStoreId().toString());
                assertEquals(sample.getAmount(), BigInteger.valueOf(74735));
                assertEquals("eur", sample.getCurrency());
                assertEquals(sample.getAmountFormatted(), BigDecimal.valueOf(747.35));
                assertEquals(sample.getFlatFeeAmount(), BigDecimal.valueOf(30));
                assertEquals("eur", sample.getFlatFeeCurrency());
                assertEquals(sample.getAmountFormatted(), BigDecimal.valueOf(0.3));
                assertEquals(1, sample.getExchangeRate());
                assertEquals(LedgerOrigin.CHARGE, sample.getOrigin());
                assertEquals("some note here", sample.getNote());
                assertEquals(1491282178946L, sample.getCreatedOn());
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldRequestListOfLedgersWithQueryParams() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers?all=true&currency=JPY",
        jwt,
        204,
        LedgersFakeRR.getMerchantBalanceFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listLedgers(new TransferId("8e5269b8-ec32-11e6-88ca-fb17c7a225eb"))
        .withAll(true)
        .withCurrency("JPY")
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Ledger>>());

    waitCall();

    verify(
        getRequestedFor(
            urlEqualTo(
                "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers?all=true&currency=JPY")));
  }
}
