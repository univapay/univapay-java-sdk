package com.univapay.sdk.models.response.store.checkoutInfo;

import com.google.gson.annotations.SerializedName;

public class InstallmentsConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("only_with_processor")
  private final Boolean onlyWithProcessor;

  @SerializedName("card_processor")
  private final CardProcessor cardProcessor;

  public InstallmentsConfiguration(
      Boolean enabled, Boolean onlyWithProcessor, CardProcessor cardProcessor) {
    this.enabled = enabled;
    this.onlyWithProcessor = onlyWithProcessor;
    this.cardProcessor = cardProcessor;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Boolean getOnlyWithProcessor() {
    return onlyWithProcessor;
  }

  public CardProcessor getCardProcessor() {
    return cardProcessor;
  }

  public static class CardProcessor {

    @SerializedName("fixed_cycle")
    private final Boolean fixedCycle;

    @SerializedName("revolving")
    private final Boolean revolving;

    public CardProcessor(Boolean fixedCycle, Boolean revolving) {
      this.fixedCycle = fixedCycle;
      this.revolving = revolving;
    }

    public Boolean getFixedCycle() {
      return fixedCycle;
    }

    public Boolean getRevolving() {
      return revolving;
    }
  }
}
