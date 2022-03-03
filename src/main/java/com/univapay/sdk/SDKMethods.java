package com.univapay.sdk;

import com.univapay.sdk.builders.Paginator;
import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.applicationtoken.AbstractApplicationTokenBuilders;
import com.univapay.sdk.builders.authentication.AbstractAuthenticationBuilders;
import com.univapay.sdk.builders.bankaccount.AbstractBankAccountsBuilders;
import com.univapay.sdk.builders.batch_charge.AbstractBatchCreateCharge;
import com.univapay.sdk.builders.cancel.AbstractCancelsBuilders;
import com.univapay.sdk.builders.charge.AbstractChargesBuilders;
import com.univapay.sdk.builders.exchangerate.AbstractExchangeRateBuilders;
import com.univapay.sdk.builders.issuerToken.AbstractIssuerTokensBuilders;
import com.univapay.sdk.builders.ledgers.AbstractLedgersBuilders;
import com.univapay.sdk.builders.merchant.AbstractMerchantsBuilders;
import com.univapay.sdk.builders.qrcode.AbstractQrCodeMerchantBuilders;
import com.univapay.sdk.builders.refund.AbstractRefundBuilders;
import com.univapay.sdk.builders.store.AbstractStoreBuilders;
import com.univapay.sdk.builders.store.StoreBuilders;
import com.univapay.sdk.builders.subscription.AbstractSubscriptionBuilders;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders;
import com.univapay.sdk.builders.transfer.AbstractTransferBuilders;
import com.univapay.sdk.builders.utility.AbstractUtilityBuilders;
import com.univapay.sdk.builders.webhook.AbstractWebhookBuilders;
import com.univapay.sdk.models.common.AppJWTId;
import com.univapay.sdk.models.common.AppTokenId;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.OnlinePayment;
import com.univapay.sdk.models.common.PaidyPaymentData;
import com.univapay.sdk.models.common.QrMerchantData;
import com.univapay.sdk.models.common.QrScanData;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.models.common.auth.UserCredentials;
import com.univapay.sdk.models.common.bankaccounts.JapaneseBankAccount;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.merchant.MerchantCompanyContactInfo;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

public interface SDKMethods<T extends AbstractSDK> {

  /**
   * Create a new instance of the client with a new authentication strategy, but keeping the same
   * SDK settings. Note that this creates a new thread pool for the new client.
   *
   * @param authStrategy
   * @return an instance of the SDK
   */
  T copy(AuthStrategy authStrategy);

  /**
   * Create a new instance of the client with new settings, but keeping the same authentication
   * strategy. Note that this creates a new thread pool for the new client.
   *
   * @param settings
   * @return an instance of the SDK
   */
  T copy(AbstractSDKSettings settings);

  /**
   * Create a new instance of the client with a new set of credentials and settings. Note that this
   * creates a new thread pool for the new client.
   *
   * @param settings
   * @return an instance of the SDK
   */
  T copy(AuthStrategy authStrategy, AbstractSDKSettings settings);

  /**
   * Create a new instance of the SDK with the same credentials and settings. Note that this creates
   * a new thread pool for the new client.
   *
   * @return an instance of the SDK
   */
  T copy();

  /**
   * Create a new instance of the client with a new authentication strategy, but keeping the same
   * SDK settings. The new client shares the same thread pool as the one of the original client.
   *
   * @param authStrategy
   * @return an instance of the SDK
   */
  T clone(AuthStrategy authStrategy);

  /**
   * Create a new instance of the client with new settings, but keeping the same authentication
   * strategy. The new client shares the same thread pool as the one of the original client.
   *
   * @param settings
   * @return an instance of the SDK
   */
  T clone(AbstractSDKSettings settings);

  /**
   * Create a new instance of the SDK with a new set of credentials and settings. The new client
   * shares the same thread pool with the original client.
   *
   * @param authStrategy
   * @param settings
   * @return an instance of the SDK
   */
  T clone(AuthStrategy authStrategy, AbstractSDKSettings settings);

  /**
   * Create a new instance of the SDK with the same credentials and settings. The new client shares
   * the same thread pool with the original client.
   *
   * @return an instance of the SDK
   */
  T clone();

  /**
   * Obtain a new set of login credentials (login token and JWT) for a merchant account.
   *
   * @param email the merchant's email that is associated with the Univapay account
   * @param password the merchant's password
   * @return a request builder.
   * @see AbstractAuthenticationBuilders.AbstractLoginRequestBuilder
   */
  AbstractAuthenticationBuilders.AbstractLoginRequestBuilder getLoginToken(
      String email, String password);

  /**
   * Obtain a new set of login credentials (login token and JWT) for a merchant account.
   *
   * @param userCredentials
   * @return a request builder.
   * @see AbstractAuthenticationBuilders.AbstractLoginRequestBuilder
   */
  AbstractAuthenticationBuilders.AbstractLoginRequestBuilder getLoginToken(
      UserCredentials userCredentials);

  /**
   * Invalidate the token used for authentication (login token or JWT) currently set as
   * authentication strategy for this SDK instance.
   *
   * @return a request builder.
   * @see AbstractAuthenticationBuilders.AbstractLogoutRequestBuilder
   */
  AbstractAuthenticationBuilders.AbstractLogoutRequestBuilder logout();

