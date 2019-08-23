package com.univapay.sdk;

import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.settings.UnivapaySettings;
import java.io.Closeable;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.applicationtoken.ApplicationTokenBuilders;
import com.univapay.sdk.builders.authentication.AuthenticationBuilders;
import com.univapay.sdk.builders.bankaccount.BankAccountsBuilders;
import com.univapay.sdk.builders.batch_charge.BatchCreateCharge;
import com.univapay.sdk.builders.cancel.CancelsBuilders;
import com.univapay.sdk.builders.charge.ChargesBuilders;
import com.univapay.sdk.builders.exchangerate.ExchangeRateBuilders;
import com.univapay.sdk.builders.ledgers.LedgersBuilders;
import com.univapay.sdk.builders.merchant.MerchantsBuilders;
import com.univapay.sdk.builders.refund.RefundBuilders;
import com.univapay.sdk.builders.store.StoreBuilders;
import com.univapay.sdk.builders.subscription.SubscriptionBuilders;
import com.univapay.sdk.builders.transactiontoken.TransactionTokensBuilders;
import com.univapay.sdk.builders.transfer.TransferBuilders;
import com.univapay.sdk.builders.utility.UtilityBuilders;
import com.univapay.sdk.builders.webhook.WebhookBuilders;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.AppJWTId;
import com.univapay.sdk.models.common.AppTokenId;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.MoneyLike;
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
import com.univapay.sdk.models.common.auth.*;
import com.univapay.sdk.models.common.auth.AppJWTStrategy;
import com.univapay.sdk.models.common.auth.AppTokenStrategy;
import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.models.common.auth.LoginTokenStrategy;
import com.univapay.sdk.models.common.auth.UnauthenticatedStrategy;
import com.univapay.sdk.models.common.auth.UserCredentials;
import com.univapay.sdk.models.common.bankaccounts.JapaneseBankAccount;
import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.merchant.MerchantCompanyContactInfo;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.BusinessType;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.RefundReason;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.TransactionTokenType;
import okhttp3.Call.Factory;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Main class of the Univapay SDK. Provides methods for setting up credentials and communicating
 * with the API.
 */
public class UnivapaySDK extends AbstractSDK implements SDKMethods<UnivapaySDK>, Closeable {

  private UnivapaySDK(AuthStrategy authStrategy, AbstractSDKSettings settings) {
    super(authStrategy, settings);
  }

  private UnivapaySDK(
      AuthStrategy authStrategy, AbstractSDKSettings settings, ConnectionPool connectionPool) {
    super(authStrategy, settings, connectionPool);
  }

  @Override
  public UnivapaySDK copy(AuthStrategy authStrategy) {
    return new UnivapaySDK(authStrategy, this.settings.copy());
  }

  @Override
  public UnivapaySDK copy(AbstractSDKSettings settings) {
    return new UnivapaySDK(this.authStrategy, settings);
  }

  @Override
  public UnivapaySDK copy(AuthStrategy authStrategy, AbstractSDKSettings settings) {
    return new UnivapaySDK(authStrategy, settings);
  }

  @Override
  public UnivapaySDK copy() {
    return new UnivapaySDK(this.authStrategy, this.settings.copy());
  }

  @Override
  public UnivapaySDK clone(AuthStrategy authStrategy) {
    OkHttpClient client = (OkHttpClient) retrofit.callFactory();
    return new UnivapaySDK(authStrategy, this.settings.copy(), client.connectionPool());
  }

  @Override
  public UnivapaySDK clone(AbstractSDKSettings settings) {
    OkHttpClient client = (OkHttpClient) retrofit.callFactory();
    return new UnivapaySDK(this.authStrategy, settings, client.connectionPool());
  }

  @Override
  public UnivapaySDK clone(AuthStrategy authStrategy, AbstractSDKSettings settings) {
    OkHttpClient client = (OkHttpClient) retrofit.callFactory();
    return new UnivapaySDK(authStrategy, settings, client.connectionPool());
  }

  @Override
  public UnivapaySDK clone() {
    OkHttpClient client = (OkHttpClient) retrofit.callFactory();
    return new UnivapaySDK(this.authStrategy, this.settings, client.connectionPool());
  }

  /**
   * Create a UnivapaySDK instance using an authentication strategy.
   *
   * @param authStrategy The merchant's credentials.
   * @param settings The SDK settings.
   * @see AbstractSDKSettings
   * @see AuthStrategy
   * @see LoginTokenStrategy
   * @see AppTokenStrategy
   * @see LoginJWTStrategy
   * @see AppJWTStrategy
   * @see UnauthenticatedStrategy
   */
  public static UnivapaySDK create(AuthStrategy authStrategy, AbstractSDKSettings settings) {
    return new UnivapaySDK(authStrategy, settings);
  }

