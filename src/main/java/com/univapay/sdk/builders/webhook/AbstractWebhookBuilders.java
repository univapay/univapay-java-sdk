package com.univapay.sdk.builders.webhook;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.PaymentSystemEvent;
import java.net.URL;
import java.util.List;
import retrofit2.Retrofit;

public abstract class AbstractWebhookBuilders {

  public abstract static class AbstractListWebhookMerchantRequestBuilder<
          B extends AbstractListWebhookMerchantRequestBuilder, R, M extends Webhook>
      extends RetrofitRequestBuilderPaginated<M, R, B, WebhookId> {

    public AbstractListWebhookMerchantRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractGetWebhookMerchantRequestBuilder<
          B extends AbstractGetWebhookMerchantRequestBuilder, R, M extends Webhook>
      extends RetrofitRequestBuilder<M, R> {

    protected final WebhookId webhookId;

    protected WebhookId getWebhookId() {
      return webhookId;
    }

    public AbstractGetWebhookMerchantRequestBuilder(Retrofit retrofit, WebhookId webhookId) {
      super(retrofit);
      this.webhookId = webhookId;
    }
  }

  public abstract static class AbstractCreateWebhookMerchantRequestBuilder<
          B extends AbstractCreateWebhookMerchantRequestBuilder, R, M extends Webhook>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected URL url;
    protected List<PaymentSystemEvent> triggers;

    public AbstractCreateWebhookMerchantRequestBuilder(Retrofit retrofit, URL url) {
      super(retrofit);
      this.url = url;
    }

    protected URL getUrl() {
      return url;
    }

    protected List<PaymentSystemEvent> getTriggers() {
      return triggers;
    }

    public B withTriggers(List<PaymentSystemEvent> triggers) {
      this.triggers = triggers;
      return (B) this;
    }
  }

  public abstract static class AbstractUpdateWebhookMerchantRequestBuilder<
          B extends AbstractUpdateWebhookMerchantRequestBuilder, R, M extends Webhook>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected final WebhookId webhookId;
    protected URL url;
    protected List<PaymentSystemEvent> triggers;

    protected WebhookId getWebhookId() {
      return webhookId;
    }

    protected URL getUrl() {
      return url;
    }

    protected List<PaymentSystemEvent> getTriggers() {
      return triggers;
    }

    public AbstractUpdateWebhookMerchantRequestBuilder(Retrofit retrofit, WebhookId webhookId) {
      super(retrofit);
      this.webhookId = webhookId;
    }

    public B withURL(URL url) {
      this.url = url;
      return (B) this;
    }

    public B withTriggers(List<PaymentSystemEvent> triggers) {
      this.triggers = triggers;
      return (B) this;
    }
  }

  public abstract static class AbstractDeleteWebhookMerchantRequestBuilder<
          B extends AbstractDeleteWebhookMerchantRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    protected final WebhookId webhookId;

    protected WebhookId getWebhookId() {
      return webhookId;
    }

    public AbstractDeleteWebhookMerchantRequestBuilder(Retrofit retrofit, WebhookId webhookId) {
      super(retrofit);
      this.webhookId = webhookId;
    }
  }

  public abstract static class AbstractListWebhookRequestBuilder<
          B extends AbstractListWebhookRequestBuilder, R, M extends Webhook>
      extends RetrofitRequestBuilderPaginated<M, R, B, WebhookId> {

    protected StoreId storeId;

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractListWebhookRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }
  }

  public abstract static class AbstractDeleteWebhookRequestBuilder<
          B extends AbstractDeleteWebhookRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    protected final StoreId storeId;
    protected final WebhookId webhookId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected WebhookId getWebhookId() {
      return webhookId;
    }

    public AbstractDeleteWebhookRequestBuilder(
        Retrofit retrofit, StoreId storeId, WebhookId webhookId) {
      super(retrofit);
      this.storeId = storeId;
      this.webhookId = webhookId;
    }
  }

  public abstract static class AbstractGetWebhookRequestBuilder<
          B extends AbstractGetWebhookRequestBuilder, R, M extends Webhook>
      extends RetrofitRequestBuilder<M, R> {

    protected final StoreId storeId;
    protected final WebhookId webhookId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected WebhookId getWebhookId() {
      return webhookId;
    }

    public AbstractGetWebhookRequestBuilder(
        Retrofit retrofit, StoreId storeId, WebhookId webhookId) {
      super(retrofit);
      this.storeId = storeId;
      this.webhookId = webhookId;
    }
  }

  public abstract static class AbstractUpdateWebhookRequestBuilder<
          B extends AbstractUpdateWebhookRequestBuilder, R, M extends Webhook>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected final StoreId storeId;
    protected final WebhookId webhookId;
    protected URL url;
    protected List<PaymentSystemEvent> triggers;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected WebhookId getWebhookId() {
      return webhookId;
    }

    protected URL getUrl() {
      return url;
    }

    protected List<PaymentSystemEvent> getTriggers() {
      return triggers;
    }

    public AbstractUpdateWebhookRequestBuilder(
        Retrofit retrofit, StoreId storeId, WebhookId webhookId) {
      super(retrofit);
      this.storeId = storeId;
      this.webhookId = webhookId;
    }

    public B withURL(URL url) {
      this.url = url;
      return (B) this;
    }

    public B withTriggers(List<PaymentSystemEvent> triggers) {
      this.triggers = triggers;
      return (B) this;
    }
  }

  public abstract static class AbstractCreateWebhookRequestBuilder<
          B extends AbstractCreateWebhookRequestBuilder, R, M extends Webhook>
      extends IdempotentRetrofitRequestBuilder<M, R, AbstractCreateWebhookRequestBuilder> {

    protected StoreId storeId;
    protected URL url;
    protected List<PaymentSystemEvent> triggers;

    public AbstractCreateWebhookRequestBuilder(Retrofit retrofit, StoreId storeId, URL url) {
      super(retrofit);
      this.storeId = storeId;
      this.url = url;
    }

    protected StoreId getStoreId() {
      return storeId;
    }

    protected URL getUrl() {
      return url;
    }

    protected List<PaymentSystemEvent> getTriggers() {
      return triggers;
    }

    public B withTriggers(List<PaymentSystemEvent> triggers) {
      this.triggers = triggers;
      return (B) this;
    }
  }
}
