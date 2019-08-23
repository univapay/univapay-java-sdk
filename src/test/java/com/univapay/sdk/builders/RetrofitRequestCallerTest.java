package com.univapay.sdk.builders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.Sleeper;
import org.junit.Test;
import org.mockito.Mockito;
import retrofit2.Call;
import retrofit2.Converter;

public class RetrofitRequestCallerTest {

  @Test(expected = TooManyRequestsException.class)
  public void shouldThrowTooManyRequestsIfTimeoutExceeded()
      throws UnivapayException, InterruptedException, IOException {
    // Prepare
    Call call = Mockito.mock(Call.class);
    when(call.clone()).thenReturn(call);
    Converter c = Mockito.mock(Converter.class);
    when(call.execute()).thenThrow(TooManyRequestsException.class);
    RetrofitRequestCaller caller = new RetrofitRequestCaller(c, call);
    Sleeper s = Mockito.mock(Sleeper.class);
    doNothing().when(s).sleep();

    // Action
    caller.dispatch();
  }
}
