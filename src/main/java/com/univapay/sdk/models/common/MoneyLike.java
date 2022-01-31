package com.univapay.sdk.models.common;

import com.univapay.sdk.models.errors.SDKException;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/** A class that represents monetary quantities with an amount and a currency. */
public class MoneyLike extends UnivapayResponse {

  private final BigInteger amount;
  private final String currency;

  public MoneyLike(BigInteger amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  // A simple way of creating Physical Currency
  public static MoneyLike of(String currency, int amount) {
    return new MoneyLike(BigInteger.valueOf(amount), currency);
  }

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public boolean equals(Object obj) {
    try {
      MoneyLike other = (MoneyLike) obj;
      return this.amount.equals(other.amount)
          && this.currency.toLowerCase().equals(other.currency.toLowerCase());
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Convert this instance to its Joda Money equivalent
   *
   * @return an instance of Joda Money. The amount takes into consideration decimal places.
   */
  public Money asJodaMoney() {
    CurrencyUnit currencyUnit = CurrencyUnit.of(currency);
    int decimalPlaces = currencyUnit.getDecimalPlaces();
    BigDecimal scaledAmount =
        new BigDecimal(amount)
            .setScale(decimalPlaces, RoundingMode.HALF_UP)
            .divide(BigDecimal.valueOf(Math.pow(10, decimalPlaces)), RoundingMode.HALF_UP);
    return Money.of(currencyUnit, scaledAmount);
  }

  /**
   * Compares this to another instance of MoneyLike
   *
   * @param other
   * @return As in the JodaMoney equivalent, -1 if Less Than , 0 if Equal and 1 if Greater Than
   * @throws SDKException if the currencies differ.
   */
  public int compareTo(MoneyLike other) throws SDKException {
    if (isSameCurrency(other)) {
      return this.asJodaMoney().compareTo(other.asJodaMoney());
    } else throw new SDKException("Cannot compare money in different currencies.");
  }

  /**
   * Addition operation. Assumes that `amountToAdd` is in the same currency.
   *
   * @param amountToAdd
   * @return a new instance of MoneyLike with the altered amount.
   */
  public MoneyLike plus(BigInteger amountToAdd) {
    return new MoneyLike(this.amount.add(amountToAdd), this.currency);
  }

  /**
   * Addition operation between two instances of MoneyLike.
   *
   * @param other
   * @return a new instance of MoneyLike with the altered amount.
   * @throws SDKException if the currencies differ.
   */
  public MoneyLike plus(MoneyLike other) throws SDKException {
    if (isSameCurrency(other)) {
      return this.plus(other.getAmount());
    } else throw new SDKException("Cannot add money in different currencies.");
  }

  /**
   * Subtraction operation. Assumes that `amountToSubtract` is in the same currency.
   *
   * @param amountToSubtract
   * @return a new instance of MoneyLike with the altered amount.
   */
  public MoneyLike minus(BigInteger amountToSubtract) {
    return new MoneyLike(this.amount.subtract(amountToSubtract), this.currency);
  }

  /**
   * Subtraction operation between two instances of MoneyLike
   *
   * @param other
   * @return a new instance of MoneyLike with the altered amount.
   * @throws SDKException if the currencies differ.
   */
  public MoneyLike minus(MoneyLike other) throws SDKException {
    if (isSameCurrency(other)) {
      return this.minus(other.getAmount());
    } else throw new SDKException("Cannot subtract money in different currencies.");
  }

  private boolean isSameCurrency(MoneyLike other) {
    return this.currency.equals(other.getCurrency());
  }
}
