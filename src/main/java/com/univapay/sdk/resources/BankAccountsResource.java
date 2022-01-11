package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.bankaccounts.BaseBankAccount;
import com.univapay.sdk.models.request.bankaccount.BankAccountPatchData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.types.CursorDirection;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

/** Resource for managing a merchant's bank accounts. */
public interface BankAccountsResource {

  @GET("/bank_accounts")
  Call<PaginatedList<BankAccount>> list(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable BankAccountId cursor);

  @POST("/bank_accounts")
  Call<BankAccount> create(
      @Body BaseBankAccount dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @GET("/bank_accounts/{bankAccountId}")
  Call<BankAccount> get(@Path("bankAccountId") BankAccountId bankAccountID);

  @PATCH("/bank_accounts/{bankAccountId}")
  Call<BankAccount> update(
      @Path("bankAccountId") BankAccountId bankAccountID,
      @Body BankAccountPatchData patchData,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @DELETE("/bank_accounts/{bankAccountId}")
  Call<Void> delete(@Path("bankAccountId") BankAccountId bankAccountId);

  @GET("/bank_accounts/primary")
  Call<BankAccount> getPrimary();
}
