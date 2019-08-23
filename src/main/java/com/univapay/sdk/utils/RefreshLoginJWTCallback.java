package com.univapay.sdk.utils;

import com.univapay.sdk.models.common.auth.AuthHeader;

public interface RefreshLoginJWTCallback {
  void onRefreshed(AuthHeader authHeader);
}
