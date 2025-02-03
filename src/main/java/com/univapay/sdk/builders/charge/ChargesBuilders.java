package com.univapay.sdk.builders.charge;

import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.charge.AbstractChargesBuilders.*;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.charge.CaptureReq;
import com.univapay.sdk.models.request.charge.ChargesReq;
import com.univapay.sdk.models.request.charge.PatchReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.resources.ChargesResource;
import com.univapay.sdk.types.ChargeStatus;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class ChargesBuilders {

  public static class ListChargesRequestBuilder
      extends AbstractListChargesRequestBuilder<
          ListChargesRequestBuilder, ChargesResource, Charge> {

    public ListChargesRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    public ListChargesRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<PaginatedList<Charge>> getRequest(ChargesResource resource) {
      if (storeId != null) {
        return resource.listAllStoreCharges(
            storeId,
            getLimit(),
            getCursorDirection(),
            getCursor(),
            propertySearch.asMap(),
            metadataSearch);
      } else {
        return resource.listAllCharges(
            getLimit(), getCursorDirection(), getCursor(), propertySearch.asMap(), metadataSearch);
      }
    }
  }

  public static class GetChargeRequestBuilder
      extends AbstractGetChargeRequestBuilder<GetChargeRequestBuilder, ChargesResource, Charge> {

    public GetChargeRequestBuilder(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<Charge> getRequest(ChargesResource resource) {
      return resource.getStoreCharge(storeId, chargeId, polling);
    }
  }

  public static class CreateChargeRequestBuilder
      extends AbstractCreateChargeRequestBuilder<
          CreateChargeRequestBuilder, ChargesResource, Charge> {

    public CreateChargeRequestBuilder(
        Retrofit retrofit,
        TransactionTokenId transactionTokenId,
        MoneyLike money,
        Boolean capture) {
      super(retrofit, transactionTokenId, money, capture);
    }

    @Override
    protected Call<Charge> getRequest(ChargesResource resource) {
      return resource.createCharge(
          new ChargesReq(
              transactionTokenId,
              money,
              capture,
              captureAt,
              metadata,
              onlyDirectCurrency,
              descriptor,
              threeDs),
          idempotencyKey);
    }
  }

  public static class UpdateChargeRequestBuilder
      extends AbstractUpdateChargeRequestBuilder<
          UpdateChargeRequestBuilder, ChargesResource, Charge> {

    public UpdateChargeRequestBuilder(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<Charge> getRequest(ChargesResource resource) {
      return resource.updateCharge(storeId, chargeId, new PatchReq(metadata), idempotencyKey);
    }
  }

  public static class CaptureAuthorizedChargeRequestBuilder
      extends AbstractCaptureAuthorizedChargeRequestBuilder<
          CaptureAuthorizedChargeRequestBuilder, ChargesResource> {

    public CaptureAuthorizedChargeRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, MoneyLike money) {
      super(retrofit, storeId, chargeId, money);
    }

    @Override
    protected Call<Void> getRequest(ChargesResource resource) {
      return resource.capture(storeId, chargeId, new CaptureReq(money), idempotencyKey);
    }
  }

  public static ResourceMonitor<Charge> createChargeCompletionMonitor(
      Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
    return new ResourceMonitor<>(
        new GetChargeRequestBuilder(retrofit, storeId, chargeId).withPolling(true),
        resource -> resource.getStatus() != ChargeStatus.PENDING);
  }
}
