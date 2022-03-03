package com.univapay.sdk.models.response.store.checkoutInfo;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.VirtualBankMatchAmount;
import java.time.Period;

public class BankTransferConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("match_amount")
  private final VirtualBankMatchAmount matchAmount;

  @SerializedName("expiration")
  private final Period expirationPeriod;

  @SerializedName("virtual_bank_accounts_threshold")
  private final Integer virtualBankAccountThreshold;

  @SerializedName("virtual_bank_accounts_fetch_count")
  private final Integer virtualBankAccountFetchCount;

  public BankTransferConfiguration(
      Boolean enabled,
      VirtualBankMatchAmount matchAmount,
      Period expirationPeriod,
      Integer virtualBankAccountThreshold,
      Integer virtualBankAccountFetchCount) {
    this.enabled = enabled;
    this.matchAmount = matchAmount;
    this.expirationPeriod = expirationPeriod;
    this.virtualBankAccountThreshold = virtualBankAccountThreshold;
    this.virtualBankAccountFetchCount = virtualBankAccountFetchCount;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public VirtualBankMatchAmount getMatchAmount() {
    return matchAmount;
  }

  public Period getExpirationPeriod() {
    return expirationPeriod;
  }

  public Integer getVirtualBankAccountFetchCount() {
    return virtualBankAccountFetchCount;
  }

  public Integer getVirtualBankAccountThreshold() {
    return virtualBankAccountThreshold;
  }
}
