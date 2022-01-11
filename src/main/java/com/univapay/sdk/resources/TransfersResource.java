package com.univapay.sdk.resources;

import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.types.CursorDirection;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/** Resource for managing transfers. */
public interface TransfersResource {
  @GET("/transfers")
  Call<PaginatedList<Transfer>> list(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable TransferId cursor);

  @GET("/transfers/{transferId}")
  Call<Transfer> get(@Path("transferId") TransferId transferId);
}
