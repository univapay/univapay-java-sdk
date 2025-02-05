# UnivaPay Java SDK

The UnivaPay Java SDK provides methods and models to interact with the UnivaPay API.

_Read this in other language: [日本語](README.md)_

## Installing

To use the UnivaPay Java SDK, add the following dependency to your pom file:

```xml
<dependency>
    <groupId>com.univapay</groupId>
    <artifactId>univapay-java-sdk</artifactId>
    <version>0.2.39</version>
</dependency>
```

## Usage

Obtain an application Json Web Token (appJWT) and secret and define your authentication strategy in the following way:

```java
AppJWTStrategy authStrategy = new AppJWTStrategy(appJWT, appJWTSecret);
```

You may also define the sdk settings, such as the `endpoint`, the `timeout` in seconds per request and attach an Origin header:

```java
UnivapaySettings settings =  new UnivapaySettings()
                    .withEndpoint(endpoint)
                    .withTimeoutSeconds(timeout)
                    .attachOrigin(MY_ORIGIN_URI)
```

All fields are optional and have the following default values:

- Endpoint: the value of the `UNIVAPAY_ENDPOINT` environment variable, or `https://api.univapay.com` if it's not defined.
- Timeout: the read/write/connection timeout. Defaults to 900 seconds.
- Origin: the value for the Origin header which is attached to every request sent by the SDK. By default, no header is attached.

To create an instance of the SDK, you may use the `create` static method in the following way:

```java
UnivapaySDK univapay = UnivapaySDK.create(authStrategy, settings);
```

The created instance gives you access to all the methods available for communicating with the API.

It also provides the `copy` method, which allows you to create a new instance using different auth credentials, settings or both.

For example, you could make a single request with a different set of credentials in the following way:

```java
univapay.copy(newAuthStrategy).listCharges();
```

This does not mutate the auth strategy and settings of the original `univapay` instance.

## Request Builders

All request methods return a request builder, which allows setting optional parameters in a fluent way:

```java
// Instantiate the builder:
CreateRefundRequestBuilder refundCreationBuilder = univapay.createRefund(storeId, chargeId, BigInteger.valueOf(15), "JPY", RefundReason.CUSTOMER_REQUEST);

// Add the optional parameters
RetrofitRequestCaller<Refund> request = refundCreationBuilder
            .withMetadata(metadata)
            .withMessage("reservation cancelled")
            .build()

// You may dispatch at any moment in a synchronous way...
Refund refund = request.dispatch();

// or in an asynchronous way:
request.dispatch(new UnivapayCallback<Refund>() {
            @Override
            public void getResponse(Refund response) {
                logger.log(Level.INFO, "It succeeded!");
            }

            @Override
            public void getFailure(Throwable error) {
                logger.log(Level.SEVERE, "oh no!");
            }
        });
```

## Making Charges

First, obtain your application JWT and Secret. Remember to use a token in the mode that fits your use case (`TEST`, or `LIVE` mode).

Obtain an SDK instance and create a transaction token. For example, for a one-time transaction token using a credit card:

```java
CreditCard creditCard = new CreditCard(cardHolder, cardNumber, expMonth, expYear, cvv)
                        .addAddress(country, state, city, line1, line2, postalCode);

TransactionTokenWithData transactionToken = univapay.createTransactionToken(email, creditCard, TransactionTokenType.ONE_TIME)
                                             .build()
                                             .dispatch();
```

Then, proceed to make the payment:

```java
Charge charge = univapay.createCharge(transactionToken.getId(), chargedAmount, requestedCurrency)
                     .withMetadata(metadata)
                     .withIdempotencyKey(idempotencyKey)
                     .build()
                     .dispatch();
```

The `withIdempotencyKey(idempotencyKey)` method attaches an idempotency key to the request. All non-authentication POST and PATCH request methods can attach an idempotency key to the request.

For charges, as well as for all instances of `UnivapayResponse`, you can verify the idempotency status of the request by using the `getIdempotencyStatus()` method.

The initial status of a charge is `PENDING`. You might be interested in waiting until the charge's status is fully resolved and take action depending on whether it fails or succeeds.
In that case, you may use a `ResourceMonitor` which smartly polls the charge's status until it achieves a terminal status and returns it:

```java
Charge polledCharge = univapay.chargeCompletionMonitor(charge.getStoreId(), charge.getId()).await()
```

### Capture and Auth

By default, the `createCharge` method captures the charge. If you need to authorize it instead, you can do so passing a fourth argument indicating whether you wish to capture it or not.
For example, `univapay.createCharge(transactionToken.getId(), chargedAmount, requestedCurrency, false)` would only authorize the charge.

To capture it, you can use the `captureAuthorizedCharge` method by passing the corresponding `StoreId` and `ChargeId`.

### Cancels

You can cancel an authorized charge in the following way:

```java
univapay.createCancel(storeId, authorizedCharge.getId())
     .withMetadata(metadata)
     .build()
     .dispatch
```

### Batch creation of charges

The SDK provides utility methods to create a large number of charges without overwhelming the limits of the API, saving you the pain of handling such exceptions.

First, create a batch charger:

```java
BatchCreateCharge batchCharger = univapay.batchCreateCharge();
```

Obtain the necessary transaction tokens for the charges you intend to make and for each one add a `CreateChargeBuilder`:

```java
for (TransactionToken transactionToken: transactionTokens) {
    batchCharger.add(univapay.createCharge(transactionToken.getId(), amount, currency));
}
```

Finally, execute the batch charge creation, which returns an array of charges. The batch mechanism polls the status of the charges for you:

```java
CreateChargeResult[] chargeResults = batchCharger.execute();
```

All the charges are made idempotently when batching.

## Paginated Lists and Iterables

The UnivaPay Java SDK also provides methods to iterate through a list of results.

For example, if you needed a list of the merchant's charges satisfying some search criteria, you would use:

```java
ListChargesRequestBuilder builder = payments.listCharges()
                                            .withCardChargeSearch(cardChargeSearch)
                                            .setLimit(15)
                                            .setCursorDirection(CursorDirection.DESC)
                                            .setCursor(new ChargeId("8486dc98-9836-41dd-b598-bbf49d5bc862"));
```

The `limit` sets the maximum number of items per page. The `cursor direction` tells the API to return the items sorted in descending or ascending order.
Finally, the `cursor` asks the API to return results from a given ID.

This returns a paginated request builder. You may build and dispatch it, which returns a `PaginatedList<Charge>`.

You may use the results from this paginated list to navigate through the following responses. However, this leaves it up to you to handle `TOO_MANY_REQUESTS` errors.

A better alternative is to turn the paginated list into a Java `Iterable`:

```java
PaginatedListIterable<Charge> chargesIterable = builder.asIterable();

for(List<Charge> charges: chargesIterable){
    doSomething(charges);
}
```

The underlying iterator takes into account the API's request limits and retries failed requests with a backoff mechanism.
The `asIterable` method provides ways to customize the `timeout` and `backoff` algorithm. For further reference, check the java docs.

## More

For more details on the UnivaPay API, check the [Javadocs](https://www.javadoc.io/doc/com.univapay/univapay-java-sdk/0.2.39) or the [API Docs](https://docs.univapay.com).
