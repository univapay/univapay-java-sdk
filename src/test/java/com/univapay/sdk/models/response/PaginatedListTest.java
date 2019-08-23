package com.univapay.sdk.models.response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.util.concurrent.TimeoutException;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.Request;
import com.univapay.sdk.builders.store.StoreBuilders;
import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.Backoff;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PaginatedListTest extends GenericTest {

  @Mock private StoreBuilders.ListStoresRequestBuilder builder;
  @Mock private Backoff backoff;
  @Mock private PaginatedListIterator<Store> paginatedList;
  @Mock private UnivapaySDK payments;
  @Mock private Request request;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(builder.build()).thenReturn(request);
    paginatedList = new PaginatedListIterator<>(builder, 0, backoff);
    when(payments.listStores()).thenReturn(builder);
    when(builder.asIterable()).thenReturn(new PaginatedListIterable<>(paginatedList));
    paginatedList = builder.asIterable().getPaginatedList();
  }

  @Test(expected = TimeoutException.class)
  public void shouldThrowIfTimeout() throws Throwable {
    when(paginatedList.getLastBuilder().build().dispatch())
        .thenThrow(new TooManyRequestsException(400, "Test Error"));

    UnivapayPaginatedListIterator<Store> iterator = payments.listStores().asIterable().iterator();
    try {
      iterator.next();
    } catch (IllegalStateException e) {
      Throwable actualCause = e.getCause();
      assertThat(actualCause.getMessage(), is("Retry timeout exceeded : 0ms "));
      throw actualCause;
    }
    fail();
  }

  @Test(expected = UnivapayException.class)
  public void shouldThrowIfRequestFailure() throws Throwable {
    when(paginatedList.getLastBuilder().build().dispatch())
        .thenThrow(new UnivapayException(400, "Test Error", null));

    UnivapayPaginatedListIterator<Store> iterator = payments.listStores().asIterable().iterator();
    try {
      iterator.next();
    } catch (IllegalStateException e) {
      throw e.getCause();
    }
    fail();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowIfChangeItems() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/stores", token, 200, StoreFakeRR.listAllStoresPaginationParamFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    PaginatedListIterable<Store> iterable = payments.listStores().asIterable();
    iterable.getPaginatedList().getItems().add(new Store());
  }
}
