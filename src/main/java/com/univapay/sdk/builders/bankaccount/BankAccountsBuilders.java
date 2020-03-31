package com.univapay.sdk.builders.bankaccount;

import com.univapay.sdk.builders.bankaccount.AbstractBankAccountsBuilders.*;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.bankaccounts.BaseBankAccount;
import com.univapay.sdk.models.request.bankaccount.BankAccountPatchData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.resources.BankAccountsResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class BankAccountsBuilders {

  public static class CreateBankAccountRequestBuilder
      extends AbstractCreateBankAccountRequestBuilder<
          CreateBankAccountRequestBuilder, BankAccountsResource, BankAccount> {

    public CreateBankAccountRequestBuilder(Retrofit retrofit, BaseBankAccount bankAccount) {
      super(retrofit, bankAccount);
    }

    @Override
    protected Call<BankAccount> getRequest(BankAccountsResource resource) {
      return resource.create(bankAccount, idempotencyKey);
    }
  }

  public static class DeleteBankAccountRequestBuilder
      extends AbstractDeleteBankAccountRequestBuilder<
          DeleteBankAccountRequestBuilder, BankAccountsResource> {

    public DeleteBankAccountRequestBuilder(Retrofit retrofit, BankAccountId bankAccountId) {
      super(retrofit, bankAccountId);
    }

    @Override
    protected Call<Void> getRequest(BankAccountsResource resource) {
      return resource.delete(bankAccountId);
    }
  }

  public static class GetBankAccountRequestBuilder
      extends AbstractGetBankAccountRequestBuilder<
          GetBankAccountRequestBuilder, BankAccountsResource, BankAccount> {
    public GetBankAccountRequestBuilder(Retrofit retrofit, BankAccountId bankAccountId) {
      super(retrofit, bankAccountId);
    }

    @Override
    protected Call<BankAccount> getRequest(BankAccountsResource resource) {
      return resource.get(bankAccountId);
    }
  }

  public static class ListAllBankAccountsRequestBuilder
      extends AbstractListAllBankAccountsRequestBuilder<
          ListAllBankAccountsRequestBuilder, BankAccountsResource, BankAccount> {

    public ListAllBankAccountsRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<PaginatedList<BankAccount>> getRequest(BankAccountsResource resource) {
      return resource.list(getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class GetPrimaryBankAccountRequestBuilder
      extends AbstractGetPrimaryBankAccountRequestBuilder<
          GetPrimaryBankAccountRequestBuilder, BankAccountsResource, BankAccount> {

    public GetPrimaryBankAccountRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<BankAccount> getRequest(BankAccountsResource resource) {
      return resource.getPrimary();
    }
  }

  public static class UpdateBankAccountRequestBuilder
      extends AbstractUpdateBankAccountRequestBuilder<
          UpdateBankAccountRequestBuilder, BankAccountsResource, BankAccount> {

    public UpdateBankAccountRequestBuilder(Retrofit retrofit, BankAccountId bankAccountId) {
      super(retrofit, bankAccountId);
    }

    @Override
    protected Call<BankAccount> getRequest(BankAccountsResource resource) {
      return resource.update(
          bankAccountId,
          new BankAccountPatchData(
              isPrimary,
              holderName,
              bankName,
              branchName,
              bankAddress,
              currency,
              accountNumber,
              swiftCode,
              routingNumber),
          idempotencyKey);
    }
  }
}
