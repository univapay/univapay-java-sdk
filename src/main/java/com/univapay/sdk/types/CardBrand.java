package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;
import java.util.regex.Pattern;

public enum CardBrand {
  @SerializedName("visa")
  VISA("^4\\d*", 16, 16, 3, "visa"),
  @SerializedName("mastercard")
  MASTERCARD("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[0-1]|2720)\\d*", 16, 16, 3, "mastercard"),
  @SerializedName("discover")
  DISCOVER("^(6011|65|64[4-9]|622)\\d*", 16, 16, 3, "discover"),
  @SerializedName("american_express")
  AMERICAN_EXPRESS("^3[47]\\d*", 15, 15, 4, "american_express"),
  @SerializedName("diners_club")
  DINERS_CLUB("^(36|38|30[0-5])\\d*", 14, 14, 3, "diners_club"),
  @SerializedName("jcb")
  JCB("^35\\d*", 16, 16, 3, "jcb"),
  @SerializedName("maestro")
  MAESTRO("^(5018|5020|5038|6020|6304|6703|6759|676[1-3])\\d*", 12, 19, 3, "maestro"),
  @SerializedName("unionpay")
  UNIONPAY("^62\\d*", 16, 19, 3, "unionpay"),
  @SerializedName("unknown")
  UNKNOWN("\\d+", 12, 19, 3, "unknown"),
  @SerializedName("empty")
  EMPTY("^$", 12, 19, 3, "empty");

  private static final int[] AMERICAN_EXPRESS_SPACE_INDICES = {4, 10};
  private static final int[] DEFAULT_SPACE_INDICES = {4, 8, 12};

  private final Pattern pattern;
  private final int minCardLength;
  private final int maxCardLength;
  private final int securityCodeLength;
  private final String brandName;

  CardBrand(
      String regex,
      int minCardLength,
      int maxCardLength,
      int securityCodeLength,
      String brandName) {
    this.pattern = Pattern.compile(regex);
    this.minCardLength = minCardLength;
    this.maxCardLength = maxCardLength;
    this.securityCodeLength = securityCodeLength;
    this.brandName = brandName;
  }

  /**
   * Returns the card type matching this account, or UNKNOWN for no match.
   *
   * <p>A partial account type may be given, with the caveat that it may not have enough digits to
   * match.
   */
  public static CardBrand forCardNumber(String cardNumber) {
    for (CardBrand cardType : values()) {
      if (cardType.getPattern().matcher(cardNumber).matches()) {
        return cardType;
      }
    }
    return EMPTY;
  }

  public static CardBrand forBrandName(String brandName) {
    for (CardBrand cardBrand : values()) {
      if (cardBrand.brandName.equals(brandName)) return cardBrand;
    }
    return CardBrand.UNKNOWN;
  }

  /** @return The regex matching this card type. */
  public Pattern getPattern() {
    return pattern;
  }

  /** @return The length of the current card's security code. */
  public int getSecurityCodeLength() {
    return securityCodeLength;
  }

  /** @return minimum length of a card for this CardType */
  public int getMinCardLength() {
    return minCardLength;
  }

  /** @return max length of a card for this CardType */
  public int getMaxCardLength() {
    return maxCardLength;
  }

  public String getBrandName() {
    return brandName;
  }

  /**
   * @return the locations where spaces should be inserted when formatting the card in a user
   *     friendly way. Only for display purposes.
   */
  public int[] getSpaceIndices() {
    return this == AMERICAN_EXPRESS ? AMERICAN_EXPRESS_SPACE_INDICES : DEFAULT_SPACE_INDICES;
  }

  /**
   * Performs the Luhn check on the given card number.
   *
   * @param cardNumber a String consisting of numeric digits (only).
   * @return {@code true} if the sequence passes the checksum
   * @throws IllegalArgumentException if {@code cardNumber} contained a non-digit (where {@link
   *     Character#isDefined(char)} is {@code false}).
   * @see <a href="http://en.wikipedia.org/wiki/Luhn_algorithm">Luhn Algorithm (Wikipedia)</a>
   */
  public static boolean isLuhnValid(String cardNumber) {
    final String reversed = new StringBuffer(cardNumber).reverse().toString();
    final int len = reversed.length();
    int oddSum = 0;
    int evenSum = 0;
    for (int i = 0; i < len; i++) {
      final char c = reversed.charAt(i);
      if (!Character.isDigit(c)) {
        throw new IllegalArgumentException(String.format("Not a digit: '%s'", c));
      }
      final int digit = Character.digit(c, 10);
      if (i % 2 == 0) {
        oddSum += digit;
      } else {
        evenSum += digit / 5 + (2 * digit) % 10;
      }
    }
    return (oddSum + evenSum) % 10 == 0;
  }

  /**
   * @param cardNumber The card number to validate.
   * @return {@code true} if this card number is locally valid.
   */
  public boolean validate(String cardNumber) {
    if (cardNumber.isEmpty()) {
      return false;
    }

    final int numberLength = cardNumber.length();
    if (numberLength < minCardLength || numberLength > maxCardLength) {
      return false;
    } else if (!pattern.matcher(cardNumber).matches()) {
      return false;
    }
    return isLuhnValid(cardNumber);
  }
}
