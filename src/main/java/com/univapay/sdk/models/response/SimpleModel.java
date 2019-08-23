package com.univapay.sdk.models.response;

import com.univapay.sdk.models.common.BaseId;

/**
 * This marker represents a POJO that can be a response from the API T : the type of an ID of the
 * model
 */
public interface SimpleModel<T extends BaseId> {
  T getId();
}
