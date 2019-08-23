package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.AppJWTId;
import com.univapay.sdk.models.common.AppTokenId;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.applicationtoken.CreateAppTokenReq;
import com.univapay.sdk.models.request.applicationtoken.CreateMerchantAppJWTReq;
import com.univapay.sdk.models.request.applicationtoken.CreateStoreAppJWTReq;
import com.univapay.sdk.models.request.store.UpdateAppTokenReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.applicationtoken.ApplicationToken;
import com.univapay.sdk.models.response.applicationtoken.MerchantApplicationJWT;
import com.univapay.sdk.models.response.applicationtoken.StoreApplicationJWT;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.models.common.*;
import retrofit2.Call;
import retrofit2.http.*;

/** Resource for managing a store's credentials. */
public interface ApplicationTokenResource {

  @GET("/stores/{storeId}/app_tokens")
  Call<PaginatedList<ApplicationToken>> listAppTokens(
      @Path("storeId") StoreId storeId, @Query("mode") ProcessingMode processingMode);

  @DELETE("/stores/{storeId}/app_tokens/{appTokenId}")
  Call<Void> deleteAppToken(
      @Path("storeId") StoreId storeId, @Path("appTokenId") AppTokenId appTokenId);

  @POST("/stores/{storeId}/app_tokens")
  Call<ApplicationToken> createAppToken(
      @Path("storeId") StoreId storeId, @Body CreateAppTokenReq dataToPost);

  @PATCH("/stores/{storeId}/app_tokens/{appTokenId}")
  Call<ApplicationToken> updateAppToken(
      @Path("storeId") StoreId storeId,
      @Path("appTokenId") AppTokenId appTokenId,
      @Body UpdateAppTokenReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  //    JWT Application token resources
  @GET("/app_jwts")
  Call<PaginatedList<MerchantApplicationJWT>> listMerchantAppJWT();

  @POST("/app_jwts")
  Call<MerchantApplicationJWT> createMerchantAppJWT(@Body CreateMerchantAppJWTReq dataToPost);

  @DELETE("/app_jwts/{jwtId}")
  Call<Void> deleteMerchantAppJWT(@Path("jwtId") AppJWTId appJWTId);

  @GET("/stores/{storeId}/app_jwts")
  Call<PaginatedList<StoreApplicationJWT>> listStoreAppJWT(@Path("storeId") StoreId storeId);

  @POST("/stores/{storeId}/app_jwts")
  Call<StoreApplicationJWT> createStoreAppJWT(
      @Path("storeId") StoreId storeId, @Body CreateStoreAppJWTReq dataToPost);

  @DELETE("/stores/{storeId}/app_jwts/{jwtId}")
  Call<Void> deleteStoreAppJWT(@Path("storeId") StoreId storeId, @Path("jwtId") AppJWTId appJWTId);
}