  /**
   * Obtain a list of all the stores registered by the merchant
   *
   * @return a request builder that implements <code>Paginator</code>
   * @see StoreBuilders.ListStoresRequestBuilder
   * @see Paginator
   */
  AbstractStoreBuilders.AbstractListStoresRequestBuilder listStores();

  /**
   * Obtained detailed information for a store with ID <code>storeId</code>.
   *
   * @param storeId the store's ID
   * @return a request builder
   * @see StoreBuilders.GetStoreRequestBuilder
   */
  AbstractStoreBuilders.AbstractGetStoreRequestBuilder getStore(StoreId storeId);

  /**
   * Register a new store.
   *
   * @param name the new store's name
   * @return a request builder
   * @see StoreBuilders.CreateStoreRequestBuilder
   */
  AbstractStoreBuilders.AbstractCreateStoreRequestBuilder createStore(String name);

  /**
   * Updates the information of a previously registered store.
   *
   * @param storeId the ID of the store that is going to be updated
   * @return a request builder
   * @see StoreBuilders.UpdateStoreRequestBuilder
   */
  AbstractStoreBuilders.AbstractUpdateStoreRequestBuilder updateStore(StoreId storeId);

  /**
   * Delete a previously register
   *
   * @param storeId the ID of the store that is going to be deleted
   * @return a request builder
   */
  AbstractStoreBuilders.AbstractDeleteStoreRequestBuilder deleteStore(StoreId storeId);

  /**
   * Get the checkout information for the merchant.
   *
   * @param origin a domain included in the list of domains associated with the <code>
   *               application token</code>
   * @return a request builder
   */
  AbstractStoreBuilders.AbstractGetCheckoutInfoRequestBuilder getCheckoutInfo(Domain origin);

  /**
   * Get the checkout information for the merchant.
   *
   * @return a request builder
   */
  AbstractStoreBuilders.AbstractGetCheckoutInfoRequestBuilder getCheckoutInfo();

  /**
   * Get the supported brands for a gateway, required when the Supported Brand at the CheckoutInfo,
   * has the dynamic_info: true
   *
   * @return a request builder
   */
  AbstractStoreBuilders.AbstractGetDynamicBrandInfoRequestBuilder getDynamicBrandInfo(
      Gateway gateway);

  /**
   * List the application tokens available for the store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store for which an application token is being requested
   * @return a request builder
   */
  AbstractApplicationTokenBuilders.AbstractListApplicationTokenRequestBuilder listAppTokens(
      StoreId storeId);

  /**
   * Deletes the application token with ID <code>appTokenId</code> for the store with ID <code>
   * storeId</code>.
   *
   * @param storeId the ID of the store associated with the application token to be deleted
   * @param appTokenId the ID of the application token to be deleted
   * @return a request builder
   */
  AbstractApplicationTokenBuilders.AbstractDeleteApplicationTokenRequestBuilder deleteAppToken(
      StoreId storeId, AppTokenId appTokenId);

  /**
   * Creates a new application token for the store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store for which a new application token will be created
   * @return a request builder that implements <code>CreateApplicationTokenRequest</code>
   */
  @Deprecated
  AbstractApplicationTokenBuilders.AbstractCreateApplicationTokenRequestBuilder createAppToken(
      StoreId storeId);

  /**
   * Creates a new application token for the store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store for which a new application token will be created
   * @param domains a list of domains from where requests can be made with an application token
   * @return a request builder that implements <code>CreateApplicationTokenRequest</code>
   */
  AbstractApplicationTokenBuilders.AbstractCreateApplicationTokenRequestBuilder createAppToken(
      StoreId storeId, List<Domain> domains);

  /**
   * Updates the information for the application token with ID <code>appTokenId</code> and the store
   * with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store associated with the application token that will be updated
   * @param appTokenId the ID of the application token that will be updated
   * @param domains a list of domains from where requests can be made with an application token
   * @return a request builder
   */
  AbstractApplicationTokenBuilders.AbstractUpdateApplicationTokenRequestBuilder updateAppToken(
      StoreId storeId, AppTokenId appTokenId, List<Domain> domains);

  /**
   * Creates a new merchant-level Application Json Web Token
   *
   * @return CreateMerchantApplicationJWTRequestBuilder
   */
  AbstractApplicationTokenBuilders.AbstractCreateMerchantApplicationJWTRequestBuilder
      createMerchantAppJWT();

  /**
   * Lists all merchant-level Application Json Web Tokens
   *
   * @return ListMerchantApplicationJWTRequestBuilder
   */
  AbstractApplicationTokenBuilders.AbstractListMerchantApplicationJWTRequestBuilder
      listMerchantAppJWT();

  /**
   * Deletes a merchant-level Json Web Token
   *
   * @param appJWTId
   * @return DeleteMerchantApplicationJWTRequestBuilder
   */
  AbstractApplicationTokenBuilders.AbstractDeleteMerchantApplicationJWTRequestBuilder deleteAppJWT(
      AppJWTId appJWTId);

  /**
   * Creates a new store-level Application Json Web Token
   *
   * @param storeId
   * @return CreateStoreApplicationJWTRequestBuilder
   */
  AbstractApplicationTokenBuilders.AbstractCreateStoreApplicationJWTRequestBuilder
      createStoreAppJWT(StoreId storeId);

