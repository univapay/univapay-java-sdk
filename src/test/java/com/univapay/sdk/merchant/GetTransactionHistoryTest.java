package com.univapay.sdk.merchant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.merchant.*;
import com.univapay.sdk.models.response.merchant.transaction.OnlineTransactionData;
import com.univapay.sdk.models.response.merchant.transaction.QrMpmTransactionData;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.PaidyBrand;
import com.univapay.sdk.types.brand.QrCpmBrand;
import com.univapay.sdk.types.brand.QrMpmBrand;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.MerchantsFakeRR;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import org.junit.Test;

public class GetTransactionHistoryTest extends GenericTest {

  private final UnivapaySDK univapay = createTestInstance(AuthType.JWT);
  private final MockRRGenerator mockRRGenerator = new MockRRGenerator();

  @Test
  public void shouldReadCorrectlyTransactionHistoryContainingCardPayments()
      throws UnivapayException, IOException {

    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getGetStoreTransactionHistoryFakeResponseCreditCard);

    PaginatedList<Transaction> response =
        univapay
            .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
            .build()
            .dispatch();

    assertEquals(1, response.getItems().size());
    Transaction transaction = response.getItems().get(0);

    assertEquals(TransactionType.CHARGE, transaction.getTransactionType());
    assertEquals(PaymentTypeName.CARD, transaction.getPaymentType());

    CardTransactionData cardData = transaction.getUserData().asCardTransactionData();

    assertEquals(CardBrand.MASTERCARD, cardData.getCardBrand());
    assertEquals(CardBrand.MASTERCARD, cardData.getBrand());

    assertEquals("test name", cardData.getCardHolderName());

