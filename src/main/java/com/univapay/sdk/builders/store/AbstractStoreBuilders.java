package com.univapay.sdk.builders.store;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.BankTransferConfiguration;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.KonbiniConfiguration;
import com.univapay.sdk.models.common.OnlineConfiguration;
import com.univapay.sdk.models.common.PaidyConfiguration;
import com.univapay.sdk.models.common.QrMerchantConfiguration;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.common.UserTransactionsConfiguration;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.response.store.*;
import com.univapay.sdk.models.response.store.CardConfiguration;
import com.univapay.sdk.models.response.store.CheckoutInfo;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.models.response.subscription.SubscriptionConfiguration;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.math.BigDecimal;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import retrofit2.Retrofit;

public abstract class AbstractStoreBuilders {

  public abstract static class AbstractListStoresRequestBuilder<
          B extends AbstractListStoresRequestBuilder, R, M extends Store>
      extends RetrofitRequestBuilderPaginated<M, R, B, StoreId> {

    protected String search;

    protected String getSearch() {
      return search;
    }

    public AbstractListStoresRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    public B withSearch(String searchString) {
      this.search = searchString;
      return (B) this;
    }
  }

  public abstract static class AbstractDeleteStoreRequestBuilder<
          B extends AbstractDeleteStoreRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    protected StoreId storeId;

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractDeleteStoreRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }
  }

  public abstract static class AbstractGetStoreRequestBuilder<
          B extends AbstractGetStoreRequestBuilder, R, M extends StoreWithConfiguration>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractGetStoreRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }
  }

  public abstract static class AbstractUpdateStoreRequestBuilder<
          B extends AbstractUpdateStoreRequestBuilder, R, M extends StoreWithConfiguration>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected StoreId storeId;
    protected String name;
    protected URL logoUrl;
    protected Locale language;
    protected Country country;
    protected ZoneId timeZone;
    protected UserTransactionsConfiguration userTransactionsConfiguration;
    protected CardConfiguration cardConfiguration;
    protected QrScanConfiguration qrScanConfiguration;
    protected KonbiniConfiguration convenienceConfiguration;
    protected PaidyConfiguration paidyConfiguration;
    protected QrMerchantConfiguration qrMerchantConfiguration;
    protected RecurringTokenConfiguration recurringTokenConfiguration;
    protected SecurityConfiguration securityConfiguration;
    protected Map<CardBrand, BigDecimal> cardBrandPercentFees;
    protected SubscriptionConfiguration subscriptionConfiguration;
    protected OnlineConfiguration onlineConfiguration;
    protected BankTransferConfiguration bankTransferConfiguration;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected String getName() {
      return name;
    }

    protected URL getLogoUrl() {
      return logoUrl;
    }

    protected Locale getLanguage() {
      return language;
    }

    public ZoneId getTimeZone() {
      return timeZone;
    }

    public UserTransactionsConfiguration getUserTransactionsConfiguration() {
      return userTransactionsConfiguration;
    }

    protected CardConfiguration getCardConfiguration() {
      return cardConfiguration;
    }

    protected QrScanConfiguration getQrScanConfiguration() {
      return qrScanConfiguration;
    }

    protected KonbiniConfiguration getConvenienceConfiguration() {
      return convenienceConfiguration;
    }

    protected PaidyConfiguration getPaidyConfiguration() {
      return paidyConfiguration;
    }

    protected QrMerchantConfiguration getQrMerchantConfiguration() {
      return qrMerchantConfiguration;
    }

    protected RecurringTokenConfiguration getRecurringTokenConfiguration() {
      return recurringTokenConfiguration;
    }

    protected SecurityConfiguration getSecurityConfiguration() {
      return securityConfiguration;
    }

    protected Map<CardBrand, BigDecimal> getCardBrandPercentFees() {
      return cardBrandPercentFees;
    }

    protected BankTransferConfiguration getBankTransferConfiguration() {
      return bankTransferConfiguration;
    }
    /**
     * The returned type will be changed to {@link Country} on later release
     *
     * @return country
     */
    protected String getCountry() {
      if (country == null) {
        return null;
      }
      return country.getAlpha2();
    }

    /**
     * This method will be deleted when the returned type by "getCountry(String)" is changed to
     * {@link Country}
     *
     * @return country enum
     */
    protected Country getCountryEnum() {
      return country;
    }

    public AbstractUpdateStoreRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }

    public B withName(String name) {
      this.name = name;
      return (B) this;
    }

    public B withLogoUrl(URL logoUrl) {
      this.logoUrl = logoUrl;
      return (B) this;
    }

    public B withLanguage(Locale language) {
      this.language = language;
      return (B) this;
    }

    public B withCountry(Country country) {
      this.country = country;
      return (B) this;
    }

    public B withTimeZone(ZoneId timeZone) {
      this.timeZone = timeZone;
      return (B) this;
    }

    public B withUserTransactionsConfiguration(
        UserTransactionsConfiguration userTransactionsConfiguration) {
      this.userTransactionsConfiguration = userTransactionsConfiguration;
      return (B) this;
    }

    public B withCardConfiguration(CardConfiguration cardConfiguration) {
      this.cardConfiguration = cardConfiguration;
      return (B) this;
    }

    public B withQrScanConfiguration(QrScanConfiguration qrScanConfiguration) {
      this.qrScanConfiguration = qrScanConfiguration;
      return (B) this;
    }

    public B withConvenienceConfiguration(KonbiniConfiguration convenienceConfiguration) {
      this.convenienceConfiguration = convenienceConfiguration;
      return (B) this;
    }

    public B withPaidyConfiguration(PaidyConfiguration paidyConfiguration) {
      this.paidyConfiguration = paidyConfiguration;
      return (B) this;
    }

    public B withQrMerchantConfiguration(QrMerchantConfiguration qrMerchantConfiguration) {
      this.qrMerchantConfiguration = qrMerchantConfiguration;
      return (B) this;
    }

    public B withRecurringTokenConfiguration(
        RecurringTokenConfiguration recurringTokenConfiguration) {
      this.recurringTokenConfiguration = recurringTokenConfiguration;
      return (B) this;
    }

    public B withSecurityConfiguration(SecurityConfiguration securityConfiguration) {
      this.securityConfiguration = securityConfiguration;
      return (B) this;
    }

    public B withCardBrandPercentFees(Map<CardBrand, BigDecimal> cardBrandPercentFees) {
      this.cardBrandPercentFees = cardBrandPercentFees;
      return (B) this;
    }

    public B withSubscriptionConfiguration(SubscriptionConfiguration subscriptionConfiguration) {
      this.subscriptionConfiguration = subscriptionConfiguration;
      return (B) this;
    }

    public B withOnlineConfiguration(OnlineConfiguration onlineConfiguration) {
      this.onlineConfiguration = onlineConfiguration;
      return (B) this;
    }

    public B withBankTransferConfiguration(BankTransferConfiguration bankTransferConfiguration) {
      this.bankTransferConfiguration = bankTransferConfiguration;
      return (B) this;
    }
  }

  public abstract static class AbstractCreateStoreRequestBuilder<
          B extends AbstractCreateStoreRequestBuilder<B, R, M>, R, M extends StoreWithConfiguration>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected String name;
    protected URL logoUrl;
    protected Locale language;
    protected Country country;
    protected ZoneId timeZone;
    protected UserTransactionsConfiguration userTransactionsConfiguration;
    protected CardConfiguration cardConfiguration;
    protected QrScanConfiguration qrScanConfiguration;
    protected KonbiniConfiguration convenienceConfiguration;
    protected PaidyConfiguration paidyConfiguration;
    protected QrMerchantConfiguration qrMerchantConfiguration;
    protected RecurringTokenConfiguration recurringTokenConfiguration;
    protected SecurityConfiguration securityConfiguration;
    protected Map<CardBrand, BigDecimal> cardBrandPercentFees;
    protected SubscriptionConfiguration subscriptionConfiguration;
    protected OnlineConfiguration onlineConfiguration;
    protected BankTransferConfiguration bankTransferConfiguration;
    protected List<MoneyLike> minimumChargeAmounts;

    protected String getName() {
      return name;
    }

    protected URL getLogoUrl() {
      return logoUrl;
    }

    protected Locale getLanguage() {
      return language;
    }

    public ZoneId getTimeZone() {
      return timeZone;
    }

    public UserTransactionsConfiguration getUserTransactionsConfiguration() {
      return userTransactionsConfiguration;
    }

    protected CardConfiguration getCardConfiguration() {
      return cardConfiguration;
    }

    protected QrScanConfiguration getQrScanConfiguration() {
      return qrScanConfiguration;
    }

    public KonbiniConfiguration getConvenienceConfiguration() {
      return convenienceConfiguration;
    }

    protected PaidyConfiguration getPaidyConfiguration() {
      return paidyConfiguration;
    }

    protected QrMerchantConfiguration getQrMerchantConfiguration() {
      return qrMerchantConfiguration;
    }

    protected RecurringTokenConfiguration getRecurringTokenConfiguration() {
      return recurringTokenConfiguration;
    }

    protected SecurityConfiguration getSecurityConfiguration() {
      return securityConfiguration;
    }

    protected Map<CardBrand, BigDecimal> getCardBrandPercentFees() {
      return cardBrandPercentFees;
    }

    protected Country getCountry() {
      return country;
    }

    public SubscriptionConfiguration getSubscriptionConfiguration() {
      return subscriptionConfiguration;
    }

    public OnlineConfiguration getOnlineConfiguration() {
      return onlineConfiguration;
    }

    public BankTransferConfiguration getBankTransferConfiguration() {
      return bankTransferConfiguration;
    }

    public List<MoneyLike> getMinimumChargeAmounts() {
      return minimumChargeAmounts;
    }

    public AbstractCreateStoreRequestBuilder(Retrofit retrofit, String name) {
      super(retrofit);
      this.name = name;
    }

    public B withCountry(Country country) {
      this.country = country;
      return (B) this;
    }

    public B withLogoUrl(URL logoUrl) {
      this.logoUrl = logoUrl;
      return (B) this;
    }

    public B withLanguage(Locale language) {
      this.language = language;
      return (B) this;
    }

    public B withTimeZone(ZoneId timeZone) {
      this.timeZone = timeZone;
      return (B) this;
    }

    public B withUserTransactionsConfiguration(
        UserTransactionsConfiguration userTransactionsConfiguration) {
      this.userTransactionsConfiguration = userTransactionsConfiguration;
      return (B) this;
    }

    public B withCardConfiguration(CardConfiguration cardConfiguration) {
      this.cardConfiguration = cardConfiguration;
      return (B) this;
    }

    public B withQrScanConfiguration(QrScanConfiguration qrScanConfiguration) {
      this.qrScanConfiguration = qrScanConfiguration;
      return (B) this;
    }

    public B withConvenienceConfiguration(KonbiniConfiguration convenienceConfiguration) {
      this.convenienceConfiguration = convenienceConfiguration;
      return (B) this;
    }

    public B withPaidyConfiguration(PaidyConfiguration paidyConfiguration) {
      this.paidyConfiguration = paidyConfiguration;
      return (B) this;
    }

    public B withQrMerchantConfiguration(QrMerchantConfiguration qrMerchantConfiguration) {
      this.qrMerchantConfiguration = qrMerchantConfiguration;
      return (B) this;
    }

    public B withRecurringTokenConfiguration(
        RecurringTokenConfiguration recurringTokenConfiguration) {
      this.recurringTokenConfiguration = recurringTokenConfiguration;
      return (B) this;
    }

    public B withSecurityConfiguration(SecurityConfiguration securityConfiguration) {
      this.securityConfiguration = securityConfiguration;
      return (B) this;
    }

    public B withCardBrandPercentFees(Map<CardBrand, BigDecimal> cardBrandPercentFees) {
      this.cardBrandPercentFees = cardBrandPercentFees;
      return (B) this;
    }

    public B withSubscriptionConfiguration(SubscriptionConfiguration subscriptionConfiguration) {
      this.subscriptionConfiguration = subscriptionConfiguration;
      return (B) this;
    }

    public B withOnlineConfiguration(OnlineConfiguration onlineConfiguration) {
      this.onlineConfiguration = onlineConfiguration;
      return (B) this;
    }

    public B withBankTransferConfiguration(BankTransferConfiguration bankTransferConfiguration) {
      this.bankTransferConfiguration = bankTransferConfiguration;
      return (B) this;
    }
    
    public B withMinimumChargeAmounts(List<MoneyLike> minimumChargeAmounts) {
      this.minimumChargeAmounts = minimumChargeAmounts;
      return (B) this;
    }
  }

  public abstract static class AbstractGetCheckoutInfoRequestBuilder<
          B extends AbstractGetCheckoutInfoRequestBuilder, R, M extends CheckoutInfo>
      extends RetrofitRequestBuilder<M, R> {
    protected Domain origin;

    protected Domain getOrigin() {
      return origin;
    }

    public AbstractGetCheckoutInfoRequestBuilder(Retrofit retrofit, Domain origin) {
      super(retrofit);
      this.origin = origin;
    }

    public AbstractGetCheckoutInfoRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractCreateCustomerIdRequestBuilder<
          B extends AbstractCreateCustomerIdRequestBuilder, R, M extends UnivapayCustomerId>
      extends RetrofitRequestBuilder<M, R> {

    protected String customerId;
    protected StoreId storeId;

    public String getCustomerId() {
      return customerId;
    }

    public StoreId getStoreId() {
      return storeId;
    }

    public AbstractCreateCustomerIdRequestBuilder(
        Retrofit retrofit, StoreId storeId, String customerId) {
      super(retrofit);
      this.storeId = storeId;
      this.customerId = customerId;
    }
  }
}
