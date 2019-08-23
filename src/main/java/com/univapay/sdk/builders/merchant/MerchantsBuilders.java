package com.univapay.sdk.builders.merchant;

import com.univapay.sdk.builders.merchant.AbstractMerchantsBuilders.AbstractCreateMerchantVerificationRequestBuilder;
import com.univapay.sdk.builders.merchant.AbstractMerchantsBuilders.AbstractGetMeRequestBuilder;
import com.univapay.sdk.builders.merchant.AbstractMerchantsBuilders.AbstractGetMerchantVerificationRequestBuilder;
import com.univapay.sdk.builders.merchant.AbstractMerchantsBuilders.AbstractGetTransactionHistoryRequestBuilder;
import com.univapay.sdk.builders.merchant.AbstractMerchantsBuilders.AbstractUpdateMerchantVerificationRequestBuilder;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.request.merchant.MerchantsReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.merchant.MerchantCompanyContactInfo;
import com.univapay.sdk.models.response.merchant.MerchantVerificationData;
import com.univapay.sdk.models.response.merchant.MerchantWithConfiguration;
import com.univapay.sdk.models.response.merchant.Transaction;
import com.univapay.sdk.types.BusinessType;
import java.net.URL;
import javax.annotation.Nullable;
import com.univapay.sdk.resources.MerchantsResource;
import org.joda.time.format.ISODateTimeFormat;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class MerchantsBuilders {

  public static class CreateMerchantVerificationRequestBuilder
      extends AbstractCreateMerchantVerificationRequestBuilder<
                CreateMerchantVerificationRequestBuilder, MerchantsResource, MerchantVerificationData> {

    public CreateMerchantVerificationRequestBuilder(
        Retrofit retrofit,
        URL homepageUrl,
        String companyDescription,
        MerchantCompanyContactInfo companyContactInfo,
        BusinessType businessType,
        String systemManagerName) {
      super(
          retrofit,
          homepageUrl,
          companyDescription,
          companyContactInfo,
          businessType,
          systemManagerName);
    }

    @Override
    protected Call<MerchantVerificationData> getRequest(MerchantsResource resource) {
      return resource.createVerification(
          new MerchantsReq(
              homepageUrl,
              companyDescription,
              getContactInfo(),
              businessType,
              systemManagerName,
              systemManagerNumber,
              systemManagerEmail,
              recurringTokenRequest,
              recurringTokenRequestReason,
              allowEmptyCvv),
          idempotencyKey);
    }
  }

  public static class GetMerchantVerificationRequestBuilder
      extends AbstractGetMerchantVerificationRequestBuilder<
                GetMerchantVerificationRequestBuilder, MerchantsResource, MerchantVerificationData> {

    public GetMerchantVerificationRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<MerchantVerificationData> getRequest(MerchantsResource resource) {
      return resource.getVerification();
    }
  }

  public static class GetTransactionHistoryRequestBuilder
      extends AbstractGetTransactionHistoryRequestBuilder<
                GetTransactionHistoryRequestBuilder, MerchantsResource, Transaction> {

    public GetTransactionHistoryRequestBuilder(Retrofit retrofit, @Nullable StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<PaginatedList<Transaction>> getRequest(MerchantsResource resource) {
      String fromString =
          (from == null) ? null : ISODateTimeFormat.dateTime().print(from.getTime());
      String toString = (to == null) ? null : ISODateTimeFormat.dateTime().print(to.getTime());

      if (storeId == null) {
        return resource.getTransactionHistory(
            getLimit(),
            getCursorDirection(),
            getCursor(),
            fromString,
            toString,
            status,
            type,
            mode,
            search,
            all);
      }
      return resource.getStoreTransactionHistory(
          storeId,
          getLimit(),
          getCursorDirection(),
          getCursor(),
          fromString,
          toString,
          status,
          type,
          mode,
          search,
          all);
    }
  }

  public static class UpdateMerchantVerificationRequestBuilder
      extends AbstractUpdateMerchantVerificationRequestBuilder<
                UpdateMerchantVerificationRequestBuilder, MerchantsResource, MerchantVerificationData> {

    public UpdateMerchantVerificationRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<MerchantVerificationData> getRequest(MerchantsResource resource) {
      return resource.updateVerification(
          new MerchantsReq(
              homepageUrl,
              companyDescription,
              getCompayContactInfo(),
              businessType,
              systemManagerName,
              systemManagerNumber,
              systemManagerEmail,
              recurringTokenRequest,
              recurringTokenRequestReason,
              allowEmptyCvv),
          idempotencyKey);
    }
  }

  public static class GetMeRequestBuilder
      extends AbstractGetMeRequestBuilder<
                GetMeRequestBuilder, MerchantsResource, MerchantWithConfiguration> {

    public GetMeRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<MerchantWithConfiguration> getRequest(MerchantsResource resource) {
      return resource.me();
    }
  }
}
