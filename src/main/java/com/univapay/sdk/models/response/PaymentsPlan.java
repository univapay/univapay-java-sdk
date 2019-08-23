package com.univapay.sdk.models.response;

import java.util.ArrayList;
import java.util.Iterator;
import com.univapay.sdk.models.response.subscription.SimulatedPayment;

public class PaymentsPlan extends UnivapayResponse implements Iterable<SimulatedPayment> {

  private ArrayList<SimulatedPayment> items;

  public PaymentsPlan(ArrayList<SimulatedPayment> items) {
    this.items = items;
  }

  public ArrayList<SimulatedPayment> getItems() {
    return items;
  }

  @Override
  public Iterator<SimulatedPayment> iterator() {
    return items.iterator();
  }
}
