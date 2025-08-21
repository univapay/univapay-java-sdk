package com.univapay.sdk.pagination;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.store.StoreBuilders;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedListIterable;
import com.univapay.sdk.models.response.PaginatedListIterator;
import com.univapay.sdk.models.response.UnivapayPaginatedListIterator;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.paginationmock.PaginatedMock;
import com.univapay.sdk.utils.paginationmock.StoresMock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PaginatedListIterableTest extends GenericTest {
  @Test
  void shouldRequestIterableStores() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=2&cursor_direction=desc",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    PaginatedListIterable<Store> iterable =
        payments.listStores().setLimit(2).setCursorDirection(CursorDirection.DESC).asIterable();
    List<List<Store>> actual = new ArrayList<>();

    int i = 0;
    for (List<Store> stores : iterable) {
      List<Store> onePage = new ArrayList<>();
      for (Store store : stores) {
        onePage.add(store);
        i++;
        if (i == 2) {
          mockRRGenerator.GenerateMockRequestResponseJWT(
              "GET",
              "/stores?limit=2&cursor_direction=desc&cursor=8486dc98-9836-41dd-b598-bbf49d5bc861",
              jwt,
              200,
              new PaginatedMock(StoresMock.storesMock));
        }
      }
      actual.add(onePage);
    }

    assertThat(actual.get(0).get(0).getId().toString(), is("11e81b94-4f52-1398-8ab3-230675bcb38f"));
    assertThat(actual.get(0).get(0).getName(), is("Store 3"));
    assertThat(actual.get(0).get(0).getCreatedOn(), is(parsedDate));
    assertThat(actual.get(0).get(1).getId().toString(), is("8486dc98-9836-41dd-b598-bbf49d5bc861"));
    assertThat(actual.get(0).get(1).getName(), is("Store 2"));
    assertThat(actual.get(0).get(1).getCreatedOn(), is(parsedDate));

    assertThat(actual.get(1).get(0).getId().toString(), is("11e81b94-4f53-a5c8-8ab3-d75ea65c02fc"));
    assertThat(actual.get(1).get(0).getName(), is("Store 1"));
    assertThat(actual.get(1).get(0).getCreatedOn(), is(parsedDate));

    assertThat(actual.get(0).size(), is(2));
    assertThat(actual.get(1).size(), is(1));
    assertThat(actual.size(), is(2));
  }

  @Test
  void shouldRequestIterableStoresReverse() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=2&cursor_direction=asc",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));
    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    PaginatedListIterable<Store> iterable =
        payments.listStores().setLimit(2).setCursorDirection(CursorDirection.DESC).asIterable();
    iterable.iterator().reverse();

    List<List<Store>> actual = new ArrayList<>();
    int i = 0;
    for (List<Store> stores : iterable) {
      List<Store> onePage = new ArrayList<>();
      for (Store store : stores) {
        onePage.add(store);
        i++;
        if (i == 2) {
          mockRRGenerator.GenerateMockRequestResponseJWT(
              "GET",
              "/stores?limit=2&cursor_direction=asc&cursor=8486dc98-9836-41dd-b598-bbf49d5bc861",
              jwt,
              200,
              new PaginatedMock(StoresMock.storesMock));
        }
      }
      actual.add(onePage);
    }

    assertThat(actual.get(0).get(0).getId().toString(), is("11e81b94-4f53-a5c8-8ab3-d75ea65c02fc"));
    assertThat(actual.get(0).get(0).getName(), is("Store 1"));
    assertThat(actual.get(0).get(0).getCreatedOn(), is(parsedDate));
    assertThat(actual.get(0).get(1).getId().toString(), is("8486dc98-9836-41dd-b598-bbf49d5bc861"));
    assertThat(actual.get(0).get(1).getName(), is("Store 2"));
    assertThat(actual.get(0).get(1).getCreatedOn(), is(parsedDate));

    assertThat(actual.get(1).get(0).getId().toString(), is("11e81b94-4f52-1398-8ab3-230675bcb38f"));
    assertThat(actual.get(1).get(0).getName(), is("Store 3"));
    assertThat(actual.get(1).get(0).getCreatedOn(), is(parsedDate));

    assertThat(actual.get(0).size(), is(2));
    assertThat(actual.get(1).size(), is(1));
    assertThat(actual.size(), is(2));
  }

  @Test
  void shouldRequestIterableStoresReverseInMiddle() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=1&cursor_direction=desc&cursor=11e81b94-4f52-1398-8ab3-230675bcb38f",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));
    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    PaginatedListIterable<Store> iterable =
        payments
            .listStores()
            .setLimit(1)
            .setCursorDirection(CursorDirection.DESC)
            .setCursor(new StoreId("11e81b94-4f52-1398-8ab3-230675bcb38f"))
            .asIterable();

    List<List<Store>> actual = new ArrayList<>();
    UnivapayPaginatedListIterator<Store> iterator = iterable.iterator();
    actual.add(iterator.next());
    iterator.reverse();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=1&cursor_direction=asc&cursor=8486dc98-9836-41dd-b598-bbf49d5bc861",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));
    actual.add(iterator.next());
    iterator.reverse();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=1&cursor_direction=desc&cursor=11e81b94-4f52-1398-8ab3-230675bcb38f",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));
    actual.add(iterator.next());

    assertThat(actual.get(0).get(0).getId().toString(), is("8486dc98-9836-41dd-b598-bbf49d5bc861"));
    assertThat(actual.get(0).get(0).getName(), is("Store 2"));
    assertThat(actual.get(0).get(0).getCreatedOn(), is(parsedDate));
    assertThat(actual.get(1).get(0).getId().toString(), is("11e81b94-4f52-1398-8ab3-230675bcb38f"));
    assertThat(actual.get(1).get(0).getName(), is("Store 3"));
    assertThat(actual.get(1).get(0).getCreatedOn(), is(parsedDate));
    assertThat(actual.get(2).get(0).getId().toString(), is("8486dc98-9836-41dd-b598-bbf49d5bc861"));
    assertThat(actual.get(2).get(0).getName(), is("Store 2"));
    assertThat(actual.get(2).get(0).getCreatedOn(), is(parsedDate));

    assertThat(actual.get(0).size(), is(1));
    assertThat(actual.get(1).size(), is(1));
    assertThat(actual.get(2).size(), is(1));
    assertThat(actual.size(), is(3));
  }

  @Test
  void shouldRequestIterableStoresReverseIterable() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=2&cursor_direction=asc",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));
    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    PaginatedListIterable<Store> iterable =
        payments.listStores().setLimit(2).setCursorDirection(CursorDirection.DESC).asIterable();
    iterable.reverse();

    List<List<Store>> actual = new ArrayList<>();
    int i = 0;
    for (List<Store> stores : iterable) {
      List<Store> onePage = new ArrayList<>();
      for (Store store : stores) {
        onePage.add(store);
        i++;
        if (i == 2) {
          mockRRGenerator.GenerateMockRequestResponseJWT(
              "GET",
              "/stores?limit=2&cursor_direction=asc&cursor=8486dc98-9836-41dd-b598-bbf49d5bc861",
              jwt,
              200,
              new PaginatedMock(StoresMock.storesMock));
        }
      }
      actual.add(onePage);
    }

    assertThat(actual.get(0).get(0).getId().toString(), is("11e81b94-4f53-a5c8-8ab3-d75ea65c02fc"));
    assertThat(actual.get(0).get(0).getName(), is("Store 1"));
    assertThat(actual.get(0).get(0).getCreatedOn(), is(parsedDate));
    assertThat(actual.get(0).get(1).getId().toString(), is("8486dc98-9836-41dd-b598-bbf49d5bc861"));
    assertThat(actual.get(0).get(1).getName(), is("Store 2"));
    assertThat(actual.get(0).get(1).getCreatedOn(), is(parsedDate));

    assertThat(actual.get(1).get(0).getId().toString(), is("11e81b94-4f52-1398-8ab3-230675bcb38f"));
    assertThat(actual.get(1).get(0).getName(), is("Store 3"));
    assertThat(actual.get(1).get(0).getCreatedOn(), is(parsedDate));

    assertThat(actual.get(0).size(), is(2));
    assertThat(actual.get(1).size(), is(1));
    assertThat(actual.size(), is(2));
  }

  @Test
  void shouldThrownUnsupportedExceptionWithoutAsIterable() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=1&cursor_direction=desc&cursor=11e81b94-4f52-1398-8ab3-230675bcb38f",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));
    PaginatedListIterator<Store> dummyPaginatedList =
        new PaginatedListIterator<>(new StoreBuilders.ListStoresRequestBuilder(null), 0, null);
    assertThrows(UnsupportedOperationException.class, () -> dummyPaginatedList.next());
  }
}
