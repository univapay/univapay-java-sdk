package com.univapay.sdk.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.text.ParseException;
import java.time.OffsetDateTime;
import org.junit.Test;

public class ListStoresTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnListOfStoresWithoutQueryParams()
      throws InterruptedException, ParseException {
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
                    response.getItems().get(0).getId().toString(),
                    "100b8ac0-b76a-42b0-b5bd-a1e06f406056");
                assertEquals(response.getItems().get(0).getName(), "New Store 5");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);

                assertEquals(
                    response.getItems().get(1).getId().toString(),
                    "294eb164-71c5-475a-9baf-207381109dcf");
                assertEquals(response.getItems().get(1).getName(), "New Store 4");
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);

                assertEquals(
                    response.getItems().get(2).getId().toString(),
                    "c7cdb063-b29a-49d1-acec-6693363ece11");
                assertEquals(response.getItems().get(2).getName(), "New Store 3");
                assertEquals(response.getItems().get(2).getCreatedOn(), parsedDate);

                assertEquals(
                    response.getItems().get(3).getId().toString(),
                    "3dea7eb6-b03b-4c44-834e-cd9358ceeb61");
                assertEquals(response.getItems().get(3).getName(), "New Store 2");
                assertEquals(response.getItems().get(3).getCreatedOn(), parsedDate);

                assertEquals(
                    response.getItems().get(4).getId().toString(),
                    "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(response.getItems().get(4).getName(), "New Store 1");
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
  public void shouldRequestAndReturnListOfStoresWithOptionalParams()
      throws InterruptedException, ParseException {
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
                    response.getItems().get(0).getId().toString(),
                    "294eb164-71c5-475a-9baf-207381109dcf");
                assertEquals(response.getItems().get(0).getName(), "New Store 4");
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
