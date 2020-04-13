package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MerchantId;
import com.univapay.sdk.models.common.VerificationPhoneNumber;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.BusinessType;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.UUID;

public class MerchantVerificationData extends UnivapayResponse implements SimpleModel<MerchantId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("homepage_url")
  private URL homepageUrl;

  @SerializedName("company_description")
  private String companyDescription;

  @SerializedName("company_contact_info")
  private MerchantCompanyContactInfo companyContactInfo;

  @SerializedName("business_type")
  private BusinessType businessType;

  @SerializedName("system_manager_name")
  private String systemManagerName;

  @SerializedName("system_manager_number")
  private VerificationPhoneNumber systemManagerNumber;

  @SerializedName("system_manager_email")
  private String systemManagerEmail;

  @SerializedName("recurring_token_request")
  private RecurringTokenPrivilege recurringTokenRequest;

  @SerializedName("recurring_token_request_reason")
  private String recurringTokenRequestReason;

  @SerializedName("allow_empty_cvv")
  private Boolean allowEmptyCvv;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  @SerializedName("updated_on")
  private OffsetDateTime updatedOn;

  public MerchantId getId() {
    return new MerchantId(id);
  }

  public URL getHomepageUrl() {
    return homepageUrl;
  }

  public String getCompanyDescription() {
    return companyDescription;
  }

  public MerchantCompanyContactInfo getCompanyContactInfo() {
    return companyContactInfo;
  }

  public BusinessType getBusinessType() {
    return businessType;
  }

  public String getSystemManagerName() {
    return systemManagerName;
  }

  public VerificationPhoneNumber getSystemManagerNumber() {
    return systemManagerNumber;
  }

  public String getSystemManagerEmail() {
    return systemManagerEmail;
  }

  public RecurringTokenPrivilege getRecurringTokenRequest() {
    return recurringTokenRequest;
  }

  public String getRecurringTokenRequestReason() {
    return recurringTokenRequestReason;
  }

  public Boolean getAllowEmptyCvv() {
    return allowEmptyCvv;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public OffsetDateTime getUpdatedOn() {
    return updatedOn;
  }
}
