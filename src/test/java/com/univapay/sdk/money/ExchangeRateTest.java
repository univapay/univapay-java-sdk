package com.univapay.sdk.money;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ExchangeRateFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ExchangeRateTest extends GenericTest {

  @Test
  void shouldSendMoneyConversionRequest() throws Exception {
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
