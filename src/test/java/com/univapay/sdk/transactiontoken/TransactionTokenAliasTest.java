package com.univapay.sdk.transactiontoken;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.google.gson.JsonObject;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.UnivapayBinaryData;
import com.univapay.sdk.models.response.transactiontoken.TemporaryTransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenAlias;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TemporaryTokenAliasMedia;
import com.univapay.sdk.types.TemporaryTokenAliasQRLogo;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.metadataadapter.ManyTypesAdapter;
import com.univapay.sdk.utils.metadataadapter.ManyTypesMetadata;
import com.univapay.sdk.utils.mockcontent.ImageMockContent;
import com.univapay.sdk.utils.mockcontent.TemporaryTransactionTokenAliasFakeRR;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.hamcrest.core.Is;
import org.junit.Test;

public class TransactionTokenAliasTest extends GenericTest {

  private final ManyTypesAdapter adapter = new ManyTypesAdapter();
  private final ManyTypesMetadata metadata =
      new ManyTypesMetadata(
          "hola",
          BigInteger.valueOf(989223112),
          BigDecimal.valueOf(1234.7981723987),
          true,
          3.141592F);

  private Date parseDate(String dateStr) {
    return dateParser.parseDateTime(dateStr).toDate();
  }

  @Test
  public void shouldRequestANewAliasWithDefaultParams() throws Exception {

    TransactionTokenId transactionTokenId = new TransactionTokenId(UUID.randomUUID());
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/tokens/alias",
        jwt,
        201,
        TemporaryTransactionTokenAliasFakeRR.createAliasResponse(aliasKey),
        TemporaryTransactionTokenAliasFakeRR.createAliasRequest(transactionTokenId));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TransactionTokenAlias alias = univapay.createTokenAlias(transactionTokenId).build().dispatch();

