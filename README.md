# UnivaPay Java SDK

UnivaPay Java SDKは、UnivaPay APIと通信するためのメソッドとモデルを提供します。

*[English](README.en.md)*

## インストール

UnivaPay Java SDKを利用するには、pomファイルに次の依存関係を追加します。

```xml
<dependency>
    <groupId>com.univapay</groupId>
    <artifactId>univapay-java-sdk</artifactId>
    <version>0.2.19</version>
</dependency>
```
## 使用方法

アプリケーションJson Webトークン（appJWT）とシークレットを取得し、次のように認証方法を定義します。

```java
AppJWTStrategy authStrategy = new AppJWTStrategy(appJWT, appJWTSecret);
```

エンドポイント(`endpoint`)やリクエストごとのタイムアウト(`timeout`)が有効かについて定義することもできます。

```java
UnivapaySettings settings =  new UnivapaySettings()
                    .withEndpoint(endpoint)
                    .withTimeoutSeconds(timeout)
                    .attachOrigin(MY_ORIGIN_URI);
```

すべてのフィールドは次のデフォルト値を持ちます。

- Endpoint: 環境変数`UNIVAPAY_ENDPOINT`の値、または定義されていない場合は`https://api.univapay.com`
- Timeout: 900秒
- Origin: 設定された場合はSDKのインスタンスからの全てのリクエストに`Origin [引数の値]`というヘッダーが追加される。デフォルト設定では`Origin`ヘッダーが追加されない。

SDKのインスタンスを作成するには、staticなメソッド `create`を以下のように使用します。
```java
UnivapaySDK univapay = UnivapaySDK.create(authStrategy, settings);
```

作成されたインスタンスでは、APIとの通信に使用できるすべてのメソッドにアクセスできます。

また、 `copy`メソッドも用意されています。これにより、異なる認証情報、設定、またはその両方を使用して新しいインスタンスを作成することができます。

たとえば、次のように異なる資格情報を使用して単一の要求を行うことができます。

```java
univapay.copy(newAuthStrategy).listCharges();
```

これは、元の `univapay`インスタンスの認証戦略と設定を変更しません。

## リクエストビルダー

すべてのリクエストメソッドはリクエストビルダーを返します。これにより、任意のパラメータを自由に設定できます。

```java
// ビルダーをインスタンス化
CreateRefundRequestBuilder refundCreationBuilder = univapay.createRefund(storeId, chargeId, BigInteger.valueOf(15), "JPY", RefundReason.CUSTOMER_REQUEST);

// 任意のパラメータを追加
RetrofitRequestCaller<Refund> request = refundCreationBuilder
            .withMetadata(metadata)
            .withMessage("reservation cancelled")
            .build()

// いつでも同期的に通信可能...
Refund refund = request.dispatch();

// または非同期的に通信可能
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

## 課金（Charge）する

まず、アプリケーショントークンとシークレットを入手します。用途に応じて`TEST`モードまたは` LIVE`モードでトークンを使用してください。

SDKインスタンスを取得し、トランザクショントークンを作成します。たとえば、クレジットカードを使用するワンタイムトランザクショントークンの場合は、次のようになります。

```java
CreditCard creditCard = new CreditCard(cardHolder, cardNumber, expMonth, expYear, cvv)
                        .addAddress(country, state, city, line1, line2, postalCode);

TransactionTokenWithData transactionToken = univapay.createTransactionToken(email, creditCard, TransactionTokenType.ONE_TIME)
                                             .build()
                                             .dispatch();
```

その後、決済を行います。

```java
Charge charge = univapay.createCharge(transactionToken.getId(), chargedAmount, requestedCurrency)
                     .withMetadata(metadata)
                     .withIdempotencyKey(idempotencyKey)
                     .build()
                     .dispatch();
