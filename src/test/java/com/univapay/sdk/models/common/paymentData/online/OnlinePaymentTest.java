package com.univapay.sdk.models.common.paymentData.online;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.google.gson.Gson;
import com.univapay.sdk.models.common.OnlinePayment;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.utils.RetrofitBuilder;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import org.junit.jupiter.api.Test;

class OnlinePaymentTest {

  // Responsible to check GSON can  read / write the OnlinePayment class
  private final RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

  @Test
  void shouldReadOnlinePayment() {

    OnlinePayment expected = new OnlinePayment(OnlineBrand.ALIPAY);

    String json = JsonLoader.loadJson("serialization/online.json");

    final Gson gson = retrofitBuilder.getGson();

    OnlinePayment onlinePayment = gson.fromJson(json, OnlinePayment.class);

    assertThat(onlinePayment, samePropertyValuesAs(expected));
  }
}