    //noinspection deprecation
    assertNull("This will be removed in the future so will return null", cardData.getGateway());
  }

  @Test
  public void shouldReadCorrectlyTransactionHistoryContainingConvenienceStorePayments()
      throws UnivapayException, IOException {
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getGetStoreTransactionHistoryFakeResponseConvenienceStore);

    PaginatedList<Transaction> response =
        univapay
            .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
            .build()
            .dispatch();

    assertEquals(1, response.getItems().size());
    Transaction transaction = response.getItems().get(0);

    assertEquals(TransactionType.CHARGE, transaction.getTransactionType());
    assertEquals(PaymentTypeName.KONBINI, transaction.getPaymentType());

    KonbiniTransactionData userData = transaction.getUserData().asKonbiniTransactionData();

    assertEquals(Konbini.FAMILY_MART, userData.getConvenienceStore());
    assertEquals(Konbini.FAMILY_MART, userData.getBrand());

    assertEquals("test user", userData.getCustomerName());

    //noinspection deprecation
    assertNull("This will be removed in the future so will return null", userData.getGateway());
  }

  @Test
  public void shouldReadCorrectlyTransactionHistoryContainingQrCpmPayments()
      throws UnivapayException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getGetStoreTransactionHistoryFakeResponseQrCpm);

    PaginatedList<Transaction> response =
        univapay
            .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
            .build()
            .dispatch();

    assertEquals(1, response.getItems().size());
    Transaction transaction = response.getItems().get(0);

    assertEquals(TransactionType.CHARGE, transaction.getTransactionType());
    assertEquals(PaymentTypeName.QR_SCAN, transaction.getPaymentType());

    QRScanTransactionData userData = transaction.getUserData().asQRScanTransactionData();

    // It is possible to send in the request, but not required
    assertNull(userData.getCardholderEmailAddress());

    assertEquals(QrCpmBrand.YuchoPay, userData.getBrand());

    //noinspection deprecation
    assertNull("This will be removed in the future so will return null", userData.getGateway());
  }

  @Test
  public void shouldReadCorrectlyTransactionHistoryContainingQrMpmPayments()
      throws UnivapayException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getGetStoreTransactionHistoryFakeResponseQrMpm);

    PaginatedList<Transaction> response =
        univapay
            .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
            .build()
            .dispatch();

    assertEquals(1, response.getItems().size());
    Transaction transaction = response.getItems().get(0);

    assertEquals(TransactionType.CHARGE, transaction.getTransactionType());
    assertEquals(PaymentTypeName.QR_MERCHANT, transaction.getPaymentType());

    QrMpmTransactionData userData = transaction.getUserData().asQrMpmTransactionData();

    assertNull(userData.getCardholderEmailAddress());

    assertEquals(QrMpmBrand.WeChat, userData.getBrand());

    //noinspection deprecation
    assertNull("This will be removed in the future so will return null", userData.getGateway());
  }

  @Test
  public void shouldReadCorrectlyTransactionHistoryContainingOnlinePayments()
      throws UnivapayException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getGetStoreTransactionHistoryFakeResponseOnline);

    PaginatedList<Transaction> response =
        univapay
            .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
            .build()
            .dispatch();

    assertEquals(1, response.getItems().size());
    Transaction transaction = response.getItems().get(0);

    assertEquals(TransactionType.CHARGE, transaction.getTransactionType());
    assertEquals(PaymentTypeName.ONLINE, transaction.getPaymentType());

    OnlineTransactionData userData = transaction.getUserData().asOnlineTransactionData();

    assertEquals("test@univapay.com", userData.getCardholderEmailAddress());
    assertEquals(OnlineBrand.ALIPAY, userData.getBrand());

    //noinspection deprecation
    assertNull("This will be removed in the future so will return null", userData.getGateway());
  }

  @Test
  public void shouldReadCorrectlyTransactionHistoryContainingPaidyPayments()
      throws UnivapayException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getGetStoreTransactionHistoryFakeResponsePaidy);

    PaginatedList<Transaction> response =
        univapay
            .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
            .build()
            .dispatch();

    assertEquals(1, response.getItems().size());
    Transaction transaction = response.getItems().get(0);

    assertEquals(TransactionType.CHARGE, transaction.getTransactionType());
    assertEquals(PaymentTypeName.PAIDY, transaction.getPaymentType());

    PaidyTransactionData userData = transaction.getUserData().asPaidyTransactionData();

    assertEquals(PaidyBrand.Paidy, userData.getBrand());
    assertEquals("8112341234", userData.getCardholderPhoneNumber());
    assertEquals("test@univapay.com", userData.getCardholderEmailAddress());
    //noinspection deprecation
    assertNull("This will be removed in the future so will return null", userData.getGateway());
  }

  @Test
  public void shouldRequestAndReturnTransactionHistory() throws InterruptedException {

    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getStoreTransactionHistoryFakeResponse);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Transaction>>() {
              @Override
              public void getResponse(PaginatedList<Transaction> response) {

                assertFalse(response.getHasMore());
                Transaction firstTransaction = response.getItems().get(0);
                Transaction secondTransaction = response.getItems().get(1);
                Transaction thirdTransaction = response.getItems().get(2);

                assertEquals(
                    firstTransaction.getStoreId().toString(),
                    "45facc11-efc8-4156-8ef3-e363a70a54c3");
                assertEquals(
                    firstTransaction.getResourceId().toString(),
                    "e1771339-b989-4a43-99c1-5e35d8008426");
                assertEquals(
                    secondTransaction.getResourceId().toString(),
                    "bae4fd94-aace-437c-bf63-8908008dff81");
                assertEquals(
                    thirdTransaction.getResourceId().toString(),
                    "6da25a85-754c-41db-97ec-c2e50f7591d5");
                assertEquals(firstTransaction.getAmount(), BigInteger.valueOf(50000));
                assertEquals(secondTransaction.getAmount(), BigInteger.valueOf(400));
                assertEquals(thirdTransaction.getAmount(), BigInteger.valueOf(700));
                assertEquals(firstTransaction.getCurrency(), "JPY");
                assertEquals(secondTransaction.getCurrency(), "CAD");
                assertEquals(thirdTransaction.getCurrency(), "EUR");
                assertEquals(firstTransaction.getTransactionType(), TransactionType.CHARGE);
                assertEquals(secondTransaction.getTransactionType(), TransactionType.CHARGE);
                assertEquals(thirdTransaction.getTransactionType(), TransactionType.REFUND);
                assertEquals(firstTransaction.getStatus(), TransactionStatus.FAILED);
                assertEquals(secondTransaction.getStatus(), TransactionStatus.FAILED);
                assertEquals(thirdTransaction.getStatus(), TransactionStatus.SUCCESSFUL);
                assertThat(firstTransaction.getMetadata().get("float"), is("10.3"));

                assertEquals(firstTransaction.getCreatedOn(), parsedDate);
                assertEquals(secondTransaction.getCreatedOn(), parsedDate);
                assertEquals(thirdTransaction.getCreatedOn(), parsedDate);
                assertEquals(firstTransaction.getMode(), ProcessingMode.TEST);
                assertEquals(secondTransaction.getMode(), ProcessingMode.TEST);
                assertEquals(thirdTransaction.getMode(), ProcessingMode.TEST);
                assertThat(firstTransaction.getMerchantName(), is("merchant_name"));
                assertThat(firstTransaction.getPaymentType(), is(PaymentTypeName.CARD));
                assertThat(
                    firstTransaction.getUserData().asCardTransactionData().getCardBrand(),
                    is(CardBrand.MASTERCARD));
                assertThat(
                    firstTransaction.getUserData().asCardTransactionData().getCardBrand(),
                    is(CardBrand.MASTERCARD));
                assertThat(
                    firstTransaction.getUserData().asCardTransactionData().getCardHolderName(),
                    is("CARD HOLDER0"));
                assertThat(
                    firstTransaction.getUserData().asCardTransactionData().getGateway(),
                    is(nullValue()));
                assertThat(
                    firstTransaction.getUserData().getTransactionType(),
                    is(TransactionType.CHARGE));
                assertThat(
                    firstTransaction
                        .getUserData()
                        .asChargeUserData()
                        .getRefunds()
                        .get(0)
                        .getRefundId()
                        .toString(),
                    is("11e922c2-2b72-7cca-9cf3-1b0af83dce8a"));
                assertThat(
                    firstTransaction
                        .getUserData()
                        .asChargeUserData()
                        .getRefunds()
                        .get(0)
                        .getReason(),
                    is(RefundReason.DUPLICATE));
                assertThat(
                    firstTransaction
                        .getUserData()
                        .asChargeUserData()
                        .getRefunds()
                        .get(0)
                        .getAmount(),
                    is(BigInteger.valueOf(9000)));
                assertThat(
                    firstTransaction
                        .getUserData()
                        .asChargeUserData()
                        .getRefunds()
                        .get(0)
                        .getCurrency(),
                    is("JPY"));
                assertThat(
                    firstTransaction
                        .getUserData()
                        .asChargeUserData()
                        .getRefunds()
                        .get(0)
                        .getAmountFormatted(),
                    is(BigDecimal.valueOf(9000)));
                assertThat(secondTransaction.getPaymentType(), is(PaymentTypeName.KONBINI));
                assertThat(
                    secondTransaction.getUserData().asKonbiniTransactionData().getCustomerName(),
                    is("Konbini Customer"));
                assertThat(
                    secondTransaction
                        .getUserData()
                        .asKonbiniTransactionData()
                        .getConvenienceStore(),
                    is(Konbini.LAWSON));
                assertThat(
                    secondTransaction.getUserData().asKonbiniTransactionData().getGateway(),
                    is(nullValue()));
                assertThat(
                    secondTransaction.getUserData().asChargeUserData().getRefunds().size(), is(0));
                assertThat(thirdTransaction.getPaymentType(), is(PaymentTypeName.PAIDY));
                assertThat(
                    thirdTransaction
                        .getUserData()
                        .asPaidyTransactionData()
                        .getCardholderEmailAddress(),
                    is("cardholder_mail@univapay.com"));
                assertThat(
                    thirdTransaction
                        .getUserData()
                        .asPaidyTransactionData()
                        .getCardholderPhoneNumber(),
                    is("810312345678"));

                assertThat(
                    thirdTransaction.getUserData().getTransactionType(),
                    is(TransactionType.REFUND));
                assertThat(
                    thirdTransaction.getUserData().asRefundUserData().getRefundReason(),
                    is(RefundReason.CUSTOMER_REQUEST));
                assertThat(
                    response.getItems().get(3).getPaymentType(), is(PaymentTypeName.QR_SCAN));
                assertThat(
                    response
                        .getItems()
                        .get(3)
                        .getUserData()
                        .asQRScanTransactionData()
                        .getCardholderEmailAddress(),
                    is("qr_email@univapay.com"));
                assertThat(
                    response.getItems().get(3).getUserData().asQRScanTransactionData().getGateway(),
                    is(nullValue()));
                assertThat(
                    firstTransaction.getUserData().asCardTransactionData().getCardBrand(),
                    is(CardBrand.MASTERCARD));
                assertThat(
                    response.getItems().get(4).getPaymentType(), is(PaymentTypeName.APPLE_PAY));
                assertThat(
                    response
                        .getItems()
                        .get(4)
                        .getUserData()
                        .asApplePayTransactionData()
                        .getCardBrand(),
                    is(CardBrand.MASTERCARD));
                assertThat(
                    response
                        .getItems()
                        .get(4)
                        .getUserData()
                        .asApplePayTransactionData()
                        .getCardBrand(),
                    is(CardBrand.MASTERCARD));
                assertThat(
                    response
                        .getItems()
                        .get(4)
                        .getUserData()
                        .asApplePayTransactionData()
                        .getCardHolderName(),
                    is("CARD HOLDER4"));
                assertThat(
                    response
                        .getItems()
                        .get(4)
                        .getUserData()
                        .asApplePayTransactionData()
                        .getGateway(),
                    is(nullValue()));
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
  public void shouldRequestAndReturnTransactionHistoryWithDateRange() throws Exception {

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");
    final String dateString = urlEncode(formatDate(parsedDate));
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history?from="
            + dateString
            + "&to="
            + dateString
            + "&status=successful&type=charge&mode=test&search=search&all=true",
        jwt,
        200,
        MerchantsFakeRR.getStoreTransactionHistoryFakeResponse);

    univapay
        .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
        .withFromDate(parsedDate)
        .withToDate(parsedDate)
        .withStatus(TransactionStatus.SUCCESSFUL)
        .withTransactionType(TransactionType.CHARGE)
        .withProcessingMode(ProcessingMode.TEST)
        .withSearch("search")
        .withAll(true)
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Transaction>>() {
              @Override
              public void getResponse(PaginatedList<Transaction> response) {

                assertFalse(response.getHasMore());
                assertEquals(
                    response.getItems().get(0).getStoreId().toString(),
                    "45facc11-efc8-4156-8ef3-e363a70a54c3");
                assertEquals(
                    response.getItems().get(0).getResourceId().toString(),
                    "e1771339-b989-4a43-99c1-5e35d8008426");
                assertEquals(
                    response.getItems().get(1).getResourceId().toString(),
                    "bae4fd94-aace-437c-bf63-8908008dff81");
                assertEquals(
                    response.getItems().get(2).getResourceId().toString(),
                    "6da25a85-754c-41db-97ec-c2e50f7591d5");
                assertEquals(response.getItems().get(0).getAmount(), BigInteger.valueOf(50000));
                assertEquals(response.getItems().get(1).getAmount(), BigInteger.valueOf(400));
                assertEquals(response.getItems().get(2).getAmount(), BigInteger.valueOf(700));
                assertEquals(response.getItems().get(0).getCurrency(), "JPY");
                assertEquals(response.getItems().get(1).getCurrency(), "CAD");
                assertEquals(response.getItems().get(2).getCurrency(), "EUR");
                assertEquals(
                    response.getItems().get(0).getTransactionType(), TransactionType.CHARGE);
                assertEquals(
                    response.getItems().get(1).getTransactionType(), TransactionType.CHARGE);
                assertEquals(
                    response.getItems().get(2).getTransactionType(), TransactionType.REFUND);
                assertEquals(response.getItems().get(0).getStatus(), TransactionStatus.FAILED);
                assertEquals(response.getItems().get(1).getStatus(), TransactionStatus.FAILED);
                assertEquals(response.getItems().get(2).getStatus(), TransactionStatus.SUCCESSFUL);
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                assertEquals(response.getItems().get(2).getCreatedOn(), parsedDate);
                assertEquals(response.getItems().get(0).getMode(), ProcessingMode.TEST);
                assertEquals(response.getItems().get(1).getMode(), ProcessingMode.TEST);
                assertEquals(response.getItems().get(2).getMode(), ProcessingMode.TEST);
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
