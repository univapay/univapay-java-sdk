package com.univapay.sdk.models.common.bankaccounts;

import com.univapay.sdk.types.BankAccountCountry;
import com.univapay.sdk.types.BankAccountType;

public class AmericanBankAccount extends BaseBankAccount {

  public AmericanBankAccount(
      String holderName,
      String bankName,
      String currency,
      String accountNumber,
      String routingNumber,
      BankAccountType accountType) {
    setCommonFields(
        BankAccountCountry.US, holderName, bankName, currency, accountNumber, accountType);
    this.setRoutingNumber(routingNumber);
  }

  public AmericanBankAccount withBranchName(String branchName) {
    this.setBranchName(branchName);
    return this;
  }

  public AmericanBankAccount withBankAddress(String bankAddress) {
    this.setBankAddress(bankAddress);
    return this;
  }
}
