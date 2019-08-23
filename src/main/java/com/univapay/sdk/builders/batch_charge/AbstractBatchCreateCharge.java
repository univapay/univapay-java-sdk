package com.univapay.sdk.builders.batch_charge;

import com.univapay.sdk.builders.CreateChargeCompletionMonitor;
import com.univapay.sdk.builders.charge.AbstractChargesBuilders.AbstractCreateChargeRequestBuilder;
import com.univapay.sdk.builders.charge.AbstractChargesBuilders.AbstractGetChargeRequestBuilder;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.utils.ExponentialBackoffSleeper;
import com.univapay.sdk.utils.Sleeper;
import com.univapay.sdk.utils.functions.EndoFunction;
import com.univapay.sdk.utils.functions.UnivapayFunctions;
import com.univapay.sdk.utils.streams.StreamOptions;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;

/**
 * Request builder for creating charges in batch processing.
 *
 * <p>How to use:
 *
 * <pre>{@code
 * BatchCreateCharge batch = univapay.batchCreateCharge();
 * for (Invoice i : targetInvoicesThisMonth) {
 *    batch.add(univapay.createCharge(i.getTransactionToken(), i.getTotalAmount(), "JPY"));
 * }
 *
 * CreateChargeResult[] results = batch.execute();
 * for (CreateChargeResult r : results) {
 *     if (r.createChargeException != null) {
 *         assert r.charge == null;
 *         // the charge creation request failed.
 *         ...
 *     } else if (r.statusCheckException != null) {
 *         assert r.charge != null;
 *         // Failed to check the charge status.
 *         ...
 *     } else {
 *         assert r.charge != null;
 *         assert r.charge.getStatus() != ChargeStatus.PENDING
 *         // Save charge result.
 *         ...
 *     }
 * }
 * }</pre>
 */
public abstract class AbstractBatchCreateCharge<
        M extends Charge,
        B extends AbstractCreateChargeRequestBuilder<B, ?, M>,
        G extends AbstractGetChargeRequestBuilder<G, ?, M>>
    implements CreateChargeCompletionMonitor<M> {

  private final Retrofit retrofit;

  private final int createMaxRetry;

  private final int statusCheckTimeout;

  private StreamOptions streamOptions;

  private List<B> builders = new ArrayList<>();

  public AbstractBatchCreateCharge(Retrofit retrofit, int createMaxRetry, int statusCheckTimeout) {
    this.retrofit = retrofit;

    this.createMaxRetry = createMaxRetry;
    this.statusCheckTimeout = statusCheckTimeout;
    this.streamOptions = StreamOptions.newInstance();
  }

  public void setStreamOptions(StreamOptions streamOptions) {
    this.streamOptions = streamOptions;
  }

  private Flowable<B> getChargesFlow() {
    return Flowable.fromIterable(this.builders);
  }

  /**
   * Add create charge request builder to request queue.
   *
   * @param builder Create charge builder
   * @return
   */
  public AbstractBatchCreateCharge add(B builder) {
    builders.add(builder);
    return this;
  }

  Sleeper createSleeper() {
    return new ExponentialBackoffSleeper(1_000, 30_000, 2.0, 0.5);
  }

  /**
   * Creates a flowable by dispatching each builder with idempotency.
   *
   * @return Array of batch processing result
   * @throws InterruptedException
   */
  public Flowable<CreateChargeResult<M>> createFlowable() {
    return getChargesFlow() // Take a sliding window of a configurable number of items every X
                            // seconds
        .window(
            streamOptions.getWindowOptions().getLength(),
            streamOptions.getWindowOptions().getTimeUnit(),
            streamOptions.getWindowOptions().getWindowSize())
        .flatMap(UnivapayFunctions.<Flowable<B>>identity())
        //              Process the following steps in parallel
        .parallel(streamOptions.getParallelism())
        //              Set an idempotency key for each builder where it hasn't been set
        .map(
            new EndoFunction<B>() {
              @Override
              public B apply(B builder) throws Exception {
                if (builder.getIdempotencyKey() == null) {
                  return builder.withIdempotencyKey(IdempotencyKey.randomFromUUID());
                }
                return builder;
              }
            })
        //              Attempt to create the charges
        .map(
            new Function<B, CreateChargeResult<M>>() {
              @Override
              public CreateChargeResult apply(B builder) throws Exception {
                try {
                  final M charge = builder.build().dispatch(createMaxRetry, createSleeper());
                  return new CreateChargeResult<>(charge);
                } catch (IOException | UnivapayException e) {
                  return new CreateChargeResult<>(e);
                }
                //              Poll the status of each created charge
              }
            })
        .map(
            new EndoFunction<CreateChargeResult<M>>() {
              @Override
              public CreateChargeResult<M> apply(CreateChargeResult<M> result) throws Exception {
                if (result.charge != null) {
                  try {
                    final M charge =
                        createChargeCompletionMonitor(
                                retrofit, result.charge.getStoreId(), result.charge.getId())
                            .await(statusCheckTimeout);
                    return new CreateChargeResult<>(charge);
                  } catch (InterruptedException e) {
                    throw e;
                  } catch (Exception e) {
                    result.statusCheckException = e;
                    return result;
                  }
                } else {
                  return result;
                }
              }
            })
        //              Put the results in the different rails into one single sequence
        .sequential();
  }

  /**
   * Create in parallel all the charges in the builders, polling the status of each charge. This
   * method is synchronous, meaning that it blocks the calling thread until all results are
   * finished.
   *
   * @return an array of CreateChargeResult
   * @see CreateChargeResult
   */
  public CreateChargeResult[] execute() {
    return createFlowable().toList().blockingGet().toArray(new CreateChargeResult[0]);
  }

  /**
   * Create in parallel all the charges in the builders, polling the status of each charge. The
   * results get consumed on a separate thread, where the provided callback gets executed.
   *
   * @param callback
   * @see CreateChargeResult
   */
  public void execute(final BatchChargeCallback<M> callback) {
    createFlowable()
        .subscribeOn(Schedulers.newThread())
        .subscribe(
            new Consumer<CreateChargeResult<M>>() {
              @Override
              public void accept(CreateChargeResult<M> createChargeResult) {
                callback.processResult(createChargeResult);
              }
            });
  }
}
