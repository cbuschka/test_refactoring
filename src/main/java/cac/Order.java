package cac;

import java.time.OffsetDateTime;

public record Order(String orderNo, OffsetDateTime placedAt, OffsetDateTime cancelledAt) {

  public Order cancel(OffsetDateTime cancelledAt) {
    return new Order(this.orderNo, this.placedAt, cancelledAt);
  }
}
