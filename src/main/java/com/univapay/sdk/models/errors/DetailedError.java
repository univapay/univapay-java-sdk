package com.univapay.sdk.models.errors;

import com.google.gson.annotations.SerializedName;

public class DetailedError {

  @SerializedName("field")
  private String field;

  @SerializedName("reason")
  private String reason;

  public String getField() {
    return field;
  }

  public String getReason() {
    return reason;
  }

  public DetailedError() {}

  public DetailedError(String field, String reason) {
    this.field = field;
    this.reason = reason;
  }

  @Override
  public String toString() {
    return field + ":" + reason;
  }

  @Override
  public boolean equals(Object other) {
    Boolean equals;
    try {
      equals =
          ((DetailedError) other).field.equals(this.field)
              && ((DetailedError) other).reason.equals(this.reason);
    } catch (Throwable t) {
      equals = false;
    }

    return equals;
  }
}
