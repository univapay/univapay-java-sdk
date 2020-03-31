package com.univapay.sdk.transactiontoken;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.adapters.JsonAdapters;
import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.IdempotencyStatus;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.RequestMethod;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.metadataadapter.ManyTypesAdapter;
import com.univapay.sdk.utils.metadataadapter.ManyTypesMetadata;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import com.univapay.sdk.utils.mockcontent.ErrorsFakeRR;
import com.univapay.sdk.utils.mockcontent.TemporaryTransactionTokenAliasFakeRR;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CreateChargeWithTransactionTokenAliasTest extends GenericTest {
  private final ManyTypesAdapter adapter = new ManyTypesAdapter();
  private final ManyTypesMetadata metadata =
      new ManyTypesMetadata(
          "hola",
          BigInteger.valueOf(989223112),
          BigDecimal.valueOf(1234.7981723987),
          true,
          3.141592F);

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup() {
    reset();
  }

  @Test
  public void shouldCreateChargeWithATokenAlias() throws Exception {
    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");
    IdempotencyKey idempotencyKey = new IdempotencyKey("idempotency-key-alias");
    IdempotencyStatus idempotencyStatus = IdempotencyStatus.SUCCESSFULLY_STORED;

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2018-10-22T05:46:11.507166Z").toDate();
    final String descriptor = "test descriptor";
    final MetadataMap reqMetadata = new MetadataMap();
    reqMetadata.put("cod", String.valueOf(15984632));
    reqMetadata.put("prod", "electronics");

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse,
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            201,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            ChargesFakeRR.createFullChargeFakeRequest(
                BigInteger.valueOf(5000), JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            TestScenarioState.CREATE_CHARGE,
            TestScenarioState.DELETE_ALIAS,
            idempotencyKey,
            idempotencyStatus));

    stubFor(
        createStub(
            RequestMethod.DELETE,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            204,
            "{}",
            null,
            TestScenarioState.DELETE_ALIAS,
            null));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Charge charge =
        univapay
            .createChargeWithTokenAlias(
                storeId, aliasKey, new MoneyLike(BigInteger.valueOf(1000), "JPY"), true)
            .withCaptureAt(captureAt)
            .withDescriptor(descriptor)
            .withOnlyDirectCurrency(true)
            .withMetadata(reqMetadata)
            .withIdempotencyKey(idempotencyKey)
            .dispatch();

    assertThat(charge.getId().toString(), is(notNullValue()));
    assertThat(charge.getCaptureAt(), is(captureAt));
    assertThat(charge.getDescriptor(), is(descriptor));
    assertThat(charge.getOnlyDirectCurrency(), is(true));
    assertThat(charge.getMetadata().get("cod"), is(reqMetadata.get("cod")));
    assertThat(charge.getMetadata().get("prod"), is(reqMetadata.get("prod")));
    assertThat(charge.getIdempotencyStatus(), is(idempotencyStatus));
  }

  @Test
  public void shouldCreateChargeWithATokenAliasAsynchronously() throws Exception {
    reset();

    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2018-10-22T05:46:11.507166Z").toDate();
    final String descriptor = "test descriptor";
    final MetadataMap reqMetadata = new MetadataMap();
    reqMetadata.put("cod", String.valueOf(15984632));
    reqMetadata.put("prod", "electronics");

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse,
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            201,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            ChargesFakeRR.createFullChargeFakeRequest(
                BigInteger.valueOf(5000), JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            TestScenarioState.CREATE_CHARGE,
            TestScenarioState.DELETE_ALIAS));

    stubFor(
        createStub(
            RequestMethod.DELETE,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            204,
            "{}",
            null,
            TestScenarioState.DELETE_ALIAS,
            null));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .createChargeWithTokenAlias(
            storeId, aliasKey, new MoneyLike(BigInteger.valueOf(1000), "JPY"), true)
        .withCaptureAt(captureAt)
        .withDescriptor(descriptor)
        .withOnlyDirectCurrency(true)
        .withMetadata(reqMetadata)
        .dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge charge) {
                assertThat(charge.getId().toString(), is(notNullValue()));
                assertThat(charge.getCaptureAt(), is(captureAt));
                assertThat(charge.getDescriptor(), is(descriptor));
                assertThat(charge.getOnlyDirectCurrency(), is(true));
                assertThat(charge.getMetadata().get("cod"), is(reqMetadata.get("cod")));
                assertThat(charge.getMetadata().get("prod"), is(reqMetadata.get("prod")));
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

  @Test
  public void shouldCreatePollingChargeWithATokenAlias() throws Exception {
    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2018-10-22T05:46:11.507166Z").toDate();
    final String descriptor = "test descriptor";
    final MetadataMap reqMetadata = new MetadataMap();
    reqMetadata.put("cod", String.valueOf(15984632));
    reqMetadata.put("prod", "electronics");

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse,
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            201,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            ChargesFakeRR.createFullChargeFakeRequest(
                BigInteger.valueOf(5000), JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            TestScenarioState.CREATE_CHARGE,
            TestScenarioState.DELETE_ALIAS));

    stubFor(
        createStub(
            RequestMethod.DELETE,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            204,
            "{}",
            null,
            TestScenarioState.DELETE_ALIAS,
            TestScenarioState.AWAIT_CHARGE));

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/"
                + storeId.toString()
                + "/charges/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
            jwtCredentials,
            200,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), "successful", true),
            null,
            TestScenarioState.AWAIT_CHARGE,
            null));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Charge charge =
        univapay
            .createChargeWithTokenAlias(
                storeId, aliasKey, new MoneyLike(BigInteger.valueOf(1000), "JPY"), true)
            .withPolling()
            .withCaptureAt(captureAt)
            .withDescriptor(descriptor)
            .withOnlyDirectCurrency(true)
            .withMetadata(reqMetadata)
            .dispatch();

    assertThat(charge.getId().toString(), is(notNullValue()));
    assertThat(charge.getCaptureAt(), is(captureAt));
    assertThat(charge.getDescriptor(), is(descriptor));
    assertThat(charge.getOnlyDirectCurrency(), is(true));
    assertThat(charge.getMetadata().get("cod"), is(reqMetadata.get("cod")));
    assertThat(charge.getMetadata().get("prod"), is(reqMetadata.get("prod")));
  }

  @Test
  public void shouldCreatePollingChargeWithATokenAliasAsynchronously() throws Exception {
    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2018-10-22T05:46:11.507166Z").toDate();
    final String descriptor = "test descriptor";
    final MetadataMap reqMetadata = new MetadataMap();
    reqMetadata.put("cod", String.valueOf(15984632));
    reqMetadata.put("prod", "electronics");

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasWithNoMoneyResponse,
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            201,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            ChargesFakeRR.createFullChargeFakeRequest(
                BigInteger.valueOf(1000), JsonAdapters.dateTimePrinter.print(captureAt.getTime())),
            TestScenarioState.CREATE_CHARGE,
            TestScenarioState.DELETE_ALIAS));

    stubFor(
        createStub(
            RequestMethod.DELETE,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            204,
            "{}",
            null,
            TestScenarioState.DELETE_ALIAS,
            TestScenarioState.AWAIT_CHARGE));

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/"
                + storeId.toString()
                + "/charges/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
            jwtCredentials,
            200,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), "successful", true),
            null,
            TestScenarioState.AWAIT_CHARGE,
            null));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .createChargeWithTokenAlias(
            storeId, aliasKey, new MoneyLike(BigInteger.valueOf(1000), "JPY"), true)
        .withPolling()
        .withCaptureAt(captureAt)
        .withDescriptor(descriptor)
        .withOnlyDirectCurrency(true)
        .withMetadata(reqMetadata)
        .dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge charge) {
                assertThat(charge.getId().toString(), is(notNullValue()));
                assertThat(charge.getCaptureAt(), is(captureAt));
                assertThat(charge.getDescriptor(), is(descriptor));
                assertThat(charge.getOnlyDirectCurrency(), is(true));
                assertThat(charge.getMetadata().get("cod"), is(reqMetadata.get("cod")));
                assertThat(charge.getMetadata().get("prod"), is(reqMetadata.get("prod")));
                assertThat(charge.getStatus(), Is.is(ChargeStatus.SUCCESSFUL));
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

  @Test
  public void shouldThrowExceptionOnInactiveTokenAlias()
      throws IOException, UnivapayException, TimeoutException, InterruptedException {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("Transaction Token is not active");

    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse(false),
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .createChargeWithTokenAlias(
            storeId, aliasKey, new MoneyLike(BigInteger.valueOf(1000), "JPY"), true)
        .dispatch();
  }

  @Test
  public void shouldCreateChargeWithMoneyOfTemporaryToken() throws InterruptedException {
    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2018-10-22T05:46:11.507166Z").toDate();
    final String descriptor = "test descriptor";

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse,
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            201,
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), true),
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeRequest(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()),
                BigInteger.valueOf(5000),
                true),
            TestScenarioState.CREATE_CHARGE,
            TestScenarioState.DELETE_ALIAS));

    stubFor(
        createStub(
            RequestMethod.DELETE,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            204,
            "{}",
            null,
            TestScenarioState.DELETE_ALIAS,
            TestScenarioState.AWAIT_CHARGE));

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/"
                + storeId.toString()
                + "/charges/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
            jwtCredentials,
            200,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), "successful", true),
            null,
            TestScenarioState.AWAIT_CHARGE,
            null));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .createChargeWithTokenAlias(storeId, aliasKey, true)
        .withCaptureAt(captureAt)
        .withDescriptor(descriptor)
        .withOnlyDirectCurrency(true)
        .withMetadata(metadata, adapter)
        .dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge charge) {
                assertThat(charge.getId().toString(), is(notNullValue()));
                assertThat(charge.getCaptureAt(), is(captureAt));
                assertThat(charge.getDescriptor(), is(descriptor));
                assertThat(charge.getOnlyDirectCurrency(), is(true));
                assertThat(charge.getMetadata().get("string_value"), is(metadata.getStringValue()));
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

  @Test
  public void shouldThrowExceptionIfNoMoneySet()
      throws IOException, UnivapayException, TimeoutException, InterruptedException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Set charge amount and currency");

    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasWithNoMoneyResponse,
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay.createChargeWithTokenAlias(storeId, aliasKey, true).dispatch();
  }

  @Test
  public void shouldSkipDeleteTokenAliasWithOneTimeToken() throws InterruptedException {
    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2018-10-22T05:46:11.507166Z").toDate();
    final String descriptor = "test descriptor";

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse(
                true, TransactionTokenType.ONE_TIME),
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            201,
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), true),
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeRequest(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()),
                BigInteger.valueOf(5000),
                true),
            TestScenarioState.CREATE_CHARGE,
            TestScenarioState.AWAIT_CHARGE));

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/"
                + storeId.toString()
                + "/charges/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
            jwtCredentials,
            200,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), "successful", true),
            null,
            TestScenarioState.AWAIT_CHARGE,
            null));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .createChargeWithTokenAlias(storeId, aliasKey, true)
        .withCaptureAt(captureAt)
        .withDescriptor(descriptor)
        .withOnlyDirectCurrency(true)
        .withMetadata(metadata, adapter)
        .dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge charge) {
                assertThat(charge.getId().toString(), is(notNullValue()));
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

  @Test
  public void shouldRetryChargeCreationOnDescriptorFailure() throws Exception {
    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2018-10-22T05:46:11.507166Z").toDate();
    final String descriptor = "test descriptor";

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse,
            null,
            TestScenarioState.STARTED,
            TestScenarioState.DESCRIPTOR_ERROR));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            400,
            ErrorsFakeRR.descriptorNotSupportedError,
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeRequest(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()),
                BigInteger.valueOf(5000),
                true),
            TestScenarioState.DESCRIPTOR_ERROR,
            TestScenarioState.CREATE_CHARGE));

    stubFor(
        createStub(
            RequestMethod.POST,
            "/charges",
            jwtCredentials,
            201,
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), false),
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeRequest(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()),
                BigInteger.valueOf(5000),
                false),
            TestScenarioState.CREATE_CHARGE,
            TestScenarioState.DELETE_ALIAS));

    stubFor(
        createStub(
            RequestMethod.DELETE,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            204,
            "{}",
            null,
            TestScenarioState.DELETE_ALIAS,
            TestScenarioState.AWAIT_CHARGE));

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/"
                + storeId.toString()
                + "/charges/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
            jwtCredentials,
            200,
            ChargesFakeRR.createFullChargeFakeResponse(
                JsonAdapters.dateTimePrinter.print(captureAt.getTime()), "successful", false),
            null,
            TestScenarioState.AWAIT_CHARGE,
            null));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .createChargeWithTokenAlias(storeId, aliasKey, true)
        .withCaptureAt(captureAt)
        .withDescriptor(descriptor, true)
        .withOnlyDirectCurrency(true)
        .withMetadata(metadata, adapter)
        .dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge charge) {
                assertThat(charge.getId().toString(), is(notNullValue()));
                assertThat(charge.getCaptureAt(), is(captureAt));
                assertNull(charge.getDescriptor());
                assertThat(charge.getOnlyDirectCurrency(), is(true));
                assertThat(charge.getMetadata().get("string_value"), is(metadata.getStringValue()));
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

  @Test
  public void shouldThrowExceptionIfSubscriptionToken()
      throws IOException, UnivapayException, TimeoutException, InterruptedException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(
        "Can't create a charge with a transaction token of type 'subscription'.");

    StoreId storeId = new StoreId("425e88b7-b588-4247-80ee-0ea0caff1190");
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    stubFor(
        createStub(
            RequestMethod.GET,
            "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.toString(),
            jwtCredentials,
            200,
            TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse(
                true, TransactionTokenType.SUBSCRIPTION),
            null,
            TestScenarioState.STARTED,
            TestScenarioState.CREATE_CHARGE));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    MoneyLike money = new MoneyLike(BigInteger.valueOf(5000), "JPY");
    univapay.createChargeWithTokenAlias(storeId, aliasKey, money, true).dispatch();
  }

  private enum TestScenarioState {
    STARTED(Scenario.STARTED),
    CREATE_CHARGE("Create a Charge"),
    DESCRIPTOR_ERROR("Descriptor not Supported"),
    DELETE_ALIAS("Delete a Token Alias"),
    AWAIT_CHARGE("Await a Charge");

    private String name;

    TestScenarioState(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  private MappingBuilder createStub(
      RequestMethod method,
      String path,
      AuthStrategy token,
      int status,
      String responseBody,
      String requestBody,
      TestScenarioState state,
      TestScenarioState nextState) {
    return createStub(
        method, path, token, status, responseBody, requestBody, state, nextState, null, null);
  }

  private MappingBuilder createStub(
      RequestMethod method,
      String path,
      AuthStrategy token,
      int status,
      String responseBody,
      String requestBody,
      TestScenarioState state,
      TestScenarioState nextState,
      IdempotencyKey idempotencyKey,
      IdempotencyStatus idempotencyStatus) {

    ResponseDefinitionBuilder responseBuilder =
        aResponse()
            .withStatus(status)
            .withHeader("Content-Type", "application/json")
            .withHeader("Content-Length", String.valueOf(responseBody.length()))
            .withBody(responseBody);

    if (idempotencyStatus != null) {
      responseBuilder.withHeader(
          UnivapayConstants.idempotencyStatusHeaderName, idempotencyStatus.name());
    }

    ScenarioMappingBuilder stub;
    String scenarioName = "Charge with Temporary Token Alias Scenario";

    switch (method) {
      case GET:
        stub = get(urlEqualTo(path)).inScenario(scenarioName);
        break;

      case POST:
        stub = post(urlEqualTo(path)).inScenario(scenarioName);
        break;

      case PATCH:
        stub = patch(urlEqualTo(path)).inScenario(scenarioName);
        break;

      case DELETE:
        stub = delete(urlEqualTo(path)).inScenario(scenarioName);
        break;

      default:
        throw new IllegalArgumentException("Illegal Request Method");
    }

    stub.withHeader("Authorization", containing(token.getAuthHeader().getValue()))
        .whenScenarioStateIs(state.getName())
        .willReturn(responseBuilder);

    if (requestBody != null) {
      stub.withRequestBody(equalToJson(requestBody));
    }

    if (nextState != null) {
      stub.willSetStateTo(nextState.getName());
    }

    if (idempotencyKey != null) {
      stub.withHeader(
          UnivapayConstants.idempotencyKeyHeaderName, containing(idempotencyKey.getKey()));
    }

    return stub;
  }
}
