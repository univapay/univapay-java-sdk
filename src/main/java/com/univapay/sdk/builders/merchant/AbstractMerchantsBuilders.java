package com.univapay.sdk.builders.merchant;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.EmptyEmailAddress;
import com.univapay.sdk.models.common.ResourceId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.models.common.VerificationPhoneNumber;
import com.univapay.sdk.models.response.merchant.MerchantCompanyContactInfo;
import com.univapay.sdk.models.response.merchant.MerchantVerificationData;
import com.univapay.sdk.models.response.merchant.MerchantWithConfiguration;
import com.univapay.sdk.models.response.merchant.Transaction;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.BusinessType;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.types.TransactionStatus;
import com.univapay.sdk.types.TransactionType;
import java.net.URL;
import java.util.Date;
import javax.annotation.Nullable;
import retrofit2.Retrofit;

public abstract class AbstractMerchantsBuilders {

  public abstract static class AbstractCreateMerchantVerificationRequestBuilder<
          B extends AbstractCreateMerchantVerificationRequestBuilder,
          R,
          M extends MerchantVerificationData>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected URL homepageUrl;
    protected String companyDescription;
    protected String contactInfoName;
    protected String companyName;
    protected VerificationPhoneNumber phoneNumber;
    protected String addressLine1;
    protected String state;
    protected String city;
    protected Country country;
    protected String zip;
    protected BusinessType businessType;
    protected String systemManagerName;
    protected String addressLine2;
    protected VerificationPhoneNumber systemManagerNumber;
    protected UnivapayEmailAddress systemManagerEmail;
    protected RecurringTokenPrivilege recurringTokenRequest;
    protected String recurringTokenRequestReason;
    protected Boolean allowEmptyCvv;

    protected URL getHomepageUrl() {
      return homepageUrl;
    }

    protected String getCompanyDescription() {
      return companyDescription;
    }

    protected String getContactInfoName() {
      return contactInfoName;
    }

    protected String getCompanyName() {
      return companyName;
    }

    protected MerchantCompanyContactInfo getContactInfo() {
      return new MerchantCompanyContactInfo(
          contactInfoName,
          companyName,
          phoneNumber,
          addressLine1,
          addressLine2,
          state,
          city,
          country,
          zip);
    }

    protected VerificationPhoneNumber getPhoneNumber() {
      return phoneNumber;
    }

    protected String getAddressLine1() {
      return addressLine1;
    }

    protected String getState() {
      return state;
    }

    protected String getCity() {
      return city;
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

    protected String getZip() {
      return zip;
    }

    protected BusinessType getBusinessType() {
      return businessType;
    }

    protected String getSystemManagerName() {
      return systemManagerName;
    }

    protected String getAddressLine2() {
      return addressLine2;
    }

    protected VerificationPhoneNumber getSystemManagerNumber() {
      return systemManagerNumber;
    }

    protected String getSystemManagerEmail() {
      if (systemManagerEmail == null) {
        return null;
      }
      return systemManagerEmail.serialize();
    }

    protected RecurringTokenPrivilege getRecurringTokenRequest() {
      return recurringTokenRequest;
    }

    protected String getRecurringTokenRequestReason() {
      return recurringTokenRequestReason;
    }

    protected Boolean getAllowEmptyCvv() {
      return allowEmptyCvv;
    }

    public AbstractCreateMerchantVerificationRequestBuilder(
        Retrofit retrofit,
        URL homepageUrl,
        String companyDescription,
        MerchantCompanyContactInfo companyContactInfo,
        BusinessType businessType,
        String systemManagerName) {
      super(retrofit);
      this.homepageUrl = homepageUrl;
      this.companyDescription = companyDescription;
      this.contactInfoName = companyContactInfo.getName();
      this.companyName = companyContactInfo.getCompanyName();
      this.phoneNumber = companyContactInfo.getPhoneNumber();
      this.addressLine1 = companyContactInfo.getAdressLine1();
      this.addressLine2 = companyContactInfo.getAdressLine2();
      this.state = companyContactInfo.getState();
      this.city = companyContactInfo.getCity();
      this.country = companyContactInfo.getCountryEnum();
      this.zip = companyContactInfo.getZip();
      this.businessType = businessType;
      this.systemManagerName = systemManagerName;
    }

    public B withSystemManagerNumber(VerificationPhoneNumber systemManagerNumber) {
      this.systemManagerNumber = systemManagerNumber;
      return (B) this;
    }

    public B withSystemManagerEmail(String systemManagerEmail) {
      this.systemManagerEmail = new EmailAddress(systemManagerEmail);
      return (B) this;
    }

    public B withRecurringTokenRequest(RecurringTokenPrivilege recurringTokenRequest) {
      this.recurringTokenRequest = recurringTokenRequest;
      return (B) this;
    }

    public B withRecurringTokenRequestReason(String recurringTokenRequestReason) {
      this.recurringTokenRequestReason = recurringTokenRequestReason;
      return (B) this;
    }

    public B withAllowEmptyCvv(Boolean allowEmptyCvv) {
      this.allowEmptyCvv = allowEmptyCvv;
      return (B) this;
    }
  }

