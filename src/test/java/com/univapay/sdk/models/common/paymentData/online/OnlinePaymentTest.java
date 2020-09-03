package com.univapay.sdk.models.common.paymentData.online;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.google.gson.Gson;
import com.univapay.sdk.models.common.OnlinePayment;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.utils.RequestUtils;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import org.junit.Test;

public class OnlinePaymentTest {

  // Responsible to check GSON can  read / write the OnlinePayment class
  @Test
  public void shouldReadOnlinePayment() {

    OnlinePayment expected = new OnlinePayment(Gateway.ALIPAY_CONNECT);

    String json = JsonLoader.loadJson("serialization/online.json");

    final Gson gson = RequestUtils.getGson();

    OnlinePayment onlinePayment = gson.fromJson(json, OnlinePayment.class);

    assertThat(onlinePayment, samePropertyValuesAs(expected));
  }
}
