package com.univapay.sdk.models.common.bankaccounts;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.BankAccountCountry;
import com.univapay.sdk.types.BankAccountType;

public abstract class BaseBankAccount {

  @SerializedName("holder_name")
  private String holderName;

  @SerializedName("bank_name")
  private String bankName;

  @SerializedName("branch_name")
  private String branchName;

  @SerializedName("country")
  private BankAccountCountry country;

  @SerializedName("bank_address")
  private String bankAddress;

  @SerializedName("currency")
  private String currency;

  @SerializedName("account_number")
  private String accountNumber;

  @SerializedName("routing_number")
  private String routingNumber;

  @SerializedName("swift_code")
  private String swiftCode;

  @SerializedName("ifsc_code")
  private String ifscCode;

  @SerializedName("routing_code")
  private String routingCode;

  @SerializedName("account_type")
  private BankAccountType accountType;

  @SerializedName("is_primary")
  private Boolean isPrimary;

  public Boolean getPrimary() {
    return isPrimary;
  }

  public String getHolderName() {
    return holderName;
  }

  public String getBankName() {
    return bankName;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BankAccountCountry getCountry() {
    return country;
  }

  public String getBranchName() {
    return branchName;
  }

  public String getBankAddress() {
    return bankAddress;
  }

  public String getSwiftCode() {
    return swiftCode;
  }

  public String getRoutingNumber() {
    return routingNumber;
  }

  public String getIfscCode() {
    return ifscCode;
  }

  public String getRoutingCode() {
    return routingCode;
  }

  public BankAccountType getAccountType() {
    return accountType;
  }

  protected void setPrimary(Boolean primary) {
    isPrimary = primary;
  }

  protected void setHolderName(String holderName) {
    this.holderName = holderName;
  }

  protected void setBankName(String bankName) {
    this.bankName = bankName;
  }

  protected void setCurrency(String currency) {
    this.currency = currency;
  }

  protected void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  protected void setCountry(BankAccountCountry country) {
    this.country = country;
  }

  protected void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  protected void setBankAddress(String bankAddress) {
    this.bankAddress = bankAddress;
  }

  protected void setSwiftCode(String swiftCode) {
    this.swiftCode = swiftCode;
  }

  protected void setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
  }

  protected void setIfscCode(String ifscCode) {
    this.ifscCode = ifscCode;
  }

  protected void setRoutingCode(String routingCode) {
    this.routingCode = routingCode;
  }

  protected void setAccountType(BankAccountType accountType) {
    this.accountType = accountType;
  }

  protected void setCommonFields(
      BankAccountCountry country,
      String holderName,
      String bankName,
      String currency,
      String accountNumber,
      BankAccountType accountType) {
    this.setCountry(country);
    this.setHolderName(holderName);
    this.setBankName(bankName);
    this.setCurrency(currency);
    this.setAccountNumber(accountNumber);
    this.setAccountType(accountType);
  }
}
