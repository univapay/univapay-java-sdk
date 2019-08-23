package com.univapay.sdk.settings;

import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.utils.RefreshLoginJWTCallback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * SDK settings, including the following:
 *
 * <p>endpoint: Endpoint to where requests are directed. Defaults to the value of the environment
 * variable <code>UNIVAPAY_ENDPOINT</code> or to <code>"https://api.univapay.com"</code> if not set.
 * timeoutSeconds: Set the number of seconds allowed to pass before raising a connection timeout
 * error or read timeout error. Defaults to 900 seconds (15 mins). origin Value of the Origin header
 */
public abstract class AbstractSDKSettings<T extends AbstractSDKSettings> {
  private static final String DEFAULT_ENDPOINT = "https://api.univapay.com";
  private static final String GOPAY_ENDPOINT_ENVVAR = "GOPAY_ENDPOINT";
  private static final String UNIVAPAY_ENDPOINT_ENVVAR = "UNIVAPAY_ENDPOINT";
  private static final Long DEFAULT_TIMEOUT = 900L;
  private String legacyEndpointFromEnvvar = System.getenv(GOPAY_ENDPOINT_ENVVAR);
  private String univapayEndpointFromEnvvar = System.getenv(UNIVAPAY_ENDPOINT_ENVVAR);
  private String endpointFromEnvvar =
      (univapayEndpointFromEnvvar != null) ? univapayEndpointFromEnvvar : legacyEndpointFromEnvvar;

  protected String endpoint = (endpointFromEnvvar != null) ? endpointFromEnvvar : DEFAULT_ENDPOINT;
  protected long timeout = DEFAULT_TIMEOUT;
  protected String origin = "";
  protected RefreshLoginJWTCallback refreshLoginJWTCallback;

  /**
   * The API endpoint
   *
   * @param endpoint
   * @return an instance of T
   */
  public T withEndpoint(String endpoint) {
    this.endpoint = endpoint;
    return (T) this;
  }

  /**
   * The timeout for connecting, reading and writing
   *
   * @param timeoutSeconds
   * @return an instance of T
   */
  public T withTimeoutSeconds(long timeoutSeconds) {
    this.timeout = timeoutSeconds;
    return (T) this;
  }

  /**
   * Attach a value to the Origin header, which is added to every request sent by the SDK.
   *
   * @param origin
   * @return an instance of T
   */
  public T attachOrigin(String origin) {
    this.origin = origin;
    return (T) this;
  }

  /**
   * The callback when token is refreshed
   *
   * @param refreshLoginJWTCallback RefreshLoginJWTCallback
   * @return an instans of T
   */
  public T withRefreshLoginJWTCallback(final RefreshLoginJWTCallback refreshLoginJWTCallback) {
    this.refreshLoginJWTCallback = refreshLoginJWTCallback;
    return (T) this;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public long getTimeout() {
    return timeout;
  }

  public String getOrigin() {
    return origin;
  }

  public RefreshLoginJWTCallback getRefreshLoginJWTCallback() {
    return refreshLoginJWTCallback;
  }

  /**
   * Creates a copy of this instance.
   *
   * @return an instance of T
   */
  public abstract T copy();

  /**
   * @param authStrategy the authentication strategy
   * @return an OkHttpClient to be used for performing requests
   * @see OkHttpClient
   */
  public abstract OkHttpClient getClient(AuthStrategy authStrategy);

  /**
   * @param authStrategy the authentication strategy
   * @return an OkHttpClient to be used for performing requests
   * @see OkHttpClient
   */
  public abstract OkHttpClient getClient(AuthStrategy authStrategy, ConnectionPool connectionPool);
}
