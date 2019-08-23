package com.univapay.sdk.models.request.bankaccount;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("FieldCanBeLocal")
public class BankAccountPatchData {
  @SerializedName("primary")
  private Boolean isPrimary;

  @SerializedName("holder_name")
  private String holderName;

  @SerializedName("bank_name")
  private String bankName;

  @SerializedName("branch_name")
  private String branchName;

  @SerializedName("bank_address")
  private String bankAddress;

  @SerializedName("currency")
  private String currency;

  @SerializedName("account_number")
  private String accountNumber;

  @SerializedName("swift_code")
  private String swiftCode;

  @SerializedName("routing_number")
  private String routingNumber;

  public BankAccountPatchData(
      Boolean isPrimary,
      String holderName,
      String bankName,
      String branchName,
      String bankAddress,
      String currency,
      String accountNumber,
      String swiftCode,
      String routingNumber) {
    this.isPrimary = isPrimary;
    this.holderName = holderName;
    this.bankName = bankName;
    this.branchName = branchName;
    this.bankAddress = bankAddress;
    this.currency = currency;
    this.accountNumber = accountNumber;
    this.swiftCode = swiftCode;
    this.routingNumber = routingNumber;
  }
}
