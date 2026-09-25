package com.univapay.sdk.transactiontoken;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ExternalCardPayment;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.transactiontoken.ExternalCardPaymentData;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.CardCategory;
import com.univapay.sdk.types.CardSubBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import org.junit.jupiter.api.Test;

class ExternalCardTransactionTokenTest extends GenericTest {

  @Test
  void shouldPostAndReturnExternalCardTransactionToken() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        JsonLoader.loadJson("responses/transactiontoken/post-external-card.json"),
        JsonLoader.loadJson("requests/transactiontoken/post-external-card.json"));

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    TransactionTokenWithData response =
        univapay
            .createTransactionToken(
                new ExternalCardPayment(Gateway.EVO_PAYMENT_EXTERNAL_CARD, "txn_1"),
                TransactionTokenType.ONE_TIME)
            .build()
            .dispatch();

    assertThat(response.getPaymentTypeName(), is(PaymentTypeName.EXTERNAL_CARD));

    ExternalCardPaymentData data = response.getData().asExternalCardPaymentData();
    assertThat(data.getExternal().getGateway(), is(Gateway.EVO_PAYMENT_EXTERNAL_CARD));
    assertThat(data.getExternal().getGatewayTransactionId(), is("txn_1"));
    // The card block is only present once the external charge has succeeded.
    assertNull(data.getCard());
  }

  @Test
  void shouldReadExternalCardPaymentDataWithCardAndBilling() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        JsonLoader.loadJson("responses/transactiontoken/get-external-card-full.json"));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TransactionTokenWithData response =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .build()
            .dispatch();

    assertThat(response.getPaymentTypeName(), is(PaymentTypeName.EXTERNAL_CARD));

    ExternalCardPaymentData data = response.getData().asExternalCardPaymentData();
    assertThat(data.getExternal().getGateway(), is(Gateway.EVO_PAYMENT_EXTERNAL_CARD));
    assertThat(data.getExternal().getGatewayTransactionId(), is("txn_1"));

    assertThat(data.getCard().getCardholder(), is("John Doe"));
    assertThat(data.getCard().getCardBin(), is("424242"));
    assertThat(data.getCard().getLastFour(), is(4242));
    assertThat(data.getCard().getBrandEnum(), is(CardBrand.VISA));
    assertThat(data.getCard().getSubBrand(), is(CardSubBrand.NONE));
    assertThat(data.getCard().getCategory(), is(CardCategory.CLASSIC));
    assertThat(data.getCard().getIssuer(), is("test issuer"));
    assertThat(data.getCard().getCountryEnum(), is(Country.JAPAN));

    assertThat(data.getBilling().getLine1(), is("somewhere"));
    assertThat(data.getBilling().getCity(), is("TYO"));
    assertThat(data.getBilling().getZip(), is("111-1111"));
    assertThat(data.getBilling().getCountryEnum(), is(Country.JAPAN));
    assertThat(data.getBilling().getPhoneNumber().getCountryCode(), is(81));
    assertThat(data.getBilling().getPhoneNumber().getLocalNumber(), is("111-1111-1111"));
  }
}
