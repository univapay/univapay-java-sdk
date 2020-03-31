package com.univapay.sdk.builders.webhook;

import com.univapay.sdk.builders.webhook.AbstractWebhookBuilders.*;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.request.store.WebhookReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.resources.WebhooksResource;
import java.net.URL;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class WebhookBuilders {

  public static class ListWebhookMerchantRequestBuilder
      extends AbstractListWebhookMerchantRequestBuilder<
          ListWebhookMerchantRequestBuilder, WebhooksResource, Webhook> {

    public ListWebhookMerchantRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<PaginatedList<Webhook>> getRequest(WebhooksResource resource) {
      return resource.list(getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class GetWebhookMerchantRequestBuilder
      extends AbstractGetWebhookMerchantRequestBuilder<
          GetWebhookMerchantRequestBuilder, WebhooksResource, Webhook> {

    public GetWebhookMerchantRequestBuilder(Retrofit retrofit, WebhookId webhookId) {
      super(retrofit, webhookId);
    }

    @Override
    protected Call<Webhook> getRequest(WebhooksResource resource) {
      return resource.get(webhookId);
    }
  }

  public static class CreateWebhookMerchantRequestBuilder
      extends AbstractCreateWebhookMerchantRequestBuilder<
          CreateWebhookMerchantRequestBuilder, WebhooksResource, Webhook> {

    public CreateWebhookMerchantRequestBuilder(Retrofit retrofit, URL url) {
      super(retrofit, url);
    }

    @Override
    protected Call<Webhook> getRequest(WebhooksResource resource) {
      WebhookReq dataToPost = new WebhookReq(url, triggers);
      return resource.create(dataToPost, idempotencyKey);
    }
  }

  public static class UpdateWebhookMerchantRequestBuilder
      extends AbstractUpdateWebhookMerchantRequestBuilder<
          UpdateWebhookMerchantRequestBuilder, WebhooksResource, Webhook> {

    public UpdateWebhookMerchantRequestBuilder(Retrofit retrofit, WebhookId webhookId) {
      super(retrofit, webhookId);
    }

    @Override
    protected Call<Webhook> getRequest(WebhooksResource resource) {
      WebhookReq dataToPost = new WebhookReq(url, triggers);
      return resource.update(webhookId, dataToPost, idempotencyKey);
    }
  }

  public static class DeleteWebhookMerchantRequestBuilder
      extends AbstractDeleteWebhookMerchantRequestBuilder<
          DeleteWebhookMerchantRequestBuilder, WebhooksResource> {

    public DeleteWebhookMerchantRequestBuilder(Retrofit retrofit, WebhookId webhookId) {
      super(retrofit, webhookId);
    }

    @Override
    protected Call<Void> getRequest(WebhooksResource resource) {
      return resource.delete(webhookId);
    }
  }

  public static class ListWebhookRequestBuilder
      extends AbstractListWebhookRequestBuilder<
          ListWebhookRequestBuilder, WebhooksResource, Webhook> {

    public ListWebhookRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<PaginatedList<Webhook>> getRequest(WebhooksResource resource) {
      return resource.list(storeId, getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class DeleteWebhookRequestBuilder
      extends AbstractDeleteWebhookRequestBuilder<DeleteWebhookRequestBuilder, WebhooksResource> {

    public DeleteWebhookRequestBuilder(Retrofit retrofit, StoreId storeId, WebhookId webhookId) {
      super(retrofit, storeId, webhookId);
    }

    @Override
    protected Call<Void> getRequest(WebhooksResource resource) {
      return resource.delete(storeId, webhookId);
    }
  }

  public static class GetWebhookRequestBuilder
      extends AbstractGetWebhookRequestBuilder<
          GetWebhookRequestBuilder, WebhooksResource, Webhook> {

    public GetWebhookRequestBuilder(Retrofit retrofit, StoreId storeId, WebhookId webhookId) {
      super(retrofit, storeId, webhookId);
    }

    @Override
    protected Call<Webhook> getRequest(WebhooksResource resource) {
      return resource.get(storeId, webhookId);
    }
  }

  public static class UpdateWebhookRequestBuilder
      extends AbstractUpdateWebhookRequestBuilder<
          UpdateWebhookRequestBuilder, WebhooksResource, Webhook> {

    public UpdateWebhookRequestBuilder(Retrofit retrofit, StoreId storeId, WebhookId webhookId) {
      super(retrofit, storeId, webhookId);
    }

    @Override
    protected Call<Webhook> getRequest(WebhooksResource resource) {
      WebhookReq dataToPost = new WebhookReq(url, triggers);
      return resource.update(storeId, webhookId, dataToPost, idempotencyKey);
    }
  }

  public static class CreateWebhookRequestBuilder
      extends AbstractCreateWebhookRequestBuilder<
          CreateWebhookRequestBuilder, WebhooksResource, Webhook> {

    public CreateWebhookRequestBuilder(Retrofit retrofit, StoreId storeId, URL url) {
      super(retrofit, storeId, url);
    }

    @Override
    protected Call<Webhook> getRequest(WebhooksResource resource) {
      WebhookReq dataToPost = new WebhookReq(url, triggers);
      return resource.create(storeId, dataToPost, idempotencyKey);
    }
  }
}