```

`withIdempotencyKey（idempotencyKey）`メソッドは、idempotency（冪等）キーをリクエストに付加します。すべての非認証POSTメソッドとPATCHメソッドは、要求にidempotencyキーを添付できます。

`Charge`（課金情報）と、 `UnivapayResponse`のすべてのインスタンスにおいて、` getIdempotencyStatus（） `メソッドを使ってリクエストが冪等であるかを確認することができます。

Chargeの初期ステータスは「未確定（`PENDING`）」です。Chargeの処理が完了するまで待ち、成功したかどうかに応じて対処することができます。
この場合、以下のように`ResourceMonitor`を使用することで、最後のステータスを取得して返すまで、Chargeのステータスをスマートにポーリングすることができます。

```java
Charge polledCharge = univapay.chargeCompletionMonitor(charge.getStoreId(), charge.getId()).await()
```

### キャプチャと認証

デフォルトでは、 `createCharge`メソッドは支払いまで処理を進めます（capture)。そうではなく、支払処理に権限を付与する必要がある場合は、キャプチャするかどうかを示す第4引数を渡します。
たとえば、 `univapay.createCharge（transactionToken.getId（）, chargedAmount, requestedCurrency, false）`とした場合は、請求を許可するだけです。

これを取得するには、対応する `StoreId`と` ChargeId`を渡して `captureAuthorizedCharge`メソッドを使用できます。

### キャンセル

承認された請求は、以下の方法でキャンセルすることができます。

```java
univapay.createCancel(storeId, authorizedCharge.getId())
     .withMetadata(metadata)
     .build()
     .dispatch
```

### 課金（Charge）のバッチ作成


SDKは、APIの制限を超過することなく大量の料金を作成するためのユーティリティメソッドを提供し、そのような例外を処理する苦労を軽減します。

まず、バッチ用インスタンスを作成します。

```java
BatchCreateCharge batchCharger = univapay.batchCreateCharge();
```

次に、作成しようとしている課金に必要なトランザクショントークンを取得し、それぞれに対して`CreateChargeBuilder`を追加します。

```java
for (TransactionToken transactionToken: transactionTokens) {
    batchCharger.add(univapay.createCharge(transactionToken.getId(), amount, currency));
}
```
最後に、バッチ作成を実行します。このとき、作成されたChargeの配列が返されます。このメカニズムはChargeのステータスをポーリングします。

```java
CreateChargeResult[] chargeResults = batchCharger.execute();
```
すべての課金は、バッチ処理の際に冪等に作成されます。

## 改ページ用リストと反復処理

UnivaPay Java SDKには、結果リストを反復処理するメソッドも用意されています。

たとえば、いくつかの検索条件を満たすマーチャントの課金一覧が必要な場合は、次のようにします。

```java
ListChargesRequestBuilder builder = payments.listCharges()
                                            .withCardChargeSearch(cardChargeSearch)
                                            .setLimit(15)
                                            .setCursorDirection(CursorDirection.DESC)
                                            .setCursor(new ChargeId("8486dc98-9836-41dd-b598-bbf49d5bc862"));
```

`limit`は1ページあたりの最大項目数を設定します。 `cursor direction`は、降順または昇順でソートされた項目を返すようにAPIに指示します。`cursor`は指定されたIDから結果を返すようにAPIに要求します。

これにより、改ページ用のリクエストビルダーが返されます。それを`build()`、`dispatch()`することで、 `PaginatedList <Charge>`が返されます。

このリストの結果を使用して、次のレスポンスをナビゲートすることができます。しかしこの場合、`TOO_MANY_REQUESTS`エラーを自分で処理する必要があります。

より良い方法は、`PaginatedList`をJavaの`Iterable`にすることです。

```java
PaginatedListIterable<Charge> chargesIterable = builder.asIterable();

for(List<Charge> charges: chargesIterable){
    doSomething(charges);
}
```

基礎となるイテレータは、APIのリクエスト制限を考慮し、バックオフメカニズムを使用して失敗したリクエストを再試行します。
`asIterable`メソッドは、` timeout`と `backoff`アルゴリズムをカスタマイズする方法を提供します。より詳細については、JavaDocを参照してください。

## その他

UnivaPay APIの詳細については、[Javadoc](https://www.javadoc.io/doc/com.univapay/univapay-java-sdk/0.2.19)または[APIリファレンス](https://docs.univapay.com)を参照してください。
