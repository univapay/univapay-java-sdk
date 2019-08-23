package com.univapay.sdk.builders.bankaccount;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.bankaccounts.BaseBankAccount;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import retrofit2.Retrofit;

public abstract class AbstractBankAccountsBuilders {

  public abstract static class AbstractCreateBankAccountRequestBuilder<
          B extends AbstractCreateBankAccountRequestBuilder, R, M extends BankAccount>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected BaseBankAccount bankAccount;

    public BaseBankAccount getBankAccount() {
      return bankAccount;
    }

    public AbstractCreateBankAccountRequestBuilder(Retrofit retrofit, BaseBankAccount bankAccount) {
      super(retrofit);
      this.bankAccount = bankAccount;
    }
  }

  public abstract static class AbstractDeleteBankAccountRequestBuilder<
          B extends AbstractDeleteBankAccountRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {
    protected BankAccountId bankAccountId;

    protected BankAccountId getBankAccountId() {
      return bankAccountId;
    }

    public AbstractDeleteBankAccountRequestBuilder(Retrofit retrofit, BankAccountId bankAccountId) {
      super(retrofit);
      this.bankAccountId = bankAccountId;
    }
  }

  public abstract static class AbstractGetBankAccountRequestBuilder<
          B extends AbstractGetBankAccountRequestBuilder, R, M extends BankAccount>
      extends RetrofitRequestBuilder<M, R> {
    protected BankAccountId bankAccountId;

    protected BankAccountId getBankAccountId() {
      return bankAccountId;
    }

    public AbstractGetBankAccountRequestBuilder(Retrofit retrofit, BankAccountId bankAccountId) {
      super(retrofit);
      this.bankAccountId = bankAccountId;
    }
  }

  public abstract static class AbstractListAllBankAccountsRequestBuilder<
          B extends AbstractListAllBankAccountsRequestBuilder, R, M extends BankAccount>
      extends RetrofitRequestBuilderPaginated<M, R, B, BankAccountId> {

    public AbstractListAllBankAccountsRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractGetPrimaryBankAccountRequestBuilder<
          B extends AbstractGetPrimaryBankAccountRequestBuilder, R, M extends BankAccount>
      extends RetrofitRequestBuilder<M, R> {

    public AbstractGetPrimaryBankAccountRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractUpdateBankAccountRequestBuilder<
          B extends AbstractUpdateBankAccountRequestBuilder, R, M extends BankAccount>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected BankAccountId bankAccountId;
    protected Boolean isPrimary;
    protected String holderName;
    protected String bankName;
    protected String branchName;
    protected String bankAddress;
    protected String currency;
    protected String accountNumber;
    protected String swiftCode;
    protected String routingNumber;

    protected BankAccountId getBankAccountId() {
      return bankAccountId;
    }

    protected Boolean getIsPrimary() {
      return isPrimary;
    }

    protected String getHolderName() {
      return holderName;
    }

    protected String getBankName() {
      return bankName;
    }

    protected String getBranchName() {
      return branchName;
    }

    protected String getBankAddress() {
      return bankAddress;
    }

    protected String getCurrency() {
      return currency;
    }

    protected String getAccountNumber() {
      return accountNumber;
    }

    protected String getSwiftCode() {
      return swiftCode;
    }

    protected String getRoutingNumber() {
      return routingNumber;
    }

    public AbstractUpdateBankAccountRequestBuilder(Retrofit retrofit, BankAccountId bankAccountId) {
      super(retrofit);
      this.bankAccountId = bankAccountId;
    }

    public B withIsPrimary(Boolean isPrimary) {
      this.isPrimary = isPrimary;
      return (B) this;
    }

    public B withHolderName(String holderName) {
      this.holderName = holderName;
      return (B) this;
    }

    public B withBankName(String bankName) {
      this.bankName = bankName;
      return (B) this;
    }

    public B withBranchName(String branchName) {
      this.branchName = branchName;
      return (B) this;
    }

    public B withBankAddress(String bankAddress) {
      this.bankAddress = bankAddress;
      return (B) this;
    }

    public B withCurrency(String currency) {
      this.currency = currency;
      return (B) this;
    }

    public B withAccountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
      return (B) this;
    }

    public B withSwiftCode(String swiftCode) {
      this.swiftCode = swiftCode;
      return (B) this;
    }

    public B withRoutingNumber(String routingNumber) {
      this.routingNumber = routingNumber;
      return (B) this;
    }
  }
}
