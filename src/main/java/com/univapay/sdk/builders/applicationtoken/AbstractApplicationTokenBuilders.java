package com.univapay.sdk.builders.applicationtoken;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.AppJWTId;
import com.univapay.sdk.models.common.AppTokenId;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.applicationtoken.ApplicationToken;
import com.univapay.sdk.models.response.applicationtoken.MerchantApplicationJWT;
import com.univapay.sdk.models.response.applicationtoken.StoreApplicationJWT;
import com.univapay.sdk.types.MerchantRole;
import com.univapay.sdk.types.ProcessingMode;
import java.util.List;
import java.util.Set;
import retrofit2.Retrofit;

public abstract class AbstractApplicationTokenBuilders {

  public abstract static class AbstractListApplicationTokenRequestBuilder<
          B extends AbstractListApplicationTokenRequestBuilder, R, M extends ApplicationToken>
      extends RetrofitRequestBuilderPaginated<M, R, B, AppTokenId> {

    protected ProcessingMode processingMode;
    protected StoreId storeId;

    protected ProcessingMode getProcessingMode() {
      return processingMode;
    }

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractListApplicationTokenRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }

    public B withAnyMode() {
      this.processingMode = null;
      return (B) this;
    }

    public B withOnlyLiveMode() {
      this.processingMode = ProcessingMode.LIVE;
      return (B) this;
    }

    public B withOnlyTestMode() {
      this.processingMode = ProcessingMode.TEST;
      return (B) this;
    }
  }

  public abstract static class AbstractUpdateApplicationTokenRequestBuilder<
          B extends AbstractUpdateApplicationTokenRequestBuilder, R, M extends ApplicationToken>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected StoreId storeId;
    protected AppTokenId appTokenId;
    protected List<Domain> domains;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected AppTokenId getAppTokenId() {
      return appTokenId;
    }

    protected List<Domain> getDomains() {
      return domains;
    }

    public AbstractUpdateApplicationTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, AppTokenId appTokenId, List<Domain> domains) {
      super(retrofit);
      this.storeId = storeId;
      this.appTokenId = appTokenId;
      this.domains = domains;
    }

    public B withDomains(List<Domain> domains) {
      this.domains = domains;
      return (B) this;
    }
  }

  public abstract static class AbstractDeleteApplicationTokenRequestBuilder<
          B extends AbstractDeleteApplicationTokenRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    protected StoreId storeId;
    protected AppTokenId applicationTokenId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected AppTokenId getApplicationTokenId() {
      return applicationTokenId;
    }

    public AbstractDeleteApplicationTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, AppTokenId applicationTokenId) {
      super(retrofit);
      this.storeId = storeId;
      this.applicationTokenId = applicationTokenId;
    }
  }

  public abstract static class AbstractCreateApplicationTokenRequestBuilder<
          B extends AbstractCreateApplicationTokenRequestBuilder, R, M extends ApplicationToken>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected ProcessingMode mode = ProcessingMode.TEST;
    protected List<Domain> domains;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ProcessingMode getMode() {
      return mode;
    }

    protected List<Domain> getDomains() {
      return domains;
    }

    public AbstractCreateApplicationTokenRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }

    public AbstractCreateApplicationTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, List<Domain> domains) {
      super(retrofit);
      this.storeId = storeId;
      this.domains = domains;
    }

    public B withMode(ProcessingMode processingMode) {
      this.mode = processingMode;
      return (B) this;
    }

    @Deprecated
    public B withDomains(List<Domain> domains) {
      this.domains = domains;
      return (B) this;
    }
  }

  public abstract static class AbstractCreateMerchantApplicationJWTRequestBuilder<
          B extends AbstractCreateMerchantApplicationJWTRequestBuilder,
          R,
          M extends MerchantApplicationJWT>
      extends RetrofitRequestBuilder<M, R> {

    protected Set<MerchantRole> roles;

    protected Set<MerchantRole> getRoles() {
      return roles;
    }

    public AbstractCreateMerchantApplicationJWTRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    public B withRoles(Set<MerchantRole> roles) {
      this.roles = roles;
      return (B) this;
    }
  }

  public abstract static class AbstractListMerchantApplicationJWTRequestBuilder<
          B extends AbstractListMerchantApplicationJWTRequestBuilder,
          R,
          M extends MerchantApplicationJWT>
      extends RetrofitRequestBuilder<PaginatedList<M>, R> {

    public AbstractListMerchantApplicationJWTRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractDeleteMerchantApplicationJWTRequestBuilder<
          B extends AbstractDeleteMerchantApplicationJWTRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    protected AppJWTId appJWTId;

    public AppJWTId getAppJWTId() {
      return appJWTId;
    }

    public AbstractDeleteMerchantApplicationJWTRequestBuilder(
        Retrofit retrofit, AppJWTId appJWTId) {
      super(retrofit);
      this.appJWTId = appJWTId;
    }
  }

  public abstract static class AbstractCreateStoreApplicationJWTRequestBuilder<
          B extends AbstractCreateStoreApplicationJWTRequestBuilder,
          R,
          M extends StoreApplicationJWT>
      extends RetrofitRequestBuilder<M, R> {

    protected ProcessingMode mode;
    protected StoreId storeId;
    protected List<Domain> domains;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ProcessingMode getMode() {
      return mode;
    }

    public AbstractCreateStoreApplicationJWTRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }

    public B withMode(ProcessingMode processingMode) {
      this.mode = processingMode;
      return (B) this;
    }

    public B withDomains(List<Domain> domains) {
      this.domains = domains;
      return (B) this;
    }
  }

  public abstract static class AbstractListStoreApplicationJWTRequestBuilder<
          B extends AbstractListStoreApplicationJWTRequestBuilder, R, M extends StoreApplicationJWT>
      extends RetrofitRequestBuilder<PaginatedList<M>, R> {

    protected StoreId storeId;

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractListStoreApplicationJWTRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }
  }

  public abstract static class AbstractDeleteStoreApplicationJWTRequestBuilder<
          B extends AbstractDeleteStoreApplicationJWTRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    protected AppJWTId appJWTId;
    protected StoreId storeId;

    protected AppJWTId getAppJWTId() {
      return appJWTId;
    }

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractDeleteStoreApplicationJWTRequestBuilder(
        Retrofit retrofit, StoreId storeId, AppJWTId appJWTId) {
      super(retrofit);
      this.appJWTId = appJWTId;
      this.storeId = storeId;
    }
  }
}