  /**
   * Create an unauthenticated UnivapaySDK instance.
   *
   * @param settings The SDK settings.
   * @see AbstractSDKSettings
   */
  public static UnivapaySDK create(AbstractSDKSettings settings) {
    return create(new UnauthenticatedStrategy(), settings);
  }

  /**
   * Create an unauthenticated UnivapaySDK instance with default production settings.
   *
   * @see AbstractSDKSettings
   */
  public static UnivapaySDK create() {
    return create(new UnauthenticatedStrategy(), new UnivapaySettings());
  }

  /**
   * Create a UnivapaySDK instance with default settings using an authentication strategy.
   *
   * @param authStrategy The merchant's credentials.
   * @see AbstractSDKSettings
   * @see AuthStrategy
   * @see LoginTokenStrategy
   * @see AppTokenStrategy
   * @see LoginJWTStrategy
   * @see AppJWTStrategy
   * @see UnauthenticatedStrategy
   * @see AbstractSDKSettings
   */
  public static UnivapaySDK create(AuthStrategy authStrategy) {
    return create(authStrategy, new UnivapaySettings());
  }

  /**
   * Asynchronously creates a UnivapaySDK instance requesting a JWT for authentication.
   *
   * @param userCredentials The merchant's email and password
   * @throws TooManyRequestsException
   * @throws IOException
   * @throws UnivapayException
   * @see UserCredentials
   */
  public static UnivapaySDK create(UserCredentials userCredentials, AbstractSDKSettings settings)
      throws IOException, UnivapayException {
    LoginJWTStrategy jwtCredentials =
        UnivapaySDK.create(settings)
            .getLoginToken(userCredentials)
            .build()
            .dispatch()
            .getJWTAuthStrategy();
    return create(jwtCredentials, settings);
  }

  /*
   * <h1>UnivapayMethods</h1>
   * <p>These are the methods used to communicate with the Payments API</p>
   * <p>Each method returns a <code>Request</code>, which is only executed when the method</p>
   * <p><code>dispatch()</code> is called.</p>
   */

  /*
   * <h2>AUTHENTICATION</h2>
   * <p>Methods for obtaining a set of credentials or to submit registration information for a new merchant</p>
   */

  @Override
  public AuthenticationBuilders.LoginRequestBuilder getLoginToken(String email, String password) {
    return new AuthenticationBuilders.LoginRequestBuilder(
        retrofit, new EmailAddress(email), password);
  }

  @Override
  public AuthenticationBuilders.LoginRequestBuilder getLoginToken(UserCredentials userCredentials) {
    return getLoginToken(userCredentials.getEmail(), userCredentials.getPassword());
  }

  @Deprecated
  /**
   * Delete a login token.
   *
   * @return a request builder.
   * @see LogoutRequestBuilder
   */
  public AuthenticationBuilders.LogoutRequestBuilder deleteLoginToken() {
    return new AuthenticationBuilders.LogoutRequestBuilder(retrofit);
  }

  @Override
  public AuthenticationBuilders.LogoutRequestBuilder logout() {
    return new AuthenticationBuilders.LogoutRequestBuilder(retrofit);
  }

  /*
   * <h2>STORES</h2>
   * Methods for stores management tasks.
   */

  @Override
  public StoreBuilders.ListStoresRequestBuilder listStores() {
    return new StoreBuilders.ListStoresRequestBuilder(retrofit);
  }

  @Override
  public StoreBuilders.GetStoreRequestBuilder getStore(StoreId storeId) {
    return new StoreBuilders.GetStoreRequestBuilder(retrofit, storeId);
  }

  @Override
  public StoreBuilders.CreateStoreRequestBuilder createStore(String name) {
    return new StoreBuilders.CreateStoreRequestBuilder(retrofit, name);
  }

  @Override
  public StoreBuilders.UpdateStoreRequestBuilder updateStore(StoreId storeId) {
    return new StoreBuilders.UpdateStoreRequestBuilder(retrofit, storeId);
  }

  @Override
  public StoreBuilders.DeleteStoreRequestBuilder deleteStore(StoreId storeId) {
    return new StoreBuilders.DeleteStoreRequestBuilder(retrofit, storeId);
  }

  @Override
  public StoreBuilders.GetCheckoutInfoRequestBuilder getCheckoutInfo(Domain origin) {
    return new StoreBuilders.GetCheckoutInfoRequestBuilder(retrofit, origin);
  }

  @Override
  public StoreBuilders.GetCheckoutInfoRequestBuilder getCheckoutInfo() {
    return new StoreBuilders.GetCheckoutInfoRequestBuilder(retrofit);
  }

