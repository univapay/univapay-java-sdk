package com.univapay.sdk.models.request.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.models.common.VerificationPhoneNumber;
import com.univapay.sdk.models.response.merchant.MerchantCompanyContactInfo;
import com.univapay.sdk.types.BusinessType;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import java.net.URL;

@SuppressWarnings("FieldCanBeLocal")
public class MerchantsReq {
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
  private UnivapayEmailAddress systemManagerEmail;

  @SerializedName("recurring_token_request")
  private RecurringTokenPrivilege recurringTokenRequest;

  @SerializedName("recurring_token_request_reason")
  private String recurringTokenRequestReason;

  @SerializedName("allow_empty_cvv")
  private Boolean allowEmptyCvv;

  public MerchantsReq(
      URL homepageUrl,
      String companyDescription,
      MerchantCompanyContactInfo companyContactInfo,
      BusinessType businessType,
      String systemManagerName,
      VerificationPhoneNumber systemManagerNumber,
      UnivapayEmailAddress systemManagerEmail,
      RecurringTokenPrivilege recurringTokenRequest,
      String recurringTokenRequestReason,
      Boolean allowEmptyCvv) {
    this.homepageUrl = homepageUrl;
    this.companyDescription = companyDescription;
    this.companyContactInfo = companyContactInfo;
    this.businessType = businessType;
    this.systemManagerName = systemManagerName;
    this.systemManagerNumber = systemManagerNumber;
    this.systemManagerEmail = systemManagerEmail;
    this.recurringTokenRequest = recurringTokenRequest;
    this.recurringTokenRequestReason = recurringTokenRequestReason;
    this.allowEmptyCvv = allowEmptyCvv;
  }
}
