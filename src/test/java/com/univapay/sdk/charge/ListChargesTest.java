package com.univapay.sdk.charge;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.math.BigInteger;
import org.junit.Test;

public class ListChargesTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnListOfCharges() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges",
        token,
        200,
        ChargesFakeRR.listAllStoreChargesFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listCharges(new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Charge>>() {
              @Override
              public void getResponse(PaginatedList<Charge> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    response.getItems().get(0).getRequestedAmount(), BigInteger.valueOf(123));
                assertThat(response.getItems().get(0).getOnlyDirectCurrency(), is(true));
                assertThat(response.getItems().get(0).getDescriptor(), is("test descriptor"));
                assertThat(response.getItems().get(1).getOnlyDirectCurrency(), is(false));
                assertThat(response.getItems().get(1).getDescriptor(), is(nullValue()));
                assertEquals(
                    response.getItems().get(1).getRequestedAmount(), BigInteger.valueOf(1000));
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
  public void shouldPerformSearch() throws UnivapayException, IOException {

    //        final TransactionTokenId transactionTokenId = new
    // TransactionTokenId(UUID.randomUUID());
    //        final String fullPath = "/charges?" +
    //                "card_number=4111111111111111&" +
    //                "exp_month=6&exp_year=2019&" +
    //                "amount_to=10000&mode=TEST&" +
    //                "transaction_token_id="+transactionTokenId.toString()+"&" +
    //                "last_four=1234&" +
    //                "phone=7012680116&" +
    //                "name=some%20name&" +
    //                "currency=JPY&" +
    //                "from=Mon%20Sep%2011%2008:10:21%20JST%202017&" +
    //                "to=Sat%20Nov%2011%2008:10:21%20JST%202017&" +
    //                "amount_from=5000&" +
    //                "email=some@email.com&metadata=skate%20board";
    //
    //        MockRRGenerator mockRRGenerator = new MockRRGenerator();
    //        mockRRGenerator.GenerateMockRequestResponse("GET",fullPath,
    //                token, 200, "{\n" +
    //                        "    \"items\": [],\n" +
    //                        "    \"has_more\": false\n" +
    //                        "}");
    //
    //        UnivapaySDK univapay = UnivapaySDK.createInstanceWithLoginToken(token,
    // "http://localhost:" + PORT, null, false);
    //
    //
    //        univapay.listCharges()
    //                .withCardChargeSearch(
    //                        new CardChargeSearch()
    //                                .withLastFour("1234")
    //                                .withName("some name")
    //                                .withExpMonth(6)
    //                                .withExpYear(2019)
    //                                .withMoneyRange(
    //                                        new MoneyRange()
    //                                                .withMinimumAmount(BigInteger.valueOf(5000))
    //                                                .withMaximumAmount(BigInteger.valueOf(10000))
    //                                )
    //                                .withCardNumber("4111111111111111")
    //                                .withCurrency("JPY")
    //                                .withEmail("some@email.com")
    //                                .withMode(ProcessingMode.TEST)
    //                                .withTransactionTokenId(transactionTokenId)
    //                                .withPhone("7012680116")
    //                                .withTimeRange(
    //                                        new TimeRange()
    //                                                .withFromDate(
    //
    // DateTime.parse("2017-09-11T08:10:21.000000+09:00").toDate()
    //                                                )
    //                                                .withToDate(
    //
    // DateTime.parse("2017-11-11T08:10:21.000000+09:00").toDate()
    //                                                )
    //                                )
    //                )
    //                .withMetadataSearch("skate board")
    //                .build()
    //                .dispatch();
    //
    //        verify(getRequestedFor(urlEqualTo(fullPath)));
  }
}
