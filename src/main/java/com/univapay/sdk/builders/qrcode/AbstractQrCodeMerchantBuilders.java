package com.univapay.sdk.builders.qrcode;

import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.QrCode;
import retrofit2.Retrofit;

public class AbstractQrCodeMerchantBuilders {

  public abstract static class AbstractGetQrCodeRequestBuilder<
          B extends AbstractGetQrCodeRequestBuilder<B, R, M>, R, M extends QrCode>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected ChargeId chargeId;

    public AbstractGetQrCodeRequestBuilder(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }
  }
}