  @Override
  public StoreBuilders.CreateCustomerIdRequestBuilder createCustomerId(
      StoreId storeId, String customerId) {
    return new StoreBuilders.CreateCustomerIdRequestBuilder(retrofit, storeId, customerId);
  }

  /*
   * <h2>APPLICATION TOKENS</h2>
   * <p>Methods used for the management of application tokens</p>
   */

  @Override
  public ApplicationTokenBuilders.ListApplicationTokenRequestBuilder listAppTokens(
      StoreId storeId) {
    return new ApplicationTokenBuilders.ListApplicationTokenRequestBuilder(retrofit, storeId);
  }

  @Override
  public ApplicationTokenBuilders.DeleteApplicationTokenRequestBuilder deleteAppToken(
      StoreId storeId, AppTokenId appTokenId) {
    return new ApplicationTokenBuilders.DeleteApplicationTokenRequestBuilder(
        retrofit, storeId, appTokenId);
  }

  @Deprecated
  @Override
  public ApplicationTokenBuilders.CreateApplicationTokenRequestBuilder createAppToken(
      StoreId storeId) {
    return new ApplicationTokenBuilders.CreateApplicationTokenRequestBuilder(retrofit, storeId);
  }

  @Override
  public ApplicationTokenBuilders.CreateApplicationTokenRequestBuilder createAppToken(
      StoreId storeId, List<Domain> domains) {
    return new ApplicationTokenBuilders.CreateApplicationTokenRequestBuilder(
        retrofit, storeId, domains);
  }

  @Override
  public ApplicationTokenBuilders.UpdateApplicationTokenRequestBuilder updateAppToken(
      StoreId storeId, AppTokenId appTokenId, List<Domain> domains) {
    return new ApplicationTokenBuilders.UpdateApplicationTokenRequestBuilder(
        retrofit, storeId, appTokenId, domains);
  }

  @Override
  public ApplicationTokenBuilders.CreateMerchantApplicationJWTRequestBuilder
      createMerchantAppJWT() {
    return new ApplicationTokenBuilders.CreateMerchantApplicationJWTRequestBuilder(retrofit);
  }

  @Override
  public ApplicationTokenBuilders.ListMerchantApplicationJWTRequestBuilder listMerchantAppJWT() {
    return new ApplicationTokenBuilders.ListMerchantApplicationJWTRequestBuilder(retrofit);
  }

  @Override
  public ApplicationTokenBuilders.DeleteMerchantApplicationJWTRequestBuilder deleteAppJWT(
      AppJWTId appJWTId) {
    return new ApplicationTokenBuilders.DeleteMerchantApplicationJWTRequestBuilder(
        retrofit, appJWTId);
  }

  @Override
  public ApplicationTokenBuilders.CreateStoreApplicationJWTRequestBuilder createStoreAppJWT(
      StoreId storeId) {
    return new ApplicationTokenBuilders.CreateStoreApplicationJWTRequestBuilder(retrofit, storeId);
  }

  @Override
  public ApplicationTokenBuilders.ListStoreApplicationJWTRequestBuilder listStoreAppJWT(
      StoreId storeId) {
    return new ApplicationTokenBuilders.ListStoreApplicationJWTRequestBuilder(retrofit, storeId);
  }

  @Override
  public ApplicationTokenBuilders.DeleteStoreApplicationJWTRequestBuilder deleteAppJWT(
      StoreId storeId, AppJWTId appJWTId) {
    return new ApplicationTokenBuilders.DeleteStoreApplicationJWTRequestBuilder(
        retrofit, storeId, appJWTId);
  }

  /*
   * <h2>TRANSFERS</h2>
   * <p> Obtain information about the transfers to the merchant</p>
   */

  @Override
  public TransferBuilders.ListTransferRequestBuilder listTransfers() {
    return new TransferBuilders.ListTransferRequestBuilder(retrofit);
  }

  @Override
  public TransferBuilders.GetTransferRequestBuilder getTransfer(TransferId transferId) {
    return new TransferBuilders.GetTransferRequestBuilder(retrofit, transferId);
  }

  /*
   * <h2>CHARGES</h2>
   * <p>Methods used for making charges and obtaining detailed information about previous ones.</p>
   */

  @Override
  public ChargesBuilders.ListChargesRequestBuilder listCharges(StoreId storeId) {
    return new ChargesBuilders.ListChargesRequestBuilder(retrofit, storeId);
  }

  @Override
  public ChargesBuilders.ListChargesRequestBuilder listCharges() {
    return new ChargesBuilders.ListChargesRequestBuilder(retrofit);
  }

