package com.univapay.sdk.utils.paginationmock;

import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import java.util.Date;
import org.junit.Test;

public class PaginatedMockTest extends GenericTest {

  @Test
  public void shouldListNoOption() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/stores", token, 200, new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2018-02-27T17:00:43.476016+09:00").toDate();

    univapay
        .listStores()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    response.getItems().get(0).getId().toString(),
                    "11e81b94-4f52-1398-8ab3-230675bcb38f");
                assertEquals(response.getItems().get(0).getName(), "Store 3");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    response.getItems().get(1).getId().toString(),
                    "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(response.getItems().get(1).getName(), "Store 2");
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                assertEquals(
                    response.getItems().get(2).getId().toString(),
                    "11e81b94-4f53-a5c8-8ab3-d75ea65c02fc");
                assertEquals(response.getItems().get(2).getName(), "Store 1");
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
  public void shouldListLimit2DescCursor() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores?limit=2&cursor_direction=desc&cursor=11e81b94-4f52-1398-8ab3-230675bcb38f",
        token,
        200,
        new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2018-02-27T17:00:43.476016+09:00").toDate();

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
                    response.getItems().get(0).getId().toString(),
                    "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(response.getItems().get(0).getName(), "Store 2");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    response.getItems().get(1).getId().toString(),
                    "11e81b94-4f53-a5c8-8ab3-d75ea65c02fc");
                assertEquals(response.getItems().get(1).getName(), "Store 1");
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
  public void shouldListLimit2Asc() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores?limit=2&cursor_direction=asc&cursor=11e81b94-4f53-a5c8-8ab3-d75ea65c02fc",
        token,
        200,
        new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2018-02-27T17:00:43.476016+09:00").toDate();

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
                    response.getItems().get(0).getId().toString(),
                    "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(response.getItems().get(0).getName(), "Store 2");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    response.getItems().get(1).getId().toString(),
                    "11e81b94-4f52-1398-8ab3-230675bcb38f");
                assertEquals(response.getItems().get(1).getName(), "Store 3");
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
  public void shouldListLimit1HasMore() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores?limit=1&cursor_direction=asc&cursor=11e81b94-4f53-a5c8-8ab3-d75ea65c02fc",
        token,
        200,
        new PaginatedMock(StoresMock.storesMock));

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2018-02-27T17:00:43.476016+09:00").toDate();

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
                    response.getItems().get(0).getId().toString(),
                    "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(response.getItems().get(0).getName(), "Store 2");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(response.getItems().size(), 1);
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
