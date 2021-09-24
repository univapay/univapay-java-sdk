package com.univapay.sdk.builders;

/** Handle to cancel a dispatched request that was invoked with a callback */
public interface Cancelable {

  /** Cancel the request as "best effort" basis */
  void cancel();
}