  @Override
  public ChargesBuilders.GetChargeRequestBuilder getCharge(StoreId storeId, ChargeId chargeId) {
    return new ChargesBuilders.GetChargeRequestBuilder(retrofit, storeId, chargeId);
  }

  @Override
  public ResourceMonitor<Charge> chargeCompletionMonitor(StoreId storeId, ChargeId chargeId) {
    return ChargesBuilders.createChargeCompletionMonitor(retrofit, storeId, chargeId);
  }

  @Override
  public ChargesBuilders.CreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, BigInteger amount, String currency) {
    return createCharge(tokenId, amount, currency, true);
  }

  @Override
  public ChargesBuilders.CreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, BigInteger amount, String currency, Boolean capture) {
    return new ChargesBuilders.CreateChargeRequestBuilder(
        retrofit, tokenId, new MoneyLike(amount, currency), capture);
  }

  @Override
  public ChargesBuilders.CreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, MoneyLike money) {
    return createCharge(tokenId, money.getAmount(), money.getCurrency());
  }

  @Override
  public ChargesBuilders.CreateChargeRequestBuilder createCharge(
      TransactionTokenId tokenId, MoneyLike money, Boolean capture) {
    return createCharge(tokenId, money.getAmount(), money.getCurrency(), capture);
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, TokenAliasKey alias, MoneyLike money, Boolean capture) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, money, capture, this, alias);
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, String alias, MoneyLike money, Boolean capture) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, money, capture, this, TokenAliasKey.parse(alias));
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, TokenAliasKey alias, MoneyLike money) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, money, true, this, alias);
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, String alias, MoneyLike money) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, money, true, this, TokenAliasKey.parse(alias));
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, TokenAliasKey alias, Boolean capture) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, null, capture, this, alias);
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, String alias, Boolean capture) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, null, capture, this, TokenAliasKey.parse(alias));
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, TokenAliasKey alias) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, null, true, this, alias);
  }

  @Override
  public ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder createChargeWithTokenAlias(
      StoreId storeId, String alias) {
    return new ChargesBuilders.CreateChargeWithTokenAliasRequestBuilder(
        storeId, null, true, this, TokenAliasKey.parse(alias));
  }

  @Override
  public BatchCreateCharge batchCreateCharge() {
    return batchCreateCharge(10, 15_000);
  }

  @Override
  public BatchCreateCharge batchCreateCharge(int createMaxRetry, int statusCheckTimeout) {
    return new BatchCreateCharge(retrofit, createMaxRetry, statusCheckTimeout);
  }

  @Override
  public ChargesBuilders.CreateChargeRequestBuilder authorizeCharge(
      TransactionTokenId tokenId, BigInteger amount, String currency) {
    return createCharge(tokenId, amount, currency, false);
  }

  @Override
  public ChargesBuilders.CreateChargeRequestBuilder authorizeCharge(
      TransactionTokenId tokenId, MoneyLike money) {
    return authorizeCharge(tokenId, money.getAmount(), money.getCurrency());
  }

  @Override
  public ChargesBuilders.CaptureAuthorizedChargeRequestBuilder captureAuthorizedCharge(
      StoreId storeId, ChargeId chargeId, BigInteger amount, String currency) {
    return new ChargesBuilders.CaptureAuthorizedChargeRequestBuilder(
        retrofit, storeId, chargeId, new MoneyLike(amount, currency));
  }

  @Override
  public ChargesBuilders.CaptureAuthorizedChargeRequestBuilder captureAuthorizedCharge(
      StoreId storeId, ChargeId chargeId, MoneyLike money) {
    return captureAuthorizedCharge(storeId, chargeId, money.getAmount(), money.getCurrency());
  }

  @Override
  public ChargesBuilders.UpdateChargeRequestBuilder updateCharge(
      StoreId storeId, ChargeId chargeId) {
    return new ChargesBuilders.UpdateChargeRequestBuilder(retrofit, storeId, chargeId);
  }

  /*
   * <h2>SUBSCRIPTIONS</h2>
   * <p>Methods for the management of subscriptions</p>
   */

  @Override
  public SubscriptionBuilders.ListSubscriptionsMerchantRequestBuilder listSubscriptions() {
    return new SubscriptionBuilders.ListSubscriptionsMerchantRequestBuilder(retrofit);
  }

  @Override
  public SubscriptionBuilders.ListSubscriptionsRequestBuilder listSubscriptions(StoreId storeId) {
    return new SubscriptionBuilders.ListSubscriptionsRequestBuilder(retrofit, storeId);
  }

  @Override
  public SubscriptionBuilders.GetSubscriptionRequestBuilder getSubscription(
      StoreId storeId, SubscriptionId subscriptionId) {
    return new SubscriptionBuilders.GetSubscriptionRequestBuilder(
        retrofit, storeId, subscriptionId);
  }

  @Override
  public ResourceMonitor<FullSubscription> subscriptionCompletionMonitor(
      StoreId storeId, SubscriptionId subscriptionId) {
    return SubscriptionBuilders.createSubscriptionCompletionMonitor(
        retrofit, storeId, subscriptionId);
  }

  @Override
  public SubscriptionBuilders.CreateSubscriptionRequestBuilder createSubscription(
      TransactionTokenId transactionTokenId,
      BigInteger amount,
      String amountCurrency,
      SubscriptionPeriod period) {
    return createSubscription(transactionTokenId, new MoneyLike(amount, amountCurrency), period);
  }

  @Override
  public SubscriptionBuilders.CreateSubscriptionRequestBuilder createSubscription(
      TransactionTokenId transactionTokenId, MoneyLike money, SubscriptionPeriod period) {
    return new SubscriptionBuilders.CreateSubscriptionRequestBuilder(
        retrofit, transactionTokenId, money, period);
  }

  @Override
  public SubscriptionBuilders.UpdateSubscriptionRequestBuilder updateSubscription(
      StoreId storeId, SubscriptionId subscriptionId) {
    return new SubscriptionBuilders.UpdateSubscriptionRequestBuilder(
        retrofit, storeId, subscriptionId);
  }

  @Override
  public SubscriptionBuilders.ListChargesForPaymentRequestBuilder listChargesForPayment(
      StoreId storeId, SubscriptionId subscriptionId, ScheduledPaymentId paymentId) {
    return new SubscriptionBuilders.ListChargesForPaymentRequestBuilder(
        retrofit, storeId, subscriptionId, paymentId);
  }

  @Override
  public SubscriptionBuilders.ListSubscriptionChargesRequestBuilder listSubscriptionCharges(
      StoreId storeId, SubscriptionId subscriptionId) {
    return new SubscriptionBuilders.ListSubscriptionChargesRequestBuilder(
        retrofit, storeId, subscriptionId);
  }

  @Override
  public SubscriptionBuilders.DeleteSubscriptionRequestBuilder deleteSubscription(
      StoreId storeId, SubscriptionId subscriptionId) {
    return new SubscriptionBuilders.DeleteSubscriptionRequestBuilder(
        retrofit, storeId, subscriptionId);
  }

  @Override
  public SubscriptionBuilders.ListScheduledPaymentsRequestBuilder listScheduledPayments(
      StoreId storeId, SubscriptionId subscriptionId) {
    return new SubscriptionBuilders.ListScheduledPaymentsRequestBuilder(
        retrofit, storeId, subscriptionId);
  }

  @Override
  public SubscriptionBuilders.GetScheduledPaymentRequestBuilder getScheduledPayment(
      StoreId storeId, SubscriptionId subscriptionId, ScheduledPaymentId paymentId) {
    return new SubscriptionBuilders.GetScheduledPaymentRequestBuilder(
        retrofit, storeId, subscriptionId, paymentId);
  }

  @Override
  public SubscriptionBuilders.UpdateScheduledPaymentRequestBuilder updateScheduledPayment(
      StoreId storeId, SubscriptionId subscriptionId, ScheduledPaymentId paymentId) {
    return new SubscriptionBuilders.UpdateScheduledPaymentRequestBuilder(
        retrofit, storeId, subscriptionId, paymentId);
  }

  @Override
  public SubscriptionBuilders.SimulateInstallmentsPlanRequestBuilder simulateSubscriptionPlan(
      MoneyLike money, PaymentTypeName paymentType, SubscriptionPeriod period) {
    return new SubscriptionBuilders.SimulateInstallmentsPlanRequestBuilder(
        retrofit, money, paymentType, period);
  }

  @Override
  public SubscriptionBuilders.SimulateInstallmentsPlanRequestBuilder simulateSubscriptionPlan(
      StoreId storeId, MoneyLike money, PaymentTypeName paymentType, SubscriptionPeriod period) {
    return new SubscriptionBuilders.SimulateInstallmentsPlanRequestBuilder(
        retrofit, storeId, money, paymentType, period);
  }

  /*
   * <h2>REFUNDS</h2>
   * <p>Methods for managing refunds.</p>
   */

  @Override
  public RefundBuilders.ListRefundsRequestBuilder listRefunds(StoreId storeId, ChargeId chargeId) {
    return new RefundBuilders.ListRefundsRequestBuilder(retrofit, storeId, chargeId);
  }

  @Override
  public RefundBuilders.GetRefundRequestBuilder getRefund(
      StoreId storeId, ChargeId chargeId, RefundId refundId) {
    return new RefundBuilders.GetRefundRequestBuilder(retrofit, storeId, chargeId, refundId);
  }

  @Override
  public RefundBuilders.CreateRefundRequestBuilder createRefund(
      StoreId storeId, ChargeId chargeId, BigInteger amount, String currency, RefundReason reason) {
    return new RefundBuilders.CreateRefundRequestBuilder(
        retrofit, storeId, chargeId, amount, currency, reason);
  }

  @Override
  public RefundBuilders.CreateRefundRequestBuilder createRefund(
      StoreId storeId, ChargeId chargeId, MoneyLike money, RefundReason reason) {
    return createRefund(storeId, chargeId, money.getAmount(), money.getCurrency(), reason);
  }

  @Override
  public ResourceMonitor<Refund> refundCompletionMonitor(
      StoreId storeId, ChargeId chargeId, RefundId refundId) {

    return RefundBuilders.createRefundCompletionMonitor(retrofit, storeId, chargeId, refundId);
  }

  /*
   * <h2>CANCEL</h2>
   * <p>Methods used for cancelling charges.</p>
   */

  @Override
  public CancelsBuilders.ListAllCancelsRequestBuilder listCancels(
      StoreId storeId, ChargeId chargeId) {
    return new CancelsBuilders.ListAllCancelsRequestBuilder(retrofit, storeId, chargeId);
  }

  @Override
  public CancelsBuilders.GetCancelRequestBuilder getCancel(
      StoreId storeId, ChargeId chargeId, CancelId cancelId) {
    return new CancelsBuilders.GetCancelRequestBuilder(retrofit, storeId, chargeId, cancelId);
  }

  @Override
  public ResourceMonitor<Cancel> cancelCompletionMonitor(
      StoreId storeId, ChargeId chargeId, CancelId cancelId) {
    return CancelsBuilders.createRefundCompletionMonitor(retrofit, storeId, chargeId, cancelId);
  }

  @Override
  public CancelsBuilders.CreateCancelRequestBuilder createCancel(
      StoreId storeId, ChargeId chargeId) {
    return new CancelsBuilders.CreateCancelRequestBuilder(retrofit, storeId, chargeId);
  }

  @Override
  public CancelsBuilders.UpdateCancelRequestBuilder updateCancel(
      StoreId storeId, ChargeId chargeId, CancelId cancelId) {
    return new CancelsBuilders.UpdateCancelRequestBuilder(retrofit, storeId, chargeId, cancelId);
  }

  /*
   * <h2>BANK ACCOUNTS</h2>
   * Methods for managing bank accounts.
   */

  @Override
  public BankAccountsBuilders.ListAllBankAccountsRequestBuilder listBankAccounts() {
    return new BankAccountsBuilders.ListAllBankAccountsRequestBuilder(retrofit);
  }

  @Override
  public BankAccountsBuilders.GetBankAccountRequestBuilder getBankAccount(
      BankAccountId bankAccountId) {
    return new BankAccountsBuilders.GetBankAccountRequestBuilder(retrofit, bankAccountId);
  }

  @Override
  public BankAccountsBuilders.CreateBankAccountRequestBuilder createBankAccount(
      JapaneseBankAccount bankAccount) {
    return new BankAccountsBuilders.CreateBankAccountRequestBuilder(retrofit, bankAccount);
  }

  @Override
  public BankAccountsBuilders.UpdateBankAccountRequestBuilder updateBankAccount(
      BankAccountId bankAccountId) {
    return new BankAccountsBuilders.UpdateBankAccountRequestBuilder(retrofit, bankAccountId);
  }

  @Override
  public BankAccountsBuilders.DeleteBankAccountRequestBuilder deleteBankAccount(
      BankAccountId bankAccountId) {
    return new BankAccountsBuilders.DeleteBankAccountRequestBuilder(retrofit, bankAccountId);
  }

  @Override
  public BankAccountsBuilders.GetPrimaryBankAccountRequestBuilder getPrimaryBankAccount() {
    return new BankAccountsBuilders.GetPrimaryBankAccountRequestBuilder(retrofit);
  }

  /*
   * <h2>MERCHANTS</h2>
   * Methods that provide or update information on the merchant.
   */

  @Override
  public MerchantsBuilders.GetMerchantVerificationRequestBuilder getMerchantVerification() {
    return new MerchantsBuilders.GetMerchantVerificationRequestBuilder(retrofit);
  }

  @Override
  public MerchantsBuilders.UpdateMerchantVerificationRequestBuilder updateMerchantVerification() {
    return new MerchantsBuilders.UpdateMerchantVerificationRequestBuilder(retrofit);
  }

  @Override
  public MerchantsBuilders.CreateMerchantVerificationRequestBuilder createMerchantVerification(
      URL homepageUrl,
      String companyDescription,
      MerchantCompanyContactInfo companyContactInfo,
      BusinessType businessType,
      String systemManagerName) {
    return new MerchantsBuilders.CreateMerchantVerificationRequestBuilder(
        retrofit,
        homepageUrl,
        companyDescription,
        companyContactInfo,
        businessType,
        systemManagerName);
  }

  @Override
  public MerchantsBuilders.GetMeRequestBuilder getMe() {
    return new MerchantsBuilders.GetMeRequestBuilder(retrofit);
  }

  @Override
  public MerchantsBuilders.GetTransactionHistoryRequestBuilder getTransactionHistory(
      StoreId storeId) {
    return new MerchantsBuilders.GetTransactionHistoryRequestBuilder(retrofit, storeId);
  }

  @Override
  public MerchantsBuilders.GetTransactionHistoryRequestBuilder getTransactionHistory() {
    return new MerchantsBuilders.GetTransactionHistoryRequestBuilder(retrofit, null);
  }

  /*
   * <h2>WEBHOOKS</h2>
   * Methods for managing webhooks.
   */

  @Override
  public WebhookBuilders.ListWebhookMerchantRequestBuilder listWebhooks() {
    return new WebhookBuilders.ListWebhookMerchantRequestBuilder(retrofit);
  }

  @Override
  public WebhookBuilders.GetWebhookMerchantRequestBuilder getWebhook(WebhookId webhookId) {
    return new WebhookBuilders.GetWebhookMerchantRequestBuilder(retrofit, webhookId);
  }

  @Override
  public WebhookBuilders.CreateWebhookMerchantRequestBuilder createWebhook(URL url) {
    return new WebhookBuilders.CreateWebhookMerchantRequestBuilder(retrofit, url);
  }

  @Override
  public WebhookBuilders.UpdateWebhookMerchantRequestBuilder updateWebhook(WebhookId webhookId) {
    return new WebhookBuilders.UpdateWebhookMerchantRequestBuilder(retrofit, webhookId);
  }

  @Override
  public WebhookBuilders.DeleteWebhookMerchantRequestBuilder deleteWebhook(WebhookId webhookId) {
    return new WebhookBuilders.DeleteWebhookMerchantRequestBuilder(retrofit, webhookId);
  }

  @Override
  public WebhookBuilders.ListWebhookRequestBuilder listWebhooks(StoreId storeId) {
    return new WebhookBuilders.ListWebhookRequestBuilder(retrofit, storeId);
  }

  @Override
  public WebhookBuilders.GetWebhookRequestBuilder getWebhook(StoreId storeId, WebhookId webhookId) {
    return new WebhookBuilders.GetWebhookRequestBuilder(retrofit, storeId, webhookId);
  }

  @Override
  public WebhookBuilders.CreateWebhookRequestBuilder createWebhook(StoreId storeId, URL url) {
    return new WebhookBuilders.CreateWebhookRequestBuilder(retrofit, storeId, url);
  }

  @Override
  public WebhookBuilders.UpdateWebhookRequestBuilder updateWebhook(
      StoreId storeId, WebhookId webhookId) {
    return new WebhookBuilders.UpdateWebhookRequestBuilder(retrofit, storeId, webhookId);
  }

  @Override
  public WebhookBuilders.DeleteWebhookRequestBuilder deleteWebhook(
      StoreId storeId, WebhookId webhookId) {
    return new WebhookBuilders.DeleteWebhookRequestBuilder(retrofit, storeId, webhookId);
  }

  /*
   * <h2>TRANSACTION TOKEN</h2>
   */

  @Override
  public TransactionTokensBuilders.CreateTransactionTokenRequestBuilder createTransactionToken(
      String email, PaymentData paymentData, TransactionTokenType type) {
    return new TransactionTokensBuilders.CreateTransactionTokenRequestBuilder(
        retrofit, new EmailAddress(email), paymentData, type);
  }

  @Override
  public TransactionTokensBuilders.CreateTransactionTokenRequestBuilder createTransactionToken(
      PaidyPaymentData paymentData, TransactionTokenType type) {
    return new TransactionTokensBuilders.CreateTransactionTokenRequestBuilder(
        retrofit, null, paymentData, type);
  }

  @Override
  public TransactionTokensBuilders.CreateTransactionTokenRequestBuilder createTransactionToken(
      QrScanData paymentData, TransactionTokenType type) {
    return new TransactionTokensBuilders.CreateTransactionTokenRequestBuilder(
        retrofit, null, paymentData, type);
  }

  @Override
  public TransactionTokensBuilders.CreateTransactionTokenRequestBuilder createTransactionToken(
      QrMerchantData paymentData, TransactionTokenType type) {
    return new TransactionTokensBuilders.CreateTransactionTokenRequestBuilder(
        retrofit, null, paymentData, type);
  }

  @Override
  public TransactionTokensBuilders.DeleteTransactionTokenRequestBuilder deleteTransactionToken(
      StoreId storeId, TransactionTokenId tokenId) {
    return new TransactionTokensBuilders.DeleteTransactionTokenRequestBuilder(
        retrofit, storeId, tokenId);
  }

  @Override
  public TransactionTokensBuilders.GetTransactionTokenRequestBuilder getTransactionToken(
      StoreId storeId, TransactionTokenId tokenId) {
    return new TransactionTokensBuilders.GetTransactionTokenRequestBuilder(
        retrofit, storeId, tokenId);
  }

  @Override
  public TransactionTokensBuilders.UpdateTransactionTokenRequestBuilder updateTransactionToken(
      StoreId storeId, TransactionTokenId tokenId) {
    return new TransactionTokensBuilders.UpdateTransactionTokenRequestBuilder(
        retrofit, storeId, tokenId);
  }

  @Override
  public TransactionTokensBuilders.ListTransactionTokensRequestBuilder listTransactionTokens(
      StoreId storeId) {
    return new TransactionTokensBuilders.ListTransactionTokensRequestBuilder(retrofit, storeId);
  }

  @Override
  public TransactionTokensBuilders.ListTransactionTokensMerchantRequestBuilder
      listTransactionTokens() {
    return new TransactionTokensBuilders.ListTransactionTokensMerchantRequestBuilder(retrofit);
  }

  @Override
  public TransactionTokensBuilders.CreateTemporaryTokenAliasRequestBuilder createTokenAlias(
      TransactionTokenId transactionTokenId) {
    return new TransactionTokensBuilders.CreateTemporaryTokenAliasRequestBuilder(
        retrofit, transactionTokenId);
  }

  @Override
  public TransactionTokensBuilders.GetTemporaryTokenAliasRequestBuilder getTokenAlias(
      StoreId storeId, TokenAliasKey aliasKey) {
    return new TransactionTokensBuilders.GetTemporaryTokenAliasRequestBuilder(
        retrofit, storeId, aliasKey);
  }

  @Override
  public TransactionTokensBuilders.GetTemporaryTokenAliasAsImageRequestBuilder getTokenAliasImage(
      StoreId storeId, TokenAliasKey aliasKey) {
    return new TransactionTokensBuilders.GetTemporaryTokenAliasAsImageRequestBuilder(
        retrofit, storeId, aliasKey);
  }

  @Override
  public TransactionTokensBuilders.DeleteTemporaryTokenAliasRequestBuilder deleteTokenAlias(
      StoreId storeId, TokenAliasKey aliasKey) {
    return new TransactionTokensBuilders.DeleteTemporaryTokenAliasRequestBuilder(
        retrofit, storeId, aliasKey);
  }

  @Override
  public TransactionTokensBuilders.ConfirmTransactionTokenRequestBuilder confirmTransactionToken(
      StoreId storeId, TransactionTokenId tokenId, String confirmationCode) {
    return new TransactionTokensBuilders.ConfirmTransactionTokenRequestBuilder(
        retrofit, storeId, tokenId, confirmationCode);
  }

  /*
   * <h2>LEDGERS</h2>
   * <p>Methods for obtaining information related to ledgers</p>
   */

  @Override
  public LedgersBuilders.ListLedgersRequestBuilder listLedgers(TransferId transferId) {
    return new LedgersBuilders.ListLedgersRequestBuilder(retrofit, transferId);
  }

  /*
   * <h2>EXCHANGE RATES</h2>
   * <p>Methods related to exchange rates and money conversions</p>
   */
  @Override
  public ExchangeRateBuilders.ConvertMoneyBuilder convertMoney(
      MoneyLike moneyToConvert, String targetCurrency) {
    return new ExchangeRateBuilders.ConvertMoneyBuilder(retrofit, moneyToConvert, targetCurrency);
  }

  @Override
  public UtilityBuilders.HeartBeatRequestBuilder beat() {
    return new UtilityBuilders.HeartBeatRequestBuilder(retrofit);
  }

  @Override
  public void close() throws IOException {
    Factory client = retrofit.callFactory();
    if (client instanceof OkHttpClient) {
      ((OkHttpClient) client).dispatcher().executorService().shutdown();
    }
  }
}
