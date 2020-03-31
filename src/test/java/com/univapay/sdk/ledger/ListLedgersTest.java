package com.univapay.sdk.ledger;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import org.junit.Assert;
import org.junit.Test;

public class ListLedgersTest extends GenericTest {

  @Test
  public void shouldRequestListOfLedgers() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers",
        token,
        204,
        LedgersFakeRR.getMerchantBalanceFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listLedgers(new TransferId("8e5269b8-ec32-11e6-88ca-fb17c7a225eb"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Ledger>>());

    waitCall();

    verify(getRequestedFor(urlEqualTo("/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers")));
  }

  @Test
  public void shouldReturnListOfLedgers() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers",
        token,
        200,
        LedgersFakeRR.getMerchantBalanceFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

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
                assertEquals(sample.getId().toString(), "f8dda07a-18f3-11e7-915f-7782f7d3f527");
                Assert.assertEquals(
                    sample.getStoreId().toString(), "f1055366-18f3-11e7-ac9a-df664f5d7adc");
                assertEquals(sample.getAmount(), BigInteger.valueOf(74735));
                assertEquals(sample.getCurrency(), "eur");
                assertEquals(sample.getAmountFormatted(), BigDecimal.valueOf(747.35));
                assertEquals(sample.getFlatFeeAmount(), BigDecimal.valueOf(30));
                assertEquals(sample.getFlatFeeCurrency(), "eur");
                assertEquals(sample.getAmountFormatted(), BigDecimal.valueOf(0.3));
                assertEquals(sample.getExchangeRate(), 1);
                Assert.assertEquals(sample.getOrigin(), LedgerOrigin.CHARGE);
                assertEquals(sample.getNote(), "some note here");
                assertEquals(sample.getCreatedOn(), 1491282178946L);
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  public void shouldRequestListOfLedgersWithQueryParams() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers?all=true&currency=JPY",
        token,
        204,
        LedgersFakeRR.getMerchantBalanceFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

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
