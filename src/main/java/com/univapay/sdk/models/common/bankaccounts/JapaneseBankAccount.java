package com.univapay.sdk.models.common.bankaccounts;

import com.univapay.sdk.types.BankAccountCountry;
import com.univapay.sdk.types.BankAccountType;

public class JapaneseBankAccount extends BaseBankAccount {

  public JapaneseBankAccount(
      String holderName,
      String bankName,
      String branchName,
      String currency,
      String accountNumber,
      BankAccountType accountType) {
    setCommonFields(
        BankAccountCountry.JP, holderName, bankName, currency, accountNumber, accountType);
    this.setBranchName(branchName);
  }

  public JapaneseBankAccount withBankAddress(String bankAddress) {
    this.setBankAddress(bankAddress);
    return this;
  }

  public JapaneseBankAccount withSwiftCode(String swiftCode) {
    this.setSwiftCode(swiftCode);
    return this;
  }
}
