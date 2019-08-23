package com.univapay.sdk.models.common.bankaccounts;

import com.univapay.sdk.types.BankAccountCountry;
import com.univapay.sdk.types.BankAccountType;

public class CanadianBankAccount extends BaseBankAccount {

  public CanadianBankAccount(
      String holderName,
      String bankName,
      String bankAddress,
      String currency,
      String accountNumber,
      String swiftCode,
      BankAccountType accountType) {
    setCommonFields(
        BankAccountCountry.CA, holderName, bankName, currency, accountNumber, accountType);
    setBankAddress(bankAddress);
    setSwiftCode(swiftCode);
  }

  public CanadianBankAccount withBranchName(String branchName) {
    this.setBranchName(branchName);
    return this;
  }
}
