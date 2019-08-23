package com.univapay.sdk.models.request.authentication;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.UnivapayEmailAddress;

@SuppressWarnings("FieldCanBeLocal")
public class AuthenticationRequestData {
  @SerializedName("password")
  private String password;

  @SerializedName("email")
  private UnivapayEmailAddress email;

  public AuthenticationRequestData(EmailAddress email, String password) {
    this.password = password;
    this.email = email;
  }
}
