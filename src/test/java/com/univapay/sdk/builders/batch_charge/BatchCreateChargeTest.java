package com.univapay.sdk.builders.batch_charge;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.NoopSleeper;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class BatchCreateChargeTest extends GenericTest {

  String requestTemplate =
      "{\n"
          + "  \"transaction_token_id\": \"%s\",\n"
          + "  \"amount\": %d,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"capture\": true\n"
          + "}";
  String responseTemplate =
      "{\n"
          + "  \"id\": \"%s\",\n"
          + "  \"store_id\": \"00000000-0000-0000-0001-000000000000\",\n"
          + "  \"requested_amount\": %d,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"transaction_token_id\": \"%s\",\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": null\n"
          + "}";
  String getResponseTemplate =
      "{\n"
          + "  \"id\": \"%s\",\n"
          + "  \"store_id\": \"00000000-0000-0000-0001-000000000000\",\n"
          + "  \"requested_amount\": %d,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": %d,\n"
          + "  \"charged_currency\": \"JPY\",\n"
          + "  \"transaction_token_id\": \"%s\",\n"
          + "  \"status\": \"successful\",\n"
          + "  \"error\": null,\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  @Test
  public void shouldCreateMultipleRequests() throws Exception {

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();

    for (int i = 1; i <= 3; i++) {
      int amount = i * 1000;
      String tt = String.format("00000000-0000-0000-0000-0000000000%d0", i);
      String cid = String.format("00000000-0000-0000-0000-0000000000%d1", i);

      mockRRGenerator.GenerateMockRequestResponse(
          "POST",
          "/charges",
          appToken,
          secret,
          200,
          String.format(responseTemplate, cid, amount, tt),
          String.format(requestTemplate, tt, amount));
      mockRRGenerator.GenerateMockRequestResponse(
          "GET",
          "/stores/00000000-0000-0000-0001-000000000000/charges/" + cid + "?polling=true",
          appToken,
          secret,
          200,
          String.format(getResponseTemplate, cid, amount, amount, tt));
    }

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    CreateChargeResult[] results =
        univapay
            .batchCreateCharge()
            .add(
                univapay.createCharge(
                    new TransactionTokenId("00000000-0000-0000-0000-000000000010"),
                    BigInteger.valueOf(1000),
                    "JPY"))
            .add(
                univapay.createCharge(
                    new TransactionTokenId("00000000-0000-0000-0000-000000000020"),
                    BigInteger.valueOf(2000),
                    "JPY"))
            .add(
                univapay.createCharge(
                    new TransactionTokenId("00000000-0000-0000-0000-000000000030"),
                    BigInteger.valueOf(3000),
                    "JPY"))
            .execute();

    assertEquals(3, results.length);

    assertResults(Arrays.asList(results));
  }

  @Test
  public void shouldCreateMultipleRequestsAsync() throws Exception {

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();

    for (int i = 1; i <= 3; i++) {
      int amount = i * 1000;
      String tt = String.format("00000000-0000-0000-0000-0000000000%d0", i);
      String cid = String.format("00000000-0000-0000-0000-0000000000%d1", i);

      mockRRGenerator.GenerateMockRequestResponse(
          "POST",
          "/charges",
          appToken,
          secret,
          200,
          String.format(responseTemplate, cid, amount, tt),
          String.format(requestTemplate, tt, amount));
      mockRRGenerator.GenerateMockRequestResponse(
          "GET",
          "/stores/00000000-0000-0000-0001-000000000000/charges/" + cid + "?polling=true",
          appToken,
          secret,
          200,
          String.format(getResponseTemplate, cid, amount, amount, tt));
    }

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final List<CreateChargeResult> results = new ArrayList<>();

    univapay
        .batchCreateCharge()
        .add(
            univapay.createCharge(
                new TransactionTokenId("00000000-0000-0000-0000-000000000010"),
                BigInteger.valueOf(1000),
                "JPY"))
        .add(
            univapay.createCharge(
                new TransactionTokenId("00000000-0000-0000-0000-000000000020"),
                BigInteger.valueOf(2000),
                "JPY"))
        .add(
            univapay.createCharge(
                new TransactionTokenId("00000000-0000-0000-0000-000000000030"),
                BigInteger.valueOf(3000),
                "JPY"))
        .execute(
            new BatchChargeCallback() {
              @Override
              public void processResult(CreateChargeResult chargeResult) {
                results.add(chargeResult);
                if (results.size() == 3) notifyCall();
              }
            });

    assertEquals(0, results.size());

    waitCall();

    assertEquals(3, results.size());

    assertResults(results);
  }

  private void assertResults(List<CreateChargeResult> results) throws Exception {

    int i = 1;

    for (CreateChargeResult result : results) {
      checkResultException(result);
      assertEquals(
          String.format("00000000-0000-0000-0000-0000000000%d0", i),
          result.charge.getTransactionTokenId().toString());
      assertEquals(
          String.format("00000000-0000-0000-0000-0000000000%d1", i),
          result.charge.getId().toString());
      Assert.assertEquals(ChargeStatus.SUCCESSFUL, result.charge.getStatus());
      assertNull(result.charge.getError());
      i++;
    }
  }

  private void checkResultException(CreateChargeResult result) throws Exception {

    if (result.createChargeException != null) {
      throw result.createChargeException;
    }
    if (result.statusCheckException != null) {
      throw result.statusCheckException;
    }
  }

  @Test
  public void shouldReturnException() throws Exception {

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();

    for (int i = 1; i <= 3; i++) {
      int amount = i * 1000;
      String tt = String.format("00000000-0000-0000-0000-0000000000%d0", i);
      String cid = String.format("00000000-0000-0000-0000-0000000000%d1", i);

      if (i == 2) {
        // Error!
        mockRRGenerator.GenerateMockRequestResponse(
            "POST",
            "/charges",
            appToken,
            secret,
            503,
            "{}",
            String.format(requestTemplate, tt, amount));
      } else {
        mockRRGenerator.GenerateMockRequestResponse(
            "POST",
            "/charges",
            appToken,
            secret,
            200,
            String.format(responseTemplate, cid, amount, tt),
            String.format(requestTemplate, tt, amount));
      }
      if (i == 3) {
        // Error!
        mockRRGenerator.GenerateMockRequestResponse(
            "GET",
            "/stores/00000000-0000-0000-0001-000000000000/charges/" + cid + "?polling=true",
            appToken,
            secret,
            503,
            "{}");
      } else {
        mockRRGenerator.GenerateMockRequestResponse(
            "GET",
            "/stores/00000000-0000-0000-0001-000000000000/charges/" + cid + "?polling=true",
            appToken,
            secret,
            200,
            String.format(getResponseTemplate, cid, amount, amount, tt));
      }
    }

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    AbstractBatchCreateCharge batch = spy(univapay.batchCreateCharge());
    when(batch.createSleeper()).thenReturn(new NoopSleeper());
    CreateChargeResult[] results =
        batch
            .add(
                univapay.createCharge(
                    new TransactionTokenId("00000000-0000-0000-0000-000000000010"),
                    BigInteger.valueOf(1000),
                    "JPY"))
            .add(
                univapay.createCharge(
                    new TransactionTokenId("00000000-0000-0000-0000-000000000020"),
                    BigInteger.valueOf(2000),
                    "JPY"))
            .add(
                univapay.createCharge(
                    new TransactionTokenId("00000000-0000-0000-0000-000000000030"),
                    BigInteger.valueOf(3000),
                    "JPY"))
            .execute();

    assertEquals(3, results.length);

    Charge c;

    CreateChargeResult result = results[0];
    checkResultException(result);
    c = result.charge;
    assertEquals("00000000-0000-0000-0000-000000000010", c.getTransactionTokenId().toString());
    assertEquals("00000000-0000-0000-0000-000000000011", c.getId().toString());
    Assert.assertEquals(ChargeStatus.SUCCESSFUL, c.getStatus());
    assertNull(c.getError());

    result = results[1];
    assertThat(result.createChargeException, instanceOf(UnivapayException.class));
    assertNull(result.charge);

    result = results[2];
    assertThat(result.statusCheckException, instanceOf(UnivapayException.class));
    assertNotNull(result.charge);
    c = result.charge;
    assertEquals("00000000-0000-0000-0000-000000000030", c.getTransactionTokenId().toString());
    assertEquals("00000000-0000-0000-0000-000000000031", c.getId().toString());
    Assert.assertEquals(ChargeStatus.PENDING, c.getStatus());
  }
}
