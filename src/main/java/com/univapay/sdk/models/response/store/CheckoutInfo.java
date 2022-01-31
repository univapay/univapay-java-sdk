package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.KonbiniConfiguration;
import com.univapay.sdk.models.common.PaidyConfiguration;
import com.univapay.sdk.models.common.RecurringTokenCVVConfirmation;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.models.response.store.checkoutInfo.*;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import java.net.URI;
import java.util.List;

public class CheckoutInfo extends UnivapayResponse {
  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("recurring_token_privilege")
  private RecurringTokenPrivilege recurringTokenPrivilege;

  @SerializedName("name")
  private String name;

  @SerializedName("card_configuration")
  private CardConfiguration cardConfiguration;

  @SerializedName("supported_brands")
  private List<CheckoutFeatureSupport> supportedBrands;

  @SerializedName("qr_scan_configuration")
  private QrScanConfiguration qrScanConfiguration;

  @SerializedName("convenience_configuration")
  private KonbiniConfiguration konbiniConfiguration;

  @SerializedName("paidy_configuration")
  private PaidyConfiguration paidyConfiguration;

  @SerializedName("paidy_public_key")
  private String paidyPublicKey;

  @SerializedName("logo_image")
  private URI logoImage;

  @SerializedName("theme")
  private WidgetConfiguration theme;

  @SerializedName("recurring_card_charge_cvv_confirmation")
  private RecurringTokenCVVConfirmation recurringTokenCVVConfirmation;

  @SerializedName("subscription_configuration")
  private SubscriptionConfiguration subscriptionConfiguration;

  @SerializedName("checkout_configuration")
  private CheckoutConfiguration checkoutConfiguration;

  @SerializedName("installments_configuration")
  private InstallmentsConfiguration installmentsConfiguration;

  @SerializedName("online_configuration")
  private OnlineConfiguration onlineConfiguration;

  public OnlineConfiguration getOnlineConfiguration() {
    return onlineConfiguration;
  }

  @SerializedName("bank_transfer_configuration")
  private BankTransferConfiguration bankTransferConfiguration;

  public ProcessingMode getMode() {
    return mode;
  }

  public RecurringTokenPrivilege getRecurringTokenPrivilege() {
    return recurringTokenPrivilege;
  }

  public CardConfiguration getCardConfiguration() {
    return cardConfiguration;
  }

  public List<CheckoutFeatureSupport> getSupportedBrands() {
    return supportedBrands;
  }

  public QrScanConfiguration getQrScanConfiguration() {
    return qrScanConfiguration;
  }

  public KonbiniConfiguration getKonbiniConfiguration() {
    return konbiniConfiguration;
  }

  public PaidyConfiguration getPaidyConfiguration() {
    return paidyConfiguration;
  }

  public String getPaidyPublicKey() {
    return paidyPublicKey;
  }

  public String getName() {
    return name;
  }

  public URI getLogoImage() {
    return logoImage;
  }

  public WidgetConfiguration getTheme() {
    return theme;
  }

  public RecurringTokenCVVConfirmation getRecurringTokenCVVConfirmation() {
    return recurringTokenCVVConfirmation;
  }

  public SubscriptionConfiguration getSubscriptionConfiguration() {
    return subscriptionConfiguration;
  }

  public CheckoutConfiguration getCheckoutConfiguration() {
    return checkoutConfiguration;
  }

  public InstallmentsConfiguration getInstallmentsConfiguration() {
    return installmentsConfiguration;
  }

  public BankTransferConfiguration getBankTransferConfiguration() {
    return bankTransferConfiguration;
  }
}
