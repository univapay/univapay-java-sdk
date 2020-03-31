package com.univapay.sdk.builders.applicationtoken;

import static com.univapay.sdk.builders.applicationtoken.AbstractApplicationTokenBuilders.*;

import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.AppJWTId;
import com.univapay.sdk.models.common.AppTokenId;
import com.univapay.sdk.models.common.Domain;
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
import com.univapay.sdk.resources.ApplicationTokenResource;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class ApplicationTokenBuilders {

  public static class ListApplicationTokenRequestBuilder
      extends AbstractListApplicationTokenRequestBuilder<
          ListApplicationTokenRequestBuilder, ApplicationTokenResource, ApplicationToken> {

    public ListApplicationTokenRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<PaginatedList<ApplicationToken>> getRequest(ApplicationTokenResource resource) {
      return resource.listAppTokens(storeId, processingMode);
    }
  }

  public static class UpdateApplicationTokenRequestBuilder
      extends AbstractUpdateApplicationTokenRequestBuilder<
          UpdateApplicationTokenRequestBuilder, ApplicationTokenResource, ApplicationToken> {
    public UpdateApplicationTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, AppTokenId appTokenId, List<Domain> domains) {
      super(retrofit, storeId, appTokenId, domains);
    }

    @Override
    protected Call<ApplicationToken> getRequest(ApplicationTokenResource resource) {
      return resource.updateAppToken(
          storeId, appTokenId, new UpdateAppTokenReq(domains), idempotencyKey);
    }
  }

  public static class DeleteApplicationTokenRequestBuilder
      extends AbstractDeleteApplicationTokenRequestBuilder<
          DeleteApplicationTokenRequestBuilder, ApplicationTokenResource> {
    public DeleteApplicationTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, AppTokenId applicationTokenId) {
      super(retrofit, storeId, applicationTokenId);
    }

    @Override
    protected Call<Void> getRequest(ApplicationTokenResource resource) {
      return resource.deleteAppToken(storeId, applicationTokenId);
    }
  }

  public static class CreateApplicationTokenRequestBuilder
      extends AbstractCreateApplicationTokenRequestBuilder<
          CreateApplicationTokenRequestBuilder, ApplicationTokenResource, ApplicationToken> {

    public CreateApplicationTokenRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    public CreateApplicationTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, List<Domain> domains) {
      super(retrofit, storeId, domains);
    }

    @Override
    protected Call<ApplicationToken> getRequest(ApplicationTokenResource resource) {
      return resource.createAppToken(storeId, new CreateAppTokenReq(mode, domains));
    }
  }

  public static class CreateMerchantApplicationJWTRequestBuilder
      extends AbstractCreateMerchantApplicationJWTRequestBuilder<
          CreateMerchantApplicationJWTRequestBuilder,
          ApplicationTokenResource,
          MerchantApplicationJWT> {

    public CreateMerchantApplicationJWTRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<MerchantApplicationJWT> getRequest(ApplicationTokenResource resource) {
      return resource.createMerchantAppJWT(new CreateMerchantAppJWTReq(roles));
    }
  }

  public static class ListMerchantApplicationJWTRequestBuilder
      extends AbstractListMerchantApplicationJWTRequestBuilder<
          ListMerchantApplicationJWTRequestBuilder,
          ApplicationTokenResource,
          MerchantApplicationJWT> {

    public ListMerchantApplicationJWTRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<PaginatedList<MerchantApplicationJWT>> getRequest(
        ApplicationTokenResource resource) {
      return resource.listMerchantAppJWT();
    }
  }

  public static class DeleteMerchantApplicationJWTRequestBuilder
      extends AbstractDeleteMerchantApplicationJWTRequestBuilder<
          DeleteMerchantApplicationJWTRequestBuilder, ApplicationTokenResource> {

    public DeleteMerchantApplicationJWTRequestBuilder(Retrofit retrofit, AppJWTId appJWTId) {
      super(retrofit, appJWTId);
    }

    @Override
    protected Call<Void> getRequest(ApplicationTokenResource resource) {
      return resource.deleteMerchantAppJWT(appJWTId);
    }
  }

  public static class CreateStoreApplicationJWTRequestBuilder
      extends AbstractCreateStoreApplicationJWTRequestBuilder<
          CreateStoreApplicationJWTRequestBuilder, ApplicationTokenResource, StoreApplicationJWT> {

    public CreateStoreApplicationJWTRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<StoreApplicationJWT> getRequest(ApplicationTokenResource resource) {
      return resource.createStoreAppJWT(storeId, new CreateStoreAppJWTReq(mode, domains));
    }
  }

  public static class ListStoreApplicationJWTRequestBuilder
      extends AbstractListStoreApplicationJWTRequestBuilder<
          ListStoreApplicationJWTRequestBuilder, ApplicationTokenResource, StoreApplicationJWT> {

    public ListStoreApplicationJWTRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<PaginatedList<StoreApplicationJWT>> getRequest(
        ApplicationTokenResource resource) {
      return resource.listStoreAppJWT(storeId);
    }
  }

  public static class DeleteStoreApplicationJWTRequestBuilder
      extends AbstractDeleteStoreApplicationJWTRequestBuilder<
          DeleteStoreApplicationJWTRequestBuilder, ApplicationTokenResource> {

    public DeleteStoreApplicationJWTRequestBuilder(
        Retrofit retrofit, StoreId storeId, AppJWTId appJWTId) {
      super(retrofit, storeId, appJWTId);
    }

    @Override
    protected Call<Void> getRequest(ApplicationTokenResource resource) {
      return resource.deleteStoreAppJWT(storeId, appJWTId);
    }
  }
}
