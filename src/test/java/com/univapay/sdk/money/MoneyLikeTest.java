package com.univapay.sdk.money;

import static org.junit.Assert.assertEquals;

import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.errors.SDKException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;

public class MoneyLikeTest {
  private final MoneyLike jpyMoney = new MoneyLike(BigInteger.valueOf(2500), "JPY");
  private final MoneyLike usdMoney = new MoneyLike(BigInteger.valueOf(50892), "USD");
  private final MoneyLike jodMoney = new MoneyLike(BigInteger.valueOf(90000), "JOD");

  @Test
  public void shouldConvertProperlyToJodaMoney() {
    assertEquals(BigDecimal.valueOf(2500), jpyMoney.asJodaMoney().getAmount());
    assertEquals(BigDecimal.valueOf(50892, 2), usdMoney.asJodaMoney().getAmount());
    assertEquals(BigDecimal.valueOf(90000, 3), jodMoney.asJodaMoney().getAmount());
  }

  @Test
  public void canAddAnAmount() {
    MoneyLike newJpyMoney = jpyMoney.plus(BigInteger.valueOf(1000));
    assertEquals(BigInteger.valueOf(3500), newJpyMoney.getAmount());

    MoneyLike newUsdMoney = usdMoney.plus(BigInteger.valueOf(108));
    assertEquals(BigInteger.valueOf(51000), newUsdMoney.getAmount());
  }

  @Test
  public void canSubtractAnAmount() throws SDKException {
    MoneyLike newJpyMoney = jpyMoney.minus(BigInteger.valueOf(1000));
    assertEquals(newJpyMoney.getAmount(), BigInteger.valueOf(1500));

    MoneyLike newUsdMoney = usdMoney.minus(BigInteger.valueOf(892));
    assertEquals(BigInteger.valueOf(50000), newUsdMoney.getAmount());

    MoneyLike negativeJpyMoney = jpyMoney.minus(jpyMoney.plus(BigInteger.valueOf(1000)));
    assertEquals(negativeJpyMoney.getAmount(), BigInteger.valueOf(-1000));

    MoneyLike negativeUsdMoney = usdMoney.minus(usdMoney.plus(BigInteger.valueOf(3456)));
    assertEquals(BigInteger.valueOf(-3456), negativeUsdMoney.getAmount());
    assertEquals(BigDecimal.valueOf(-34.56), negativeUsdMoney.asJodaMoney().getAmount());
  }

  @Test
  public void canCompareToOtherMoney() throws SDKException {
    MoneyLike newJpyMoney = jpyMoney.plus(BigInteger.valueOf(1000));
    assertEquals(jpyMoney.compareTo(newJpyMoney), -1);
  }
}
