package com.univapay.sdk.models.common.auth;

/**
 * Interface implemented by classes that construct authorization headers according to the
 * authentication type of the merchant.
 */
public interface AuthStrategy {

  AuthHeader getAuthHeader();
}