    assertThat(alias.getKey(), is(aliasKey));
    assertThat(alias.getValidUntil(), is(parseDate("2018-10-02T15:06:51.879097Z")));
  }

  @Test
  public void shouldRequestANewAliasWithMetadata() throws Exception {

    TransactionTokenId transactionTokenId = new TransactionTokenId(UUID.randomUUID());
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/tokens/alias",
        jwt,
        201,
        TemporaryTransactionTokenAliasFakeRR.createAliasWithMetadataResponse(aliasKey),
        TemporaryTransactionTokenAliasFakeRR.createAliasWithMetadataRequest(transactionTokenId));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TransactionTokenAlias alias =
        univapay
            .createTokenAlias(transactionTokenId)
            .withMetadata(metadata, adapter)
            .build()
            .dispatch();

    assertThat(alias.getKey(), is(aliasKey));
    assertThat(alias.getValidUntil(), is(parseDate("2018-10-02T15:06:51.879097Z")));
  }

  @Test
  public void shouldRequestANewAliasWithAllOptions() throws Exception {

    TransactionTokenId transactionTokenId = new TransactionTokenId(UUID.randomUUID());
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final String validUntilStr = "2018-10-02T15:09:51.879097Z";
    final Date validUntil = dateParser.parseDateTime(validUntilStr).toDate();

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/tokens/alias",
        jwt,
        201,
        TemporaryTransactionTokenAliasFakeRR.createAliasWithAllOptionsResponse(
            aliasKey, datePrinter.print(validUntil.getTime())),
        TemporaryTransactionTokenAliasFakeRR.createAliasWithAllOptionsRequest(
            transactionTokenId, datePrinter.print(validUntil.getTime())));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TransactionTokenAlias alias =
        univapay
            .createTokenAlias(transactionTokenId)
            .withValidUntil(validUntil)
            .withMetadata(metadata, adapter)
            .withMoney(new MoneyLike(BigInteger.valueOf(10000), "jpy"))
            .build()
            .dispatch();

    assertThat(alias.getKey(), is(aliasKey));
    assertThat(alias.getValidUntil(), is(validUntil));
  }

  @Test
  public void shouldGetAnAlias() throws Exception {
    StoreId storeId = new StoreId(UUID.randomUUID());
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    final String validUntilStr = "2018-10-02T15:09:51.879097Z";
    final Date createdOn = dateParser.parseDateTime(validUntilStr).toDate();

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.getKey(),
        jwt,
        200,
        TemporaryTransactionTokenAliasFakeRR.getTokenAliasResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TemporaryTransactionToken temporaryToken =
        univapay.getTokenAlias(storeId, aliasKey).build().dispatch();

    assertThat(temporaryToken.getId().toString(), is("11e8d66c-0b26-aace-ae22-7b4a85c3ff85"));
    assertThat(temporaryToken.getStoreId().toString(), is("11e8d66b-5985-7200-8eee-9f2382a3211b"));
    assertThat(temporaryToken.getEmail(), is("blah@blah.com"));
    assertThat(temporaryToken.getPaymentTypeName(), Is.is(PaymentTypeName.CARD));
    assertTrue(temporaryToken.getActive());
    assertThat(temporaryToken.getMode(), Is.is(ProcessingMode.TEST));
    assertThat(temporaryToken.getType(), Is.is(TransactionTokenType.RECURRING));
    MetadataMap tokenMetadata = temporaryToken.getMetadata();
    assertThat(tokenMetadata.get("goodbye"), is("adios"));
    assertThat(temporaryToken.getAliasMetadata(new ManyTypesAdapter()), is(metadata));
    assertThat(temporaryToken.getCreatedOn(), is(createdOn));
    assertNull(temporaryToken.getLastUsedOn());
    assertThat(temporaryToken.getAmount(), is(BigInteger.valueOf(5000)));
    assertThat(temporaryToken.getCurrency(), is("JPY"));
  }

  @Test
  public void shouldGetAnAliasAsImage() throws Exception {
    StoreId storeId = new StoreId("11e8c54f-0487-a142-a4a0-678b9f5c2071");
    TokenAliasKey aliasKey = new TokenAliasKey("2273112348666");

    File file = new File("src/test/resources/test-qr-image.png");
    ImageMockContent responseBody = new ImageMockContent(Files.readAllBytes(file.toPath()));

    String color = "#000000";
    int size = 500;
    JsonObject requestJSON = new JsonObject();
    requestJSON.addProperty("media", TemporaryTokenAliasMedia.QR.name().toLowerCase());
    requestJSON.addProperty("size", size);
    requestJSON.addProperty("logo", TemporaryTokenAliasQRLogo.NONE.name().toLowerCase());
    requestJSON.addProperty("color", color);

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.getKey(),
        jwt,
        200,
        responseBody,
        requestJSON.toString());

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    UnivapayBinaryData image =
        univapay
            .getTokenAliasImage(storeId, aliasKey)
            .withSize(size)
            .withLogoType(TemporaryTokenAliasQRLogo.NONE)
            .withColor(color)
            .build()
            .dispatch();

    byte[] responseBytes = image.getBytes();

    assertThat(responseBytes.length, is(responseBody.getLength()));
    assertThat(responseBytes, is(responseBody.getContent()));

    BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
    LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
    String imageJson = new MultiFormatReader().decode(bitmap).getText();
    TokenAliasKey aliasKeyFromImage = TokenAliasKey.parse(imageJson);

    assertThat(aliasKeyFromImage, is(aliasKey));
  }

  @Test
  public void shouldDeleteAnAlias() throws Exception {
    StoreId storeId = new StoreId(UUID.randomUUID());
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "DELETE",
        "/stores/" + storeId.toString() + "/tokens/alias/" + aliasKey.getKey(),
        jwt,
        200,
        "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay.deleteTokenAlias(storeId, aliasKey).build().dispatch();
  }

  @Test
  public void shouldParseAliasFromStringData() throws Exception {
    String aliasKeyStr = "227503066099";
    TokenAliasKey aliasKey = new TokenAliasKey("227503066099");
    TokenAliasKey parsedAlias = TokenAliasKey.parse(aliasKeyStr);

    assertThat(parsedAlias, is(aliasKey));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionOnMalformedJson() {
    String aliasStr = "thisisnota validTOk3n4l14s";
    TokenAliasKey.parse(aliasStr);
  }
}
