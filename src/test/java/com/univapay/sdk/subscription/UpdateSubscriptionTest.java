package com.univapay.sdk.subscription;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.request.subscription.FixedCycleInstallmentsPlan;
import com.univapay.sdk.models.request.subscription.RemoveInstallmentsPlan;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class UpdateSubscriptionTest extends GenericTest {

  @Test
  public void shouldPostAndReturnUpdatedSubscriptionInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e821e9-806e-7a06-a00c-fb1ee377211d/subscriptions/11e821e9-8078-c3bc-851f-8b3cff59a635",
        jwt,
        200,
        "{}",
        ChargesFakeRR.updateSubscriptionFakeRequest);

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("name", "test-name");
    requestMetadata.put("value", "1234.7981723987");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("7f5eecc8-3b38-4cec-86bb-644af74cb186");

    final LocalDate startOn = LocalDate.parse("2018-09-07");
    final BigInteger initialAmount = BigInteger.valueOf(2000);

    univapay
        .updateSubscription(
            new StoreId("11e821e9-806e-7a06-a00c-fb1ee377211d"),
            new SubscriptionId("11e821e9-8078-c3bc-851f-8b3cff59a635"))
        .withTransactionToken(transactionTokenId)
        .withMetadata(requestMetadata)
        .withPeriod(SubscriptionPeriod.BIWEEKLY)
        .withInitialAmount(initialAmount)
        .withStartOn(startOn)
        .withPreserveEndOfMonth(true)
        .withOnlyDirectCurrency(true)
        .withStatus(SubscriptionStatus.UNPAID)
        .withDescriptor("test descriptor")
        .withInstallmentPlan(new FixedCycleInstallmentsPlan(10))
        .build()
        .dispatch();
  }

  @Test
  public void shouldSerializeCorrectlyRequestsToRemoveInstallmentsPlan()
      throws UnivapayException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e821e9-806e-7a06-a00c-fb1ee377211d/subscriptions/11e821e9-8078-c3bc-851f-8b3cff59a635",
        jwt,
        200,
        "{}",
        ChargesFakeRR.removeInstallmentsPlanFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("7f5eecc8-3b38-4cec-86bb-644af74cb186");

    univapay
        .updateSubscription(
            new StoreId("11e821e9-806e-7a06-a00c-fb1ee377211d"),
            new SubscriptionId("11e821e9-8078-c3bc-851f-8b3cff59a635"))
        .withTransactionToken(transactionTokenId)
        .withInstallmentPlan(new RemoveInstallmentsPlan())
        .build()
        .dispatch();
  }
}
