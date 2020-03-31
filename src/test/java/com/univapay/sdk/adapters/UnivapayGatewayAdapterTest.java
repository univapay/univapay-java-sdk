package com.univapay.sdk.adapters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.response.gateway.UnivapayGateway;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.RequestUtils;
import org.junit.Test;

public class UnivapayGatewayAdapterTest extends GenericTest {

  @Test
  public void shouldDeserializeAllGatewayValuesCorrectly() throws Exception {

    Class<Gateway> gatewayClass = Gateway.class;
    final Gson gson = RequestUtils.getGson();

    for (Gateway gateway : gatewayClass.getEnumConstants()) {

      SerializedName serializedName =
          gatewayClass.getField(gateway.name()).getAnnotation(SerializedName.class);

      UnivapayGateway parsedGateway =
          gson.fromJson(new JsonPrimitive(serializedName.value()), UnivapayGateway.class);
      assertThat(gateway, is(parsedGateway.getGateway()));
    }
  }
}
