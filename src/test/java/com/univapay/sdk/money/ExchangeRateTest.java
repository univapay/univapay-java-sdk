package com.univapay.sdk.money;

import static org.junit.Assert.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ExchangeRateFakeRR;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;

public class ExchangeRateTest extends GenericTest {

  @Test
  public void shouldSendMoneyConversionRequest()
      throws InterruptedException, IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/exchange_rates/calculate",
        appJWTSecret + "." + appJWT,
        200,
        ExchangeRateFakeRR.convertFakeResponse,
        ExchangeRateFakeRR.convertFakeRequest);

    UnivapaySDK univapay = UnivapaySDK.create(appJWTStrategyWithSecret, testSettings);
    final MoneyLike toConvert = new MoneyLike(BigInteger.valueOf(1000), "JPY");
    final MoneyLike expectedMoney = new MoneyLike(BigInteger.valueOf(1230), "USD");

    MoneyLike converted = univapay.convertMoney(toConvert, "USD").build().dispatch();

    assertEquals(expectedMoney, converted);
    assertEquals(BigDecimal.valueOf(1230, 2), converted.asJodaMoney().getAmount());
  }
}
