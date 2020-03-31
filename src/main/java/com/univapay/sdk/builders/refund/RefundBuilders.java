package com.univapay.sdk.builders.refund;

import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.ResourcePredicate;
import com.univapay.sdk.builders.refund.AbstractRefundBuilders.AbstractCreateRefundRequestBuilder;
import com.univapay.sdk.builders.refund.AbstractRefundBuilders.AbstractGetRefundRequestBuilder;
import com.univapay.sdk.builders.refund.AbstractRefundBuilders.AbstractListRefundsRequestBuilder;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.resources.RefundsResource;
import com.univapay.sdk.types.RefundReason;
import com.univapay.sdk.types.RefundStatus;
import java.math.BigInteger;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class RefundBuilders {

  public static class ListRefundsRequestBuilder
      extends AbstractListRefundsRequestBuilder<
          ListRefundsRequestBuilder, RefundsResource, Refund> {

    public ListRefundsRequestBuilder(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<PaginatedList<Refund>> getRequest(RefundsResource resource) {
      return resource.list(
          storeId, chargeId, getLimit(), getCursorDirection(), getCursor(), metadataSearch);
    }
  }

  public static class GetRefundRequestBuilder
      extends AbstractGetRefundRequestBuilder<GetRefundRequestBuilder, RefundsResource, Refund> {

    public GetRefundRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, RefundId refundId) {
      super(retrofit, storeId, chargeId, refundId);
    }

    @Override
    protected Call<Refund> getRequest(RefundsResource resource) {
      return resource.get(storeId, chargeId, refundId, polling);
    }
  }

  public static class CreateRefundRequestBuilder
      extends AbstractCreateRefundRequestBuilder<
          CreateRefundRequestBuilder, RefundsResource, Refund> {

    public CreateRefundRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        ChargeId chargeId,
        BigInteger amount,
        String currency,
        RefundReason reason) {
      super(retrofit, storeId, chargeId, amount, currency, reason);
    }

    @Override
    protected Call<Refund> getRequest(RefundsResource resource) {
      return resource.create(storeId, chargeId, getData(), idempotencyKey);
    }
  }

  public static ResourceMonitor<Refund> createRefundCompletionMonitor(
      Retrofit retrofit, StoreId storeId, ChargeId chargeId, RefundId refundId) {
    return new ResourceMonitor<>(
        new RefundBuilders.GetRefundRequestBuilder(retrofit, storeId, chargeId, refundId)
            .withPolling(true),
        new ResourcePredicate<Refund>() {
          @Override
          public boolean test(Refund resource) {
            return resource.getStatus() != RefundStatus.PENDING;
          }
        });
  }
}
