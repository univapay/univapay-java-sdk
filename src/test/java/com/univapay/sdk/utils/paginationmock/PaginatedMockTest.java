package com.univapay.sdk.utils.paginationmock;

import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class PaginatedMockTest extends GenericTest {

  @Test
  void shouldListNoOption() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/stores", jwt, 200, new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listStores()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "11e81b94-4f52-1398-8ab3-230675bcb38f",
                    response.getItems().get(0).getId().toString());
                assertEquals("Store 3", response.getItems().get(0).getName());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    "8486dc98-9836-41dd-b598-bbf49d5bc861",
                    response.getItems().get(1).getId().toString());
                assertEquals("Store 2", response.getItems().get(1).getName());
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                assertEquals(
                    "11e81b94-4f53-a5c8-8ab3-d75ea65c02fc",
                    response.getItems().get(2).getId().toString());
                assertEquals("Store 1", response.getItems().get(2).getName());
                assertEquals(response.getItems().get(2).getCreatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldListLimit2DescCursor() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=2&cursor_direction=desc&cursor=11e81b94-4f52-1398-8ab3-230675bcb38f",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listStores()
        .setCursor(new StoreId("11e81b94-4f52-1398-8ab3-230675bcb38f"))
        .setCursorDirection(CursorDirection.DESC)
        .setLimit(2)
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "8486dc98-9836-41dd-b598-bbf49d5bc861",
                    response.getItems().get(0).getId().toString());
                assertEquals("Store 2", response.getItems().get(0).getName());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    "11e81b94-4f53-a5c8-8ab3-d75ea65c02fc",
                    response.getItems().get(1).getId().toString());
                assertEquals("Store 1", response.getItems().get(1).getName());
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldListLimit2Asc() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=2&cursor_direction=asc&cursor=11e81b94-4f53-a5c8-8ab3-d75ea65c02fc",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listStores()
        .setCursor(new StoreId("11e81b94-4f53-a5c8-8ab3-d75ea65c02fc"))
        .setCursorDirection(CursorDirection.ASC)
        .setLimit(2)
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "8486dc98-9836-41dd-b598-bbf49d5bc861",
                    response.getItems().get(0).getId().toString());
                assertEquals("Store 2", response.getItems().get(0).getName());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    "11e81b94-4f52-1398-8ab3-230675bcb38f",
                    response.getItems().get(1).getId().toString());
                assertEquals("Store 3", response.getItems().get(1).getName());
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldListLimit1HasMore() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?limit=1&cursor_direction=asc&cursor=11e81b94-4f53-a5c8-8ab3-d75ea65c02fc",
        jwt,
        200,
        new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-02-27T17:00:43.476016+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listStores()
        .setCursor(new StoreId("11e81b94-4f53-a5c8-8ab3-d75ea65c02fc"))
        .setCursorDirection(CursorDirection.ASC)
        .setLimit(1)
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {
                assertTrue(response.getHasMore());
                assertEquals(
                    "8486dc98-9836-41dd-b598-bbf49d5bc861",
                    response.getItems().get(0).getId().toString());
                assertEquals("Store 2", response.getItems().get(0).getName());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(1, response.getItems().size());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }
}
