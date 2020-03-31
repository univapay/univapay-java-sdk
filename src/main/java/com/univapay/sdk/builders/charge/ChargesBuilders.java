package com.univapay.sdk.builders.charge;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.ResourcePredicate;
import com.univapay.sdk.builders.charge.AbstractChargesBuilders.*;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.request.charge.CaptureReq;
import com.univapay.sdk.models.request.charge.ChargesReq;
import com.univapay.sdk.models.request.charge.PatchReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.transactiontoken.TemporaryTransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.resources.ChargesResource;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.UnivapayCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
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
              descriptor),
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
        new ResourcePredicate<Charge>() {
          @Override
          public boolean test(Charge resource) {
            return resource.getStatus() != ChargeStatus.PENDING;
          }
        });
  }

  public static class CreateChargeWithTokenAliasRequestBuilder
      extends AbstractCreateChargeWithTokenAliasRequestBuilder<
          CreateChargeWithTokenAliasRequestBuilder, Charge, TemporaryTransactionToken> {

    private UnivapaySDK univapay;

    public CreateChargeWithTokenAliasRequestBuilder(
        StoreId storeId,
        MoneyLike money,
        Boolean capture,
        UnivapaySDK univapay,
        TokenAliasKey alias) {
      super(storeId, alias, money, capture);
      this.univapay = univapay;
    }

    @Override
    public Charge dispatch()
        throws IOException, UnivapayException, TimeoutException, InterruptedException {
      if (polling) {
        return pollAndDispatch();
      } else {
        TemporaryTransactionToken temporaryTransactionToken =
            univapay.getTokenAlias(storeId, alias).build().dispatch();

        Charge charge = getChargeRequestBuilder(temporaryTransactionToken).build().dispatch();

        deleteTokenAlias(temporaryTransactionToken);

        return charge;
      }
    }

    @Override
    protected Charge pollAndDispatch()
        throws IOException, UnivapayException, TimeoutException, InterruptedException {
      TemporaryTransactionToken temporaryTransactionToken =
          univapay.getTokenAlias(storeId, alias).build().dispatch();

      Charge charge = getChargeRequestBuilder(temporaryTransactionToken).build().dispatch();

      deleteTokenAlias(temporaryTransactionToken);

      if (timeout != 0 && backoff != null) {
        charge =
            univapay
                .chargeCompletionMonitor(charge.getStoreId(), charge.getId())
                .await(timeout, backoff);
      } else if (timeout != 0) {
        charge =
            univapay.chargeCompletionMonitor(charge.getStoreId(), charge.getId()).await(timeout);
      } else {
        charge = univapay.chargeCompletionMonitor(charge.getStoreId(), charge.getId()).await();
      }

      return charge;
    }

    @Override
    public void dispatch(final UnivapayCallback<Charge> callback) {
      if (polling) {
        pollAndDispatch(callback);
      } else {
        univapay
            .getTokenAlias(storeId, alias)
            .build()
            .dispatch(
                new UnivapayCallback<TemporaryTransactionToken>() {
                  @Override
                  public void getResponse(
                      final TemporaryTransactionToken temporaryTransactionToken) {
                    getChargeRequestBuilder(temporaryTransactionToken)
                        .build()
                        .dispatch(
                            new UnivapayCallback<Charge>() {
                              // delete a token alias after create a charge
                              @Override
                              public void getResponse(final Charge charge) {
                                if (temporaryTransactionToken.getType()
                                    == TransactionTokenType.RECURRING) {
                                  univapay
                                      .deleteTokenAlias(storeId, alias)
                                      .build()
                                      .dispatch(
                                          new UnivapayCallback<Void>() {
                                            @Override
                                            public void getResponse(Void response) {
                                              callback.getResponse(charge);
                                            }

                                            @Override
                                            public void getFailure(Throwable error) {
                                              callback.getResponse(charge);
                                              callback.getFailure(error);
                                            }
                                          });
                                } else {
                                  callback.getResponse(charge);
                                }
                              }

                              @Override
                              public void getFailure(Throwable error) {
                                callback.getFailure(error);
                              }
                            });
                  }

                  @Override
                  public void getFailure(Throwable error) {
                    callback.getFailure(error);
                  }
                });
      }
    }

    @Override
    protected void pollAndDispatch(final UnivapayCallback<Charge> callback) {
      univapay
          .getTokenAlias(storeId, alias)
          .build()
          .dispatch(
              new UnivapayCallback<TemporaryTransactionToken>() {
                @Override
                public void getResponse(final TemporaryTransactionToken temporaryTransactionToken) {
                  getChargeRequestBuilder(temporaryTransactionToken)
                      .build()
                      .dispatch(
                          new UnivapayCallback<Charge>() {
                            // delete a token alias after create a charge
                            @Override
                            public void getResponse(final Charge charge) {
                              if (temporaryTransactionToken.getType()
                                  == TransactionTokenType.RECURRING) {
                                univapay
                                    .deleteTokenAlias(storeId, alias)
                                    .build()
                                    .dispatch(
                                        new UnivapayCallback<Void>() {
                                          @Override
                                          public void getResponse(Void response) {
                                            pollingCharge(charge, callback);
                                          }

                                          @Override
                                          public void getFailure(Throwable error) {
                                            pollingCharge(charge, callback);
                                            callback.getFailure(error);
                                          }
                                        });
                              } else {
                                pollingCharge(charge, callback);
                              }
                            }

                            @Override
                            public void getFailure(Throwable error) {
                              callback.getFailure(error);
                            }
                          });
                }

                @Override
                public void getFailure(Throwable error) {
                  callback.getFailure(error);
                }
              });
    }

    private CreateChargeRequestBuilder getChargeRequestBuilder(
        TemporaryTransactionToken temporaryTransactionToken) {
      checkIsActive(temporaryTransactionToken);
      checkIsNotSubscription(temporaryTransactionToken);
      MoneyLike createMoney =
          fillMoney(
              money,
              temporaryTransactionToken.getAmount(),
              temporaryTransactionToken.getCurrency());
      return univapay
          .createCharge(
              temporaryTransactionToken.getId(),
              createMoney.getAmount(),
              createMoney.getCurrency(),
              capture)
          .withDescriptor(descriptor, ignoreDescriptorOnError)
          .withOnlyDirectCurrency(onlyDirectCurrency)
          .withCaptureAt(captureAt)
          .withMetadata(metadata)
          .withIdempotencyKey(idempotencyKey);
    }

    private void deleteTokenAlias(TemporaryTransactionToken temporaryTransactionToken)
        throws IOException, UnivapayException {
      if (temporaryTransactionToken.getType() == TransactionTokenType.RECURRING) {
        univapay.deleteTokenAlias(storeId, alias).build().dispatch();
      }
    }

    private void pollingCharge(Charge charge, UnivapayCallback<Charge> callback) {
      if (timeout != 0 && backoff != null) {
        univapay
            .chargeCompletionMonitor(charge.getStoreId(), charge.getId())
            .await(callback, timeout, backoff);
      } else if (timeout != 0) {
        univapay
            .chargeCompletionMonitor(charge.getStoreId(), charge.getId())
            .await(callback, timeout);
      } else {
        univapay.chargeCompletionMonitor(charge.getStoreId(), charge.getId()).await(callback);
      }
    }
  }
}
