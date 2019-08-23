package com.univapay.sdk.builders.store;

import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.configuration.StoreConfigurationRequest;
import com.univapay.sdk.models.request.store.CustomerIdRequest;
import com.univapay.sdk.models.request.store.StoreCreateData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.store.CheckoutInfo;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.utils.builders.ConfigurationBuilder;
import com.univapay.sdk.builders.store.AbstractStoreBuilders.*;
import com.univapay.sdk.resources.StoresResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class StoreBuilders {

  public static class ListStoresRequestBuilder
      extends AbstractListStoresRequestBuilder<ListStoresRequestBuilder, StoresResource, Store> {

    public ListStoresRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<PaginatedList<Store>> getRequest(StoresResource resource) {
      return resource.list(getLimit(), getCursorDirection(), getCursor(), search);
    }
  }

  public static class DeleteStoreRequestBuilder
      extends AbstractDeleteStoreRequestBuilder<DeleteStoreRequestBuilder, StoresResource> {

    public DeleteStoreRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<Void> getRequest(StoresResource resource) {
      return resource.delete(storeId);
    }
  }

  public static class GetStoreRequestBuilder
      extends AbstractGetStoreRequestBuilder<
          GetStoreRequestBuilder, StoresResource, StoreWithConfiguration> {

    public GetStoreRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<StoreWithConfiguration> getRequest(StoresResource resource) {
      return resource.get(storeId);
    }
  }

  public static class UpdateStoreRequestBuilder
      extends AbstractUpdateStoreRequestBuilder<
          UpdateStoreRequestBuilder, StoresResource, StoreWithConfiguration> {

    public UpdateStoreRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<StoreWithConfiguration> getRequest(StoresResource resource) {
      return resource.update(
          storeId,
          new StoreCreateData(
              name,
              new ConfigurationBuilder<StoreConfigurationRequest>()
                  .withLogoUrl(logoUrl)
                  .withCountry(country)
                  .withLanguage(language)
                  .withTimeZone(timeZone)
                  .withUserTransactionsConfiguration(userTransactionsConfiguration)
                  .withCardConfiguration(cardConfiguration)
                  .withQrScanConfiguration(qrScanConfiguration)
                  .withConvenienceConfiguration(convenienceConfiguration)
                  .withPaidyConfiguration(paidyConfiguration)
                  .withQrMerchantConfiguration(qrMerchantConfiguration)
                  .withRecurringConfiguration(recurringTokenConfiguration)
                  .withSecurityConfiguration(securityConfiguration)
                  .withCardBrandPercentFees(cardBrandPercentFees)
                  .withSubscriptionConfiguration(subscriptionConfiguration)
                  .build()),
          idempotencyKey);
    }
  }

  public static class CreateStoreRequestBuilder
      extends AbstractCreateStoreRequestBuilder<
          CreateStoreRequestBuilder, StoresResource, StoreWithConfiguration> {

    public CreateStoreRequestBuilder(Retrofit retrofit, String name) {
      super(retrofit, name);
    }

    @Override
    protected Call<StoreWithConfiguration> getRequest(StoresResource resource) {
      return resource.create(
          new StoreCreateData(
              name,
              new ConfigurationBuilder<StoreConfigurationRequest>()
                  .withLogoUrl(logoUrl)
                  .withLanguage(language)
                  .withCountry(country)
                  .withTimeZone(timeZone)
                  .withUserTransactionsConfiguration(userTransactionsConfiguration)
                  .withCardConfiguration(cardConfiguration)
                  .withQrScanConfiguration(qrScanConfiguration)
                  .withConvenienceConfiguration(convenienceConfiguration)
                  .withPaidyConfiguration(paidyConfiguration)
                  .withQrMerchantConfiguration(qrMerchantConfiguration)
                  .withRecurringConfiguration(recurringTokenConfiguration)
                  .withSecurityConfiguration(securityConfiguration)
                  .withCardBrandPercentFees(cardBrandPercentFees)
                  .withSubscriptionConfiguration(subscriptionConfiguration)
                  .build()),
          idempotencyKey);
    }
  }

  public static class GetCheckoutInfoRequestBuilder
      extends AbstractGetCheckoutInfoRequestBuilder<
          GetCheckoutInfoRequestBuilder, StoresResource, CheckoutInfo> {

    public GetCheckoutInfoRequestBuilder(Retrofit retrofit, Domain origin) {
      super(retrofit, origin);
    }

    public GetCheckoutInfoRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<CheckoutInfo> getRequest(StoresResource resource) {
      return resource.getCheckoutInfo(origin);
    }
  }

  public static class CreateCustomerIdRequestBuilder
      extends AbstractCreateCustomerIdRequestBuilder<
          CreateCustomerIdRequestBuilder, StoresResource, UnivapayCustomerId> {

    public CreateCustomerIdRequestBuilder(Retrofit retrofit, StoreId storeId, String customerId) {
      super(retrofit, storeId, customerId);
    }

    @Override
    protected Call<UnivapayCustomerId> getRequest(StoresResource resource) {
      return resource.createCustomerId(storeId, new CustomerIdRequest(customerId));
    }
  }
}
