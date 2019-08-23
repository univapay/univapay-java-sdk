package com.univapay.sdk.resources;

import com.univapay.sdk.models.common.LedgerId;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.ledger.Ledger;
import com.univapay.sdk.types.CursorDirection;
import javax.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/** Resource for obtaining information about the ledgers associated with a transfer. */
public interface LedgersResource {

  @GET("/transfers/{transferId}/ledgers")
  Call<PaginatedList<Ledger>> listLedgers(
      @Path("transferId") TransferId transferId,
      @Query("all") Boolean all,
      @Query("from") String from,
      @Query("to") String to,
      @Query("min") Integer min,
      @Query("max") Integer max,
      @Query("currency") String currency,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable LedgerId cursor);
}
