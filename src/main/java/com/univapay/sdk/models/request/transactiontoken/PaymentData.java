package com.univapay.sdk.models.request.transactiontoken;

import com.univapay.sdk.types.PaymentTypeName;

public interface PaymentData {
  PaymentTypeName getPaymentType();
}
