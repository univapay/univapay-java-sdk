package com.univapay.sdk.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;

class ListStoresTest extends GenericTest {

  @Test
  void shouldRequestAndReturnListOfStoresWithoutQueryParams() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/stores", jwt, 200, StoreFakeRR.listAllStoresFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .listStores()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {

                assertFalse(response.getHasMore());

                assertEquals(
                    "100b8ac0-b76a-42b0-b5bd-a1e06f406056",
                    response.getItems().get(0).getId().toString());
                assertEquals("New Store 5", response.getItems().get(0).getName());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);

                assertEquals(
                    "294eb164-71c5-475a-9baf-207381109dcf",
                    response.getItems().get(1).getId().toString());
                assertEquals("New Store 4", response.getItems().get(1).getName());
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);

                assertEquals(
                    "c7cdb063-b29a-49d1-acec-6693363ece11",
                    response.getItems().get(2).getId().toString());
                assertEquals("New Store 3", response.getItems().get(2).getName());
                assertEquals(response.getItems().get(2).getCreatedOn(), parsedDate);

                assertEquals(
                    "3dea7eb6-b03b-4c44-834e-cd9358ceeb61",
                    response.getItems().get(3).getId().toString());
                assertEquals("New Store 2", response.getItems().get(3).getName());
                assertEquals(response.getItems().get(3).getCreatedOn(), parsedDate);

                assertEquals(
                    "8486dc98-9836-41dd-b598-bbf49d5bc861",
                    response.getItems().get(4).getId().toString());
                assertEquals("New Store 1", response.getItems().get(4).getName());
                assertEquals(response.getItems().get(4).getCreatedOn(), parsedDate);

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
  void shouldRequestAndReturnListOfStoresWithOptionalParams() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores?search=New%20Store%204",
        jwt,
        200,
        StoreFakeRR.listAllStoresSearchParamFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .listStores()
        .withSearch("New Store 4")
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "294eb164-71c5-475a-9baf-207381109dcf",
                    response.getItems().get(0).getId().toString());
                assertEquals("New Store 4", response.getItems().get(0).getName());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
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
