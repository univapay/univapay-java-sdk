package com.univapay.sdk.models.response.store;

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

    public BankTransferConfiguration(
            Boolean enabled, VirtualBankMatchAmount matchAmount, Period expirationPeriod) {
        this.enabled = enabled;
        this.matchAmount = matchAmount;
        this.expirationPeriod = expirationPeriod;
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

}