  public abstract static class AbstractGetMerchantVerificationRequestBuilder<
          B extends AbstractGetMerchantVerificationRequestBuilder,
          R,
          M extends MerchantVerificationData>
      extends RetrofitRequestBuilder<M, R> {

    public AbstractGetMerchantVerificationRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractGetTransactionHistoryRequestBuilder<
          B extends AbstractGetTransactionHistoryRequestBuilder, R, M extends Transaction>
      extends RetrofitRequestBuilderPaginated<M, R, B, ResourceId> {
    protected StoreId storeId;
    protected Date from;
    protected Date to;
    protected TransactionStatus status;
    protected TransactionType type;
    protected ProcessingMode mode;
    protected String search;
    protected Boolean all;

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractGetTransactionHistoryRequestBuilder(
        Retrofit retrofit, @Nullable StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }

    public B withFromDate(Date from) {
      this.from = from;
      return (B) this;
    }

    public B withToDate(Date to) {
      this.to = to;
      return (B) this;
    }

    public B withStatus(TransactionStatus status) {
      this.status = status;
      return (B) this;
    }

    public B withTransactionType(TransactionType type) {
      this.type = type;
      return (B) this;
    }

    public B withProcessingMode(ProcessingMode mode) {
      this.mode = mode;
      return (B) this;
    }

    public B withSearch(String search) {
      this.search = search;
      return (B) this;
    }

    public B withAll(Boolean all) {
      this.all = all;
      return (B) this;
    }
  }

  public abstract static class AbstractUpdateMerchantVerificationRequestBuilder<
          B extends AbstractUpdateMerchantVerificationRequestBuilder,
          R,
          M extends MerchantVerificationData>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected URL homepageUrl;
    protected String companyDescription;
    protected String name;
    protected String companyName;
    protected VerificationPhoneNumber phoneNumber;
    protected String addressLine1;
    protected String addressLine2;
    protected String state;
    protected String city;
    protected Country country;
    protected String zip;
    protected BusinessType businessType;
    protected String systemManagerName;
    protected VerificationPhoneNumber systemManagerNumber;
    protected UnivapayEmailAddress systemManagerEmail;
    protected RecurringTokenPrivilege recurringTokenRequest;
    protected String recurringTokenRequestReason;
    protected Boolean allowEmptyCvv;

    protected URL getHomepageUrl() {
      return homepageUrl;
    }

    protected String getCompanyDescription() {
      return companyDescription;
    }

    protected String getName() {
      return name;
    }

    protected String getCompanyName() {
      return companyName;
    }

    protected VerificationPhoneNumber getPhoneNumber() {
      return phoneNumber;
    }

    protected String getAddressLine1() {
      return addressLine1;
    }

    protected String getAddressLine2() {
      return addressLine2;
    }

    protected String getState() {
      return state;
    }

    protected String getCity() {
      return city;
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

    protected String getZip() {
      return zip;
    }

    protected BusinessType getBusinessType() {
      return businessType;
    }

    protected String getSystemManagerName() {
      return systemManagerName;
    }

    protected VerificationPhoneNumber getSystemManagerNumber() {
      return systemManagerNumber;
    }

    protected String getSystemManagerEmail() {
      if (systemManagerEmail == null) {
        return null;
      }
      return systemManagerEmail.serialize();
    }

    protected RecurringTokenPrivilege getRecurringTokenRequest() {
      return recurringTokenRequest;
    }

    protected String getRecurringTokenRequestReason() {
      return recurringTokenRequestReason;
    }

    protected Boolean getAllowEmptyCvv() {
      return allowEmptyCvv;
    }

    protected MerchantCompanyContactInfo getCompayContactInfo() {
      return new MerchantCompanyContactInfo(
          name, companyName, phoneNumber, addressLine1, addressLine2, state, city, country, zip);
    }

    public AbstractUpdateMerchantVerificationRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    public B withHomepageUrl(URL homepageUrl) {
      this.homepageUrl = homepageUrl;
      return (B) this;
    }

    public B withCompanyDescription(String companyDescription) {
      this.companyDescription = companyDescription;
      return (B) this;
    }

    public B withName(String name) {
      this.name = name;
      return (B) this;
    }

    public B withCompanyName(String companyName) {
      this.companyName = companyName;
      return (B) this;
    }

    public B withPhoneNumber(VerificationPhoneNumber phoneNumber) {
      this.phoneNumber = phoneNumber;
      return (B) this;
    }

    public B withAddressLine1(String addressLine1) {
      this.addressLine1 = addressLine1;
      return (B) this;
    }

    public B withAddressLine2(String addressLine2) {
      this.addressLine2 = addressLine2;
      return (B) this;
    }

    public B withState(String state) {
      this.state = state;
      return (B) this;
    }

    public B withCity(String city) {
      this.city = city;
      return (B) this;
    }

    @Deprecated
    /** @deprecated This method will be deleted on later release */
    public B withCountry(String country) {
      this.country = Country.getCountryByAlpha2(country);
      return (B) this;
    }

    public B withCountry(Country country) {
      this.country = country;
      return (B) this;
    }

    public B withZip(String zip) {
      this.zip = zip;
      return (B) this;
    }

    public B withBusinessType(BusinessType businessType) {
      this.businessType = businessType;
      return (B) this;
    }

    public B withSystemManagerName(String systemManagerName) {
      this.systemManagerName = systemManagerName;
      return (B) this;
    }

    public B withSystemManagerNumber(VerificationPhoneNumber systemManagerNumber) {
      this.systemManagerNumber = systemManagerNumber;
      return (B) this;
    }

    public B withSystemManagerEmail(String systemManagerEmail) {
      this.systemManagerEmail = new EmailAddress(systemManagerEmail);
      return (B) this;
    }

    public B withRecurringTokenRequest(RecurringTokenPrivilege recurringTokenRequest) {
      this.recurringTokenRequest = recurringTokenRequest;
      return (B) this;
    }

    public B withRecurringTokenRequestReason(String recurringTokenRequestReason) {
      this.recurringTokenRequestReason = recurringTokenRequestReason;
      return (B) this;
    }

    public B withAllowEmptyCvv(Boolean allowEmptyCvv) {
      this.allowEmptyCvv = allowEmptyCvv;
      return (B) this;
    }

    public B removeSystemManagerEmail() {
      this.systemManagerEmail = new EmptyEmailAddress();
      return (B) this;
    }
  }

  public abstract static class AbstractGetMeRequestBuilder<
          B extends AbstractGetMeRequestBuilder, R, M extends MerchantWithConfiguration>
      extends RetrofitRequestBuilder<M, R> {

    public AbstractGetMeRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }
}
