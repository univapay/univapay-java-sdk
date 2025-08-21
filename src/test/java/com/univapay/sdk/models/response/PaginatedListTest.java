package com.univapay.sdk.models.response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PaginatedListTest extends GenericTest {

  private AutoCloseable mocks;

  @Mock private StoreBuilders.ListStoresRequestBuilder builder;
  @Mock private Backoff backoff;
  @Mock private PaginatedListIterator<Store> paginatedList;
  @Mock private UnivapaySDK payments;
  @Mock private Request request;

  @BeforeEach
  void setup() {
    mocks = MockitoAnnotations.openMocks(this);
    when(builder.build()).thenReturn(request);
    paginatedList = new PaginatedListIterator<>(builder, 0, backoff);
    when(payments.listStores()).thenReturn(builder);
    when(builder.asIterable()).thenReturn(new PaginatedListIterable<>(paginatedList));
    paginatedList = builder.asIterable().getPaginatedList();
  }

  @Test
  void shouldThrowIfTimeout() throws Throwable {
    when(paginatedList.getLastBuilder().build().dispatch())
        .thenThrow(new TooManyRequestsException(400, "Test Error"));
    UnivapayPaginatedListIterator<Store> iterator = payments.listStores().asIterable().iterator();

    IllegalStateException e = assertThrows(IllegalStateException.class, iterator::next);
    Throwable actualCause = e.getCause();
    assertThat(actualCause.getMessage(), is("Retry timeout exceeded : 0ms "));
  }

  @Test
  void shouldThrowIfRequestFailure() throws Throwable {
    when(paginatedList.getLastBuilder().build().dispatch())
        .thenThrow(new UnivapayException(400, "Test Error", null));
    UnivapayPaginatedListIterator<Store> iterator = payments.listStores().asIterable().iterator();
    IllegalStateException e = assertThrows(IllegalStateException.class, iterator::next);
    Throwable actualCause = e.getCause();

    assertInstanceOf(UnivapayException.class, actualCause);
  }

  @Test
  void shouldThrowIfChangeItems() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/stores", jwt, 200, StoreFakeRR.listAllStoresPaginationParamFakeResponse);
    assertThrows(
        UnsupportedOperationException.class,
        () -> {
          UnivapaySDK payments = createTestInstance(AuthType.JWT);

          PaginatedListIterable<Store> iterable = payments.listStores().asIterable();
          iterable.getPaginatedList().getItems().add(new Store());
        });
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }
}
