package com.univapay.sdk.models.response.store;

import com.univapay.sdk.models.response.configuration.Configuration;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "storeConfigurationBuilder")
public class StoreConfiguration extends Configuration {}