  /**
   * Lists all store-level Application Json Web Tokens
   *
   * @param storeId
   * @return ListStoreApplicationJWTRequestBuilder
   */
  AbstractApplicationTokenBuilders.AbstractListStoreApplicationJWTRequestBuilder listStoreAppJWT(
      StoreId storeId);

  /**
   * Deletes a store-level Json Web Token
   *
   * @param storeId
   * @param appJWTId
   * @return DeleteStoreApplicationJWTRequestBuilder
   */
  AbstractApplicationTokenBuilders.AbstractDeleteStoreApplicationJWTRequestBuilder deleteAppJWT(
      StoreId storeId, AppJWTId appJWTId);

  /**
   * Obtain a list of transfers made to the merchant.
   *
   * @return a request builder that implements <code>Paginator</code>.
   * @see AbstractTransferBuilders.AbstractListTransferRequestBuilder
   * @see Paginator
   */
  AbstractTransferBuilders.AbstractListTransferRequestBuilder listTransfers();

  /**
   * Get detailed information about the transfer with ID <code>transferId</code>
   *
   * @param transferId the ID of the transfer being queried
   * @return a request builder that implements <code>GetTransferRequest</code>
   */
  AbstractTransferBuilders.AbstractGetTransferRequestBuilder getTransfer(TransferId transferId);

  /**
   * Obtain a list of charges made by the merchant.
   *
   * @param storeId (optional) the ID of the store for which charges are being requested
   * @return an instance of <code>ListChargesRequestBuilder</code> implementing <code>Paginator
   * </code>
   * @see Paginator
   */
  AbstractChargesBuilders.AbstractListChargesRequestBuilder listCharges(StoreId storeId);

  /**
   * Obtain a list of charges made by the merchant.
   *
   * @return an instance of <code>ListChargesRequestBuilder</code> implementing <code>Paginator
   * </code>
   * @see Paginator
   */
  AbstractChargesBuilders.AbstractListChargesRequestBuilder listCharges();

  /**
   * Obtain detailed information for a charge with ID <code>chargeId</code> for the store with ID
   * <code>storeId</code>
   *
   * @param storeId the ID of the store associated with the charge to be queried
   * @param chargeId the ID of the charge to be queried
   * @return a request builder
   */
  AbstractChargesBuilders.AbstractGetChargeRequestBuilder getCharge(
      StoreId storeId, ChargeId chargeId);

  /**
   * Create a {@link ResourceMonitor} that awaits the charge complete.
   *
   * <p>{@link ResourceMonitor#await()} will wait until the charge status is other than 'pending'.
   *
   * @param storeId the ID of the store associated with the charge to be queried
   * @param chargeId the ID of the charge to be queried
   * @return a ResourceMonitor
   */
  ResourceMonitor<? extends Charge> chargeCompletionMonitor(StoreId storeId, ChargeId chargeId);

