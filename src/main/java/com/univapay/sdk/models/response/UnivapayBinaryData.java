package com.univapay.sdk.models.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UnivapayBinaryData extends UnivapayResponse {

  private byte[] bytes;

  public byte[] getBytes() {
    return bytes;
  }

  public InputStream getInputStream() {
    return new ByteArrayInputStream(bytes);
  }

  public UnivapayBinaryData(byte[] bytes) {
    this.bytes = bytes;
  }
}
