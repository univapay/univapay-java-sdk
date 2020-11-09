package com.univapay.sdk.resources;

import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.QrCode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QrMerchantsResource {

  @GET("/stores/{storeId}/charges/{id}/qr")
  Call<QrCode> getChargeQrCodeMerchant(@Path("storeId") StoreId storeId, @Path("id") ChargeId id);
}