  /**
   * Make a charge. Authorization and capture.
   *
   * @param tokenId the ID of the transaction token to be used for the charge
   * @param amount the amount of the charge
   * @param currency the currency
   * @return a request builder.
   */
  AbstractChargesBuilders.AbstractCreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, BigInteger amount, String currency);

  /**
   * Make a charge. Authorization and capture.
   *
   * @param tokenId
   * @param money the amount and currency of the charge.
   * @return
   */
  AbstractChargesBuilders.AbstractCreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, MoneyLike money);

  /**
   * Make a charge. Authorization and capture.
   *
   * @param tokenId the ID of the transaction token to be used for the charge
   * @param amount the amount of the charge
   * @param currency the currency
   * @param capture if false, authorization only
   * @return a request builder.
   */
  AbstractChargesBuilders.AbstractCreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, BigInteger amount, String currency, Boolean capture);

  /**
   * Make a charge. Authorization and capture.
   *
   * @param tokenId
   * @param money the amount and currency of the charge.
   * @param capture
   * @return
   */
  AbstractChargesBuilders.AbstractCreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, MoneyLike money, Boolean capture);

  /**
   * Make a charge with a token alias instead of a transaction token.
   *
   * @param storeId A store ID
   * @param alias A temporary token alias as TemporaryTokenAlias or String
   * @param money The amount and the currency of the charge. If not use it, the ones of a temporary
   *     token alias are used instead.
   * @param capture If false, authorization only (default = true)
   * @return A request builder
   */
  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(
          StoreId storeId, TokenAliasKey alias, MoneyLike money, Boolean capture);

  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(StoreId storeId, String alias, MoneyLike money, Boolean capture);

  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(StoreId storeId, TokenAliasKey alias, MoneyLike money);

  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(StoreId storeId, String alias, MoneyLike money);

  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(StoreId storeId, TokenAliasKey alias, Boolean capture);

  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(StoreId storeId, String alias, Boolean capture);

  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(StoreId storeId, TokenAliasKey alias);

  AbstractChargesBuilders.AbstractCreateChargeWithTokenAliasRequestBuilder
      createChargeWithTokenAlias(StoreId storeId, String alias);

  /**
   * Make charges by batch processing with default parameter.
   *
   * @return a request builder
   */
  AbstractBatchCreateCharge batchCreateCharge();

  /**
   * Make charges by batch processing.
   *
   * @param createMaxRetry Max retry number if creating a charge failed by temporary error.
   * @param statusCheckTimeout Timeout to wait until charge is processed.
   * @return a request builder
   */
  AbstractBatchCreateCharge batchCreateCharge(int createMaxRetry, int statusCheckTimeout);

  /**
   * Make a charge. Authorization only.
   *
   * @param tokenId the ID of the transaction token to be used for the charge
   * @param amount the amount of the charge
   * @param currency the currency
   * @return a request builder.
   */
  AbstractChargesBuilders.AbstractCreateChargeRequestBuilder authorizeCharge(
      TransactionTokenId tokenId, BigInteger amount, String currency);

  /**
   * Make a charge. Authorization only.
   *
   * @param tokenId
   * @param money the amount and currency of the charge.
   * @return
   */
  AbstractChargesBuilders.AbstractCreateChargeRequestBuilder authorizeCharge(
      TransactionTokenId tokenId, MoneyLike money);

  /**
   * Capture authorized charge.
   *
   * @param storeId the ID of the store associated with the charge to be queried
   * @param chargeId the ID of the charge to be queried
   * @param amount the amount of the charge
   * @param currency the currency
   * @return a request builder.
   */
  AbstractChargesBuilders.AbstractCaptureAuthorizedChargeRequestBuilder captureAuthorizedCharge(
      StoreId storeId, ChargeId chargeId, BigInteger amount, String currency);

  /**
   * Capture authorized charge.
   *
   * @param storeId
   * @param chargeId
   * @param money the amount and currency of the charge.
   * @return
   */
  AbstractChargesBuilders.AbstractCaptureAuthorizedChargeRequestBuilder captureAuthorizedCharge(
      StoreId storeId, ChargeId chargeId, MoneyLike money);

  /**
   * Update the settings of an existing charge with ID <code>chargeId</code> for the store with ID
   * <code>storeId</code>.
   *
   * @param storeId the ID of the store associated with the charge to be queried
   * @param chargeId the ID of the charge to be queried
   * @return a request builder that implements {@link Paginator}
   */
  AbstractChargesBuilders.AbstractUpdateChargeRequestBuilder updateCharge(
      StoreId storeId, ChargeId chargeId);

  /**
   * Obtain a list of all the subscriptions.
   *
   * @return a request builder that implements <code>ListSubscriptionsRequest</code> and <code>
   * Paginator</code>
   * @see Paginator
   */
  AbstractSubscriptionBuilders.AbstractListSubscriptionsMerchantRequestBuilder listSubscriptions();

  /**
   * Obtain a list of all the subscriptions.
   *
   * @param storeId the ID of the store for which subscriptions will be listed
   * @return a request builder that implements <code>ListSubscriptionsRequest</code> and <code>
   * Paginator</code>
   * @see Paginator
   */
  AbstractSubscriptionBuilders.AbstractListSubscriptionsRequestBuilder listSubscriptions(
      StoreId storeId);

  /**
   * Get detailed information about the subscription with ID <code>subscriptionId</code> for the
   * store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store associated with the subscription to be queried
   * @param subscriptionId the ID of the subscription to be queried
   * @return a request builder that implements <code>GetSubscriptionRequest</code>
   */
  AbstractSubscriptionBuilders.AbstractGetSubscriptionRequestBuilder getSubscription(
      StoreId storeId, SubscriptionId subscriptionId);

  /**
   * Request a list of the payments scheduled for a subscription with ID <code>subscriptionId</code>
   *
   * @param storeId
   * @param subscriptionId
   * @return a request builder that implements <code>ListScheduledPaymentsRequest</code>
   */
  AbstractSubscriptionBuilders.AbstractListScheduledPaymentsRequestBuilder listScheduledPayments(
      StoreId storeId, SubscriptionId subscriptionId);

  /**
   * Request the detail for a scheduled payment by its ID
   *
   * @param storeId
   * @param subscriptionId
   * @param paymentId
   * @return a request builder that implements <code>GetScheduledPaymentRequest</code>
   */
  AbstractSubscriptionBuilders.AbstractGetScheduledPaymentRequestBuilder getScheduledPayment(
      StoreId storeId, SubscriptionId subscriptionId, ScheduledPaymentId paymentId);

  /**
   * Update the properties of a scheduled payment such as due date and payment status
   *
   * @param storeId
   * @param subscriptionId
   * @param paymentId
   * @return a request builder that implements <code>UpdateScheduledPaymentRequest</code>
   */
  AbstractSubscriptionBuilders.AbstractUpdateScheduledPaymentRequestBuilder updateScheduledPayment(
      StoreId storeId, SubscriptionId subscriptionId, ScheduledPaymentId paymentId);

  /**
   * Create a {@link ResourceMonitor} that awaits the subscription to complete.
   *
   * <p>{@link ResourceMonitor#await} will wait until the subscription status is other than
   * 'unverified'.
   *
   * @param storeId the ID of the store associated with the subscription to be queried
   * @param subscriptionId the ID of the subscription to be queried
   * @return a ResourceMonitor
   */
  ResourceMonitor<? extends FullSubscription> subscriptionCompletionMonitor(
      StoreId storeId, SubscriptionId subscriptionId);

  /**
   * Create a new subscription.
   *
   * @param transactionTokenId the ID of the transaction token to be used to create the subscription
   * @param amount the amount of the subscription
   * @param amountCurrency the currency in which the subscription's charges will be made
   * @param period the periodicity of the subscription
   * @return a request builder
   */
  AbstractSubscriptionBuilders.AbstractCreateSubscriptionRequestBuilder createSubscription(
      TransactionTokenId transactionTokenId,
      BigInteger amount,
      String amountCurrency,
      SubscriptionPeriod period);

  /**
   * Create a new subscription
   *
   * @param transactionTokenId
   * @param money the amount and currency of the subscription
   * @param period
   * @return a request builder
   */
  AbstractSubscriptionBuilders.AbstractCreateSubscriptionRequestBuilder createSubscription(
      TransactionTokenId transactionTokenId, MoneyLike money, SubscriptionPeriod period);

  /**
   * Update the settings of an existing subscription with ID <code>subscriptionId</code> for the
   * store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store for which subscription charges will be listed
   * @param subscriptionId the ID of the subscription for which charges will be listed
   * @return a request builder that implements {@link Paginator}
   */
  AbstractSubscriptionBuilders.AbstractUpdateSubscriptionRequestBuilder updateSubscription(
      StoreId storeId, SubscriptionId subscriptionId);

  /**
   * Obtain a list of the charges by the store and the subscription.
   *
   * @param storeId the ID of the store in which subscription is.
   * @param subscriptionId the ID of the subscription for which charges will be listed
   * @return a request builder that implements {@link Paginator}
   */
  AbstractSubscriptionBuilders.AbstractListChargesForPaymentRequestBuilder listChargesForPayment(
      StoreId storeId, SubscriptionId subscriptionId, ScheduledPaymentId paymentId);

  /**
   * Obtain a list of the charges by the store and the subscription.
   *
   * @param storeId the ID of the store in which subscription is.
   * @param subscriptionId the ID of the subscription for which charges will be listed
   * @return a request builder that implements {@link Paginator}
   */
  AbstractSubscriptionBuilders.AbstractListSubscriptionChargesRequestBuilder
      listSubscriptionCharges(StoreId storeId, SubscriptionId subscriptionId);

  /**
   * Delete an existing subscription with ID <code>subscriptionId</code> for the store with ID
   * <code>storeId</code>.
   *
   * @param storeId the ID of the store associated with the subscription that will be deleted
   * @param subscriptionId the ID of the subscription to be deleted
   * @return a request builder
   */
  AbstractSubscriptionBuilders.AbstractDeleteSubscriptionRequestBuilder deleteSubscription(
      StoreId storeId, SubscriptionId subscriptionId);

  /**
   * Simulate the payments plan for a subscription.
   *
   * @param money amount and currency of the subscription
   * @param paymentType the type of the payment (credit card, QR Scan, etc.)
   * @param period the Subscription period
   * @return a request builder
   */
  AbstractSubscriptionBuilders.AbstractSimulateInstallmentsPlanRequestBuilder
      simulateSubscriptionPlan(
          MoneyLike money, PaymentTypeName paymentType, SubscriptionPeriod period);

  /**
   * Simulate the payments plan for a subscription.
   *
   * @param money amount and currency of the subscription
   * @param paymentType the type of the payment (credit card, QR Scan, etc.)
   * @param period the Subscription period
   * @return a request builder
   */
  AbstractSubscriptionBuilders.AbstractSimulateInstallmentsPlanRequestBuilder
      simulateSubscriptionPlan(
          StoreId storeId, MoneyLike money, PaymentTypeName paymentType, SubscriptionPeriod period);

  /**
   * Obtain a list of the refunds made by the merchant.
   *
   * @param storeId the ID of the store for which refunds will be listed
   * @param chargeId the ID of the charge for wich refunds will be listed
   * @return a request builder that implements {@link Paginator}
   */
  AbstractRefundBuilders.AbstractListRefundsRequestBuilder listRefunds(
      StoreId storeId, ChargeId chargeId);

  /**
   * Obtain detailed information about a refund with ID <code>refundID</code> associated with the
   * charge with ID <code>chargeID</code> and the store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store associated with the refund to be queried
   * @param chargeId the ID of the charge associated with the refund to be queried
   * @param refundId the ID of the refund to be queried
   * @return a request builder
   */
  AbstractRefundBuilders.AbstractGetRefundRequestBuilder getRefund(
      StoreId storeId, ChargeId chargeId, RefundId refundId);

  /**
   * Make a refund associated with the charge with ID <code>chargeId</code> and the store <code>
   * storeId</code>.
   *
   * @param storeId the ID of the store associated with the charge that will be refunded
   * @param chargeId the ID of the charge to be refunded
   * @param amount the amount to be refunded
   * @param currency the currency in which the refund will be made
   * @param reason the reason why the refund will be made
   * @return a request builder that implements <code>CreateRefundRequest</code>
   */
  AbstractRefundBuilders.AbstractCreateRefundRequestBuilder createRefund(
      StoreId storeId, ChargeId chargeId, BigInteger amount, String currency, RefundReason reason);

  /**
   * Make a refund associated with the charge with ID <code>chargeId</code> and the store <code>
   * storeId</code>.
   *
   * @param storeId the ID of the store associated with the charge that will be refunded
   * @param chargeId the ID of the charge to be refunded
   * @param money the amount and currency of the refund
   * @param reason the reason why the refund will be made
   * @return a request builder that implements <code>CreateRefundRequest</code>
   */
  AbstractRefundBuilders.AbstractCreateRefundRequestBuilder createRefund(
      StoreId storeId, ChargeId chargeId, MoneyLike money, RefundReason reason);

  /**
   * Create a {@link ResourceMonitor} that awaits the refund complete.
   *
   * <p>{@link ResourceMonitor#await} will wait until the refund status is other than 'pending'.
   *
   * @param storeId the ID of the store associated with the refund to be queried
   * @param chargeId the ID of the charge associated with the refund to be queried
   * @param refundId the ID of the refund to be queried
   * @return a ResourceMonitor
   */
  ResourceMonitor<? extends Refund> refundCompletionMonitor(
      StoreId storeId, ChargeId chargeId, RefundId refundId);

  /**
   * List all the cancels associated with a charge
   *
   * @param storeId
   * @param chargeId
   * @return a ListAllCancelsRequestBuilder
   */
  AbstractCancelsBuilders.AbstractListAllCancelsRequestBuilder listCancels(
      StoreId storeId, ChargeId chargeId);

  /**
   * Obtain detailed information about a cancel with ID <code>cancelId</code> associated with the
   * charge <code>chargeId</code> and store <code>storeId</code>.
   *
   * @param storeId
   * @param chargeId
   * @param cancelId
   * @return a request builder
   */
  AbstractCancelsBuilders.AbstractGetCancelRequestBuilder getCancel(
      StoreId storeId, ChargeId chargeId, CancelId cancelId);

  /**
   * Create a {@link ResourceMonitor} that awaits the cancel to complete.
   *
   * <p>{@link ResourceMonitor#await} will wait until the cancel status is other than 'pending'.
   *
   * @param storeId the ID of the store associated with the cancel to be queried
   * @param chargeId the ID of the charge
   * @param cancelId the ID of the cancel to be queried
   * @return a ResourceMonitor
   */
  ResourceMonitor<? extends Cancel> cancelCompletionMonitor(
      StoreId storeId, ChargeId chargeId, CancelId cancelId);

  /**
   * Create a cancel for charge <code>chargeId</code> and store <code>storeId</code>
   *
   * @param storeId
   * @param chargeId
   * @return a request builder
   */
  AbstractCancelsBuilders.AbstractCreateCancelRequestBuilder createCancel(
      StoreId storeId, ChargeId chargeId);

  /**
   * Update an existing cancel with Id <code>cancelId</code> associated with charge <code>chargeId
   * </code> and store <code>storeId</code>
   *
   * @param storeId
   * @param chargeId
   * @param cancelId
   * @return a request builder
   */
  AbstractCancelsBuilders.AbstractUpdateCancelRequestBuilder updateCancel(
      StoreId storeId, ChargeId chargeId, CancelId cancelId);

  /**
   * Obtain a list of the bank accounts registered by the merchant.
   *
   * @return a request builder that implements {@link Paginator}
   */
  AbstractBankAccountsBuilders.AbstractListAllBankAccountsRequestBuilder listBankAccounts();

  /**
   * Get detailed information about a bank account with ID <code>bankAccountId</code>
   *
   * @param bankAccountId the ID of the bank account to be queried
   * @return a request builder
   */
  AbstractBankAccountsBuilders.AbstractGetBankAccountRequestBuilder getBankAccount(
      BankAccountId bankAccountId);

  /**
   * Register a new bank account.
   *
   * @param bankAccount a bank account object. Currently only Japanese Bank Accounts are supported.
   * @return a request builder
   * @see JapaneseBankAccount
   */
  AbstractBankAccountsBuilders.AbstractCreateBankAccountRequestBuilder createBankAccount(
      JapaneseBankAccount bankAccount);

  /**
   * Update the information associated with the bank account with ID <code>bankAccountId</code>
   *
   * @param bankAccountId the ID of the bank account to be updated
   * @return a request builder
   */
  AbstractBankAccountsBuilders.AbstractUpdateBankAccountRequestBuilder updateBankAccount(
      BankAccountId bankAccountId);

  /**
   * Delete the bank account with ID <code>bankAccountId</code>
   *
   * @param bankAccountId the ID of the bank account to be deleted
   * @return a request builder
   */
  AbstractBankAccountsBuilders.AbstractDeleteBankAccountRequestBuilder deleteBankAccount(
      BankAccountId bankAccountId);

  /**
   * Obtain detailed information on the primary bank account of the merchant.
   *
   * @return a request builder
   */
  AbstractBankAccountsBuilders.AbstractGetPrimaryBankAccountRequestBuilder getPrimaryBankAccount();

  /**
   * Obtain the information submitted by the merchant for verification.
   *
   * @return a request builder
   */
  AbstractMerchantsBuilders.AbstractGetMerchantVerificationRequestBuilder getMerchantVerification();

  /**
   * Update the information submitted by the merchant for verification.
   *
   * @return a request builder
   */
  AbstractMerchantsBuilders.AbstractUpdateMerchantVerificationRequestBuilder
      updateMerchantVerification();

  /**
   * Submits the merchant's information requesting for verification.
   *
   * @param homepageUrl the url of the merchant's homepage
   * @param companyDescription a description of the merchant's company
   * @param companyContactInfo an instance of <code>MerchantCompanyContactInfo</code>
   * @param businessType the business type of the merchant's company
   * @param systemManagerName the system manager's name
   * @return a request builder
   */
  AbstractMerchantsBuilders.AbstractCreateMerchantVerificationRequestBuilder
      createMerchantVerification(
          URL homepageUrl,
          String companyDescription,
          MerchantCompanyContactInfo companyContactInfo,
          BusinessType businessType,
          String systemManagerName);

  /**
   * Gets the merchant's information
   *
   * @return a request builder
   */
  AbstractMerchantsBuilders.AbstractGetMeRequestBuilder getMe();

  /**
   * Obtain a list of transactions of the store.
   *
   * @param storeId (optional) the ID of the store for which transactions will be listed
   * @return a request builder that implements {@link Paginator}
   */
  AbstractMerchantsBuilders.AbstractGetTransactionHistoryRequestBuilder getTransactionHistory(
      StoreId storeId);

  /**
   * Obtain a list of all transactions of the merchant.
   *
   * @return a request builder that implements {@link Paginator}
   */
  AbstractMerchantsBuilders.AbstractGetTransactionHistoryRequestBuilder getTransactionHistory();

  /**
   * Obtain a list of the webhooks.
   *
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractListWebhookMerchantRequestBuilder listWebhooks();

  /**
   * Obtain detailed information on a webhook with ID <code>webhookId</code>.
   *
   * @param webhookId the ID of the webhook to be queried
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractGetWebhookMerchantRequestBuilder getWebhook(WebhookId webhookId);

  /**
   * Create a new webhook.
   *
   * @param url the webhook's URL
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractCreateWebhookMerchantRequestBuilder createWebhook(URL url);

  /**
   * Update a webhook with ID <code>webhookId</code>.
   *
   * @param webhookId the ID of the webhook that will be updated
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractUpdateWebhookMerchantRequestBuilder updateWebhook(
      WebhookId webhookId);

  /**
   * Delete the webhook with ID <code>webhookId</code>.
   *
   * @param webhookId the ID of the webhook to be deleted
   * @return a request builder that implements Request of type <code>Void</code>
   */
  AbstractWebhookBuilders.AbstractDeleteWebhookMerchantRequestBuilder deleteWebhook(
      WebhookId webhookId);

  /**
   * Obtain a list of the webhooks associated with a store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store for which webhooks will be listed
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractListWebhookRequestBuilder listWebhooks(StoreId storeId);

  /**
   * Obtain detailed information on a webhook with ID <code>webhookId</code> associated with the
   * store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store associated with the webhook that will be queried
   * @param webhookId the ID of the webhook to be queried
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractGetWebhookRequestBuilder getWebhook(
      StoreId storeId, WebhookId webhookId);

  /**
   * Create a new webhook associated with a store with ID <code>storeId</code>.
   *
   * @param storeId the ID of the store for which a new webhook will be created
   * @param url the webhook's URL
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractCreateWebhookRequestBuilder createWebhook(
      StoreId storeId, URL url);

  /**
   * Update a webhook with ID <code>webhookId</code> associated with a store with ID <code>storeId
   * </code>.
   *
   * @param storeId the ID of the store associated with the webhook that will be updated
   * @param webhookId the ID of the webhook that will be updated
   * @return a request builder
   */
  AbstractWebhookBuilders.AbstractUpdateWebhookRequestBuilder updateWebhook(
      StoreId storeId, WebhookId webhookId);

  /**
   * Delete the webhook with ID <code>webhookId</code>.
   *
   * @param storeId the ID of the store associated with the webhook to be deleted
   * @param webhookId the ID of the webhook to be deleted
   * @return a request builder that implements Request of type <code>Void</code>
   */
  AbstractWebhookBuilders.AbstractDeleteWebhookRequestBuilder deleteWebhook(
      StoreId storeId, WebhookId webhookId);

  /**
   * Create a new transaction token.
   *
   * @param email the email address of the person making the payment
   * @param paymentData instance of CreditCard, QRScanData, KonbiniPayment, ApplePay or
   *     PaidyPaymentData
   * @param type the type of the transaction token to be created
   * @return a request builder.
   */
  AbstractTransactionTokensBuilders.AbstractCreateTransactionTokenRequestBuilder
      createTransactionToken(String email, PaymentData paymentData, TransactionTokenType type);

  /**
   * Create a new transaction token.
   *
   * @param paymentData instance of PaidyPaymentData
   * @param type the type of the transaction token to be created
   * @return a request builder.
   */
  AbstractTransactionTokensBuilders.AbstractCreateTransactionTokenRequestBuilder
      createTransactionToken(PaidyPaymentData paymentData, TransactionTokenType type);

  /**
   * Create a new transaction token.
   *
   * @param paymentData instance of QrScanPaymentData
   * @param type the type of the transaction token to be created
   * @return a request builder.
   */
  AbstractTransactionTokensBuilders.AbstractCreateTransactionTokenRequestBuilder
      createTransactionToken(QrScanData paymentData, TransactionTokenType type);

  /**
   * Create a new transaction token.
   *
   * @param paymentData instance of QrScanPaymentData
   * @param type the type of the transaction token to be created
   * @return a request builder.
   */
  AbstractTransactionTokensBuilders.AbstractCreateTransactionTokenRequestBuilder
      createTransactionToken(QrMerchantData paymentData, TransactionTokenType type);

  /**
   * Create a new transaction token.
   *
   * @param paymentData instance of OnlinePayment
   * @param type the type of the transaction token to be created
   * @return a request builder.
   */
  AbstractTransactionTokensBuilders.AbstractCreateTransactionTokenRequestBuilder
      createTransactionToken(OnlinePayment paymentData, TransactionTokenType type);

  /**
   * Delete a transaction token.
   *
   * @param storeId the ID of the store associated with the transaction token to be deleted
   * @param tokenId the ID of the transaction token to be deleted
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractDeleteTransactionTokenRequestBuilder
      deleteTransactionToken(StoreId storeId, TransactionTokenId tokenId);

  /**
   * Get an existing transaction token.
   *
   * @param storeId the ID of the store associated with the transaction token being queried
   * @param tokenId the ID of the transaction token to be queried
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractGetTransactionTokenRequestBuilder getTransactionToken(
      StoreId storeId, TransactionTokenId tokenId);

  /**
   * Update a recurring transaction token.
   *
   * @param storeId the ID of the store associated with the transaction token to be updated
   * @param tokenId the ID of the transaction token to be updated
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractUpdateTransactionTokenRequestBuilder
      updateTransactionToken(StoreId storeId, TransactionTokenId tokenId);

  /**
   * List recurring transaction tokens.
   *
   * @param storeId the ID of the store
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractListTransactionTokensRequestBuilder
      listTransactionTokens(StoreId storeId);

  /**
   * List recurring transaction tokens.
   *
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractListTransactionTokensMerchantRequestBuilder
      listTransactionTokens();

  /**
   * Create an alias for an existing transaction token
   *
   * @param transactionTokenId
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractCreateTemporaryTokenAliasRequestBuilder
      createTokenAlias(TransactionTokenId transactionTokenId);

  /**
   * Get an existing temporary transaction token alias
   *
   * @param storeId the ID of the store
   * @param aliasKey the ID of the temporary alias
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractGetTemporaryTokenAliasRequestBuilder getTokenAlias(
      StoreId storeId, TokenAliasKey aliasKey);

  /**
   * Get the token alias image as binary data
   *
   * @param storeId the ID of the store
   * @param aliasKey the ID of the temporary alias
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractGetTemporaryTokenAliasAsImageRequestBuilder
      getTokenAliasImage(StoreId storeId, TokenAliasKey aliasKey);

  /**
   * Get the Issuer Token of a charge
   *
   * @param storeId the ID of the store
   * @param chargeId the Id of the charge
   * @return a request builder
   */
  AbstractIssuerTokensBuilders.AbstractGetIssuerTokenRequestBuilder getIssuerToken(
      StoreId storeId, ChargeId chargeId);

  /**
   * Get the QrCode of a charge, only for MPM Payments
   *
   * @param storeId the ID of the store
   * @param chargeId the Id of the charge
   * @return a request builder
   */
  AbstractQrCodeMerchantBuilders.AbstractGetQrCodeRequestBuilder getQrCode(
      StoreId storeId, ChargeId chargeId);

  /**
   * Delete a temporary transaction token alias
   *
   * @param storeId the ID of the store
   * @param aliasKey the ID of the temporary alias to be deleted
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractDeleteTemporaryTokenAliasRequestBuilder
      deleteTokenAlias(StoreId storeId, TokenAliasKey aliasKey);

  /**
   * Confirm a transaction token
   *
   * @param storeId the ID of the store
   * @param tokenId the ID of the transaction token
   * @param confirmationCode the code to confirm the transaction token
   * @return a request builder
   */
  AbstractTransactionTokensBuilders.AbstractConfirmTransactionTokenRequestBuilder
      confirmTransactionToken(StoreId storeId, TransactionTokenId tokenId, String confirmationCode);

  /**
   * List ledgers associated with a transfer.
   *
   * @param transferId the ID of the transfer for which ledgers will be listed
   * @return a request builder
   */
  AbstractLedgersBuilders.AbstractListLedgersRequestBuilder listLedgers(TransferId transferId);

  /**
   * Associate a merchant-defined customer ID with a Univapay customer ID.
   *
   * @param customerId
   * @return a request builder
   */
  AbstractStoreBuilders.AbstractCreateCustomerIdRequestBuilder createCustomerId(
      StoreId storeId, String customerId);

  /**
   * Obtains an estimate of an amount converted to a target currency.
   *
   * @param moneyToConvert
   * @param targetCurrency
   * @return a request builder
   */
  AbstractExchangeRateBuilders.AbstractConvertMoneyBuilder convertMoney(
      MoneyLike moneyToConvert, String targetCurrency);

  /**
   * Send a heartbeat route.
   *
   * @return a request builder
   */
  AbstractUtilityBuilders.AbstractHeartbeatRequestBuilder beat();
}
