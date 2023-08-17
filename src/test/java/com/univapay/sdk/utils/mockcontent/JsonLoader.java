package com.univapay.sdk.utils.mockcontent;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/** Used in the tests to load a file to be used a mock response */
public class JsonLoader {

  /**
   * Given a resource, return its contents as String
   *
   * @param resource location
   * @return contents as string if found
   * @throws IllegalArgumentException if failed to read
   */
  public static String loadJson(String resource) {

    try {
      final String resourceToLoad;
      if (resource.startsWith("/")) {
        resourceToLoad = resource.replaceFirst("/", "");
      } else {
        resourceToLoad = resource;
      }

      InputStream stream =
          Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceToLoad);

      if (stream == null) {
        throw new IllegalArgumentException("Fail to read resource " + resource);
      }

      final int bufferSize = 1024;
      final char[] buffer = new char[bufferSize];
      final StringBuilder out = new StringBuilder();
      Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
      for (; ; ) {
        int rsz = in.read(buffer, 0, buffer.length);
        if (rsz < 0) break;
        out.append(buffer, 0, rsz);
      }
      return out.toString();
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to read " + resource, e);
    }
  }
}
