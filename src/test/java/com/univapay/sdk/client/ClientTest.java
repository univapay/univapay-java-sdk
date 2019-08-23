package com.univapay.sdk.client;

import static junit.framework.TestCase.fail;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ClientTest {

  public static class CloneClientTest extends GenericTest {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Test
    public void shouldCloneClientSharingThreadPool() throws Exception {

      MockRRGenerator mockRRGenerator = new MockRRGenerator();
      mockRRGenerator.GenerateMockRequestResponse(
          "GET", "/stores", token, 200, StoreFakeRR.listAllStoresFakeResponse);

      final int iterations = 50;

      final int initialThreads = countOkHttpThreadPools();

      final UnivapaySDK firstClient = createTestInstance(AuthType.LOGIN_TOKEN);
      List<UnivapaySDK> clients = new ArrayList<>();
      clients.add(firstClient);

      for (Integer i = 0; i <= iterations; i++) {
        UnivapaySDK client = firstClient.clone();
        client.listStores().build().dispatch();
        clients.add(client);
      }

      int finalThreads = countOkHttpThreadPools();

      if (finalThreads - initialThreads != 1) {
        fail((finalThreads - initialThreads) + " thread pools were created, but 1 was expected.");
      } else logger.log(Level.INFO, "Only 1 thread pool was created, as expected");

      for (UnivapaySDK client : clients) {
        client.close();
      }
    }
  }

  public static class CopyClientTest extends GenericTest {

    @Test
    public void shouldCopyClientCreatingNewThreadPool() throws Exception {

      Logger logger = Logger.getLogger(this.getClass().getName());

      MockRRGenerator mockRRGenerator = new MockRRGenerator();
      mockRRGenerator.GenerateMockRequestResponse(
          "GET", "/stores", token, 200, StoreFakeRR.listAllStoresFakeResponse);

      final int iterations = 50;
      final int initialThreads = countOkHttpThreadPools();
      final int expectedThreads = initialThreads + iterations;

      final UnivapaySDK firstClient = createTestInstance(AuthType.LOGIN_TOKEN);
      List<UnivapaySDK> clients = new ArrayList<>();
      clients.add(firstClient);

      for (Integer i = 0; i < iterations; i++) {
        UnivapaySDK client = firstClient.copy();
        client.listStores().build().dispatch();
        clients.add(client);
      }

      int finalThreads = countOkHttpThreadPools();

      if (finalThreads - initialThreads != (expectedThreads - initialThreads)) {
        fail(
            (finalThreads - initialThreads)
                + " thread pools were created, but "
                + (expectedThreads - initialThreads)
                + " were expected.");
      } else
        logger.log(
            Level.INFO,
            "Exactly "
                + (expectedThreads - initialThreads)
                + " thread pools were created as expected");

      for (UnivapaySDK client : clients) {
        client.close();
      }
    }
  }

  public static int countOkHttpThreadPools() {
    List<Thread> threads = new ArrayList<>();
    for (Thread thread : Thread.getAllStackTraces().keySet()) {
      if (thread.getName().equals("OkHttp ConnectionPool")) {
        threads.add(thread);
      }
    }

    return threads.size();
  }
}
