package com.univapay.sdk.models.request.charge;

import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.types.ProcessingMode;
import java.util.HashMap;
import java.util.Map;

public class CardChargeSearch {

  private String lastFour;

  private String name;

  private Integer expMonth;

  private Integer expYear;

  private String cardNumber;

  private TimeRange timeRange;

  private String email;

  private String phone;

  private MoneyRange moneyRange;

  private String currency;

  private ProcessingMode mode;

  private TransactionTokenId transactionTokenId;

  private HashMap<String, String> mappedValues = new HashMap<>();

  public String getLastFour() {
    return lastFour;
  }

  public String getName() {
    return name;
  }

  public Integer getExpMonth() {
    return expMonth;
  }

  public Integer getExpYear() {
    return expYear;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public TimeRange getTimeRange() {
    return timeRange;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public MoneyRange getMoneyRange() {
    return moneyRange;
  }

  public String getCurrency() {
    return currency;
  }

  public ProcessingMode getMode() {
    return mode;
  }

  public TransactionTokenId getTransactionTokenId() {
    return transactionTokenId;
  }

  public CardChargeSearch withLastFour(String lastFour) {
    this.lastFour = lastFour;
    putHandlingNull("last_four", lastFour);
    return this;
  }

  public CardChargeSearch withName(String name) {
    putHandlingNull("name", name);
    this.name = name;
    return this;
  }

  public CardChargeSearch withExpMonth(Integer expMonth) {
    putHandlingNull("exp_month", expMonth);
    this.expMonth = expMonth;
    return this;
  }

  public CardChargeSearch withExpYear(Integer expYear) {
    putHandlingNull("exp_year", expYear);
    this.expYear = expYear;
    return this;
  }

  public CardChargeSearch withCardNumber(String cardNumber) {
    putHandlingNull("card_number", cardNumber);
    this.cardNumber = cardNumber;
    return this;
  }

  public CardChargeSearch withTimeRange(TimeRange timeRange) {
    putHandlingNull("from", timeRange.getFromDate());
    putHandlingNull("to", timeRange.getToDate());
    this.timeRange = timeRange;
    return this;
  }

  public CardChargeSearch withEmail(String email) {
    putHandlingNull("email", email);
    this.email = email;
    return this;
  }

  public CardChargeSearch withPhone(String phone) {
    putHandlingNull("phone", phone);
    this.phone = phone;
    return this;
  }

  public CardChargeSearch withMoneyRange(MoneyRange moneyRange) {
    putHandlingNull("amount_from", moneyRange.getMinimumAmount());
    putHandlingNull("amount_to", moneyRange.getMaximumAmount());
    this.moneyRange = moneyRange;
    return this;
  }

  public CardChargeSearch withCurrency(String currency) {
    putHandlingNull("currency", currency);
    this.currency = currency;
    return this;
  }

  public CardChargeSearch withMode(ProcessingMode mode) {
    putHandlingNull("mode", mode);
    this.mode = mode;
    return this;
  }

  public CardChargeSearch withTransactionTokenId(TransactionTokenId transactionTokenId) {
    putHandlingNull("transaction_token_id", transactionTokenId);
    this.transactionTokenId = transactionTokenId;
    return this;
  }

  private void putHandlingNull(String key, Object value) {
    if (value != null) {
      mappedValues.put(key, value.toString());
    }
  }

  public Map<String, String> asMap() {
    return mappedValues;
  }
}
