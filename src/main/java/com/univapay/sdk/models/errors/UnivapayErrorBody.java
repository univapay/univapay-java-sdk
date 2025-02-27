package com.univapay.sdk.models.errors;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Getter;

@Getter
public class UnivapayErrorBody {
  @SerializedName("status")
  private String status;

  @SerializedName("code")
  private String code;

  @SerializedName("errors")
  private List<DetailedError> errors;

  UnivapayErrorBody() {}

  UnivapayErrorBody(String code, String status, List<DetailedError> errors) {
    this.status = status;
    this.code = code;
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "{code:"
        + code
        + ", status:"
        + status
        + ", details:"
        + (errors != null ? errors.toString() : "null")
        + "}";
  }
}
