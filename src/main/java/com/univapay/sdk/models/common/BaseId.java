package com.univapay.sdk.models.common;

import java.util.UUID;

/**
 *
 *
 * <h2>BaseId</h2>
 *
 * <p>Class extended by ID classes. Allows creating ID objects by passing a string or a UUID.
 */
public abstract class BaseId {
  private UUID id;

  public BaseId(String id) {
    this.id = UUID.fromString(id);
  }

  public BaseId(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    if (this.id == null) {
      return null;
    }
    return this.id.toString();
  }

  public UUID toUUID() {
    return this.id;
  }
}
