package com.univapay.sdk.models.response.store.checkoutInfo;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.BankTransferExpirationTimeShift;
import com.univapay.sdk.models.common.VirtualBankMatchAmount;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BankTransferConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("match_amount")
  private final VirtualBankMatchAmount matchAmount;

  @SerializedName("expiration")
  private final Duration expirationPeriod;

  @SerializedName("expiration_time_shift")
  private final BankTransferExpirationTimeShift expirationTimeShift;

  @SerializedName("virtual_bank_accounts_threshold")
  private final Integer virtualBankAccountThreshold;

  @SerializedName("virtual_bank_accounts_fetch_count")
  private final Integer virtualBankAccountFetchCount;

  @SerializedName("default_extension_period")
  private final Duration defaultExtensionPeriod;

  @SerializedName("maximum_extension_period")
  private final Duration maximumExtensionPeriod;

  @SerializedName("charge_request_notification_enabled")
  private final Boolean chargeRequestNotificationEnabled;

  @SerializedName("deposit_received_notification_enabled")
  private final Boolean depositReceivedNotificationEnabled;

  @SerializedName("deposit_insufficient_notification_enabled")
  private final Boolean depositInsufficientNotificationEnabled;

  @SerializedName("deposit_exceeded_notification_enabled")
  private final Boolean depositExceededNotificationEnabled;

  @SerializedName("extension_notification_enabled")
  private final Boolean extensionNotificationEnabled;

  @SerializedName("remind_notification_period")
  private final Duration remindNotificationPeriod;
}
