package cac;

import java.time.OffsetDateTime;

public record Order(String orderNo, OffsetDateTime placedAt, OffsetDateTime cancelledAt, String cancellationReceipt) {

  public Order cancel(OffsetDateTime cancelledAt, String receipt) {
    return new Order(this.orderNo, this.placedAt, cancelledAt, receipt);
  }
}
