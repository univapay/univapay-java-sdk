package com.univapay.sdk.models.response.bankaccount;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.BankAccountStatus;
import com.univapay.sdk.types.BankAccountType;
import com.univapay.sdk.types.Country;
import java.util.Date;
import java.util.UUID;

public class BankAccount extends UnivapayResponse implements SimpleModel<BankAccountId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("holder_name")
  private String holderName;

  @SerializedName("bank_name")
  private String bankName;

  @SerializedName("branch_name")
  private String branchName;

  @SerializedName("country")
  private Country country;

  @SerializedName("bank_address")
  private String bankAddress;

  @SerializedName("currency")
  private String currency;

  @SerializedName("account_number")
  private String accountNumber;

  @SerializedName("routing_number")
  private String routingNumber;

  @SerializedName("swift_code")
  private String swiftCode;

  @SerializedName("ifsc_code")
  private String ifscCode;

  @SerializedName("routing_code")
  private String routingCode;

  @SerializedName("last_four")
  private String lastFour;

  @SerializedName("status")
  private BankAccountStatus status;

  @SerializedName("primary")
  private Boolean primary;

  @SerializedName("account_type")
  private BankAccountType accountType;

  @SerializedName("created_on")
  private Date createdOn;

  public BankAccountId getId() {
    return new BankAccountId(id);
  }

  public String getHolderName() {
    return holderName;
  }

  public String getBankName() {
    return bankName;
  }

  public String getBranchName() {
    return branchName;
  }

  /**
   * The returned type will be changed to {@link Country} on later release
   *
   * @return country
   */
  public String getCountry() {
    if (country == null) {
      return null;
    }
    return country.getAlpha2();
  }

  /**
   * This method will be deleted when the returned type by "getCountry(String)" is changed to {@link
   * Country}
   *
   * @return country enum
   */
  public Country getCountryEnum() {
    return country;
  }

  public String getBankAddress() {
    return bankAddress;
  }

  public String getCurrency() {
    return currency;
  }

  public String getRoutingNumber() {
    return routingNumber;
  }

  public String getSwiftCode() {
    return swiftCode;
  }

  public String getIfscCode() {
    return ifscCode;
  }

  public String getRoutingCode() {
    return routingCode;
  }

  public String getLastFour() {
    return lastFour;
  }

  public BankAccountStatus getStatus() {
    return status;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public Boolean getPrimary() {
    return primary;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BankAccountType getAccountType() {
    return accountType;
  }
}
