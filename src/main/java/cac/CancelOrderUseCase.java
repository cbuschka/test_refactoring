package cac;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CancelOrderUseCase  {
  private final OrderRepository orderRepository;

  public void cancel(String orderNo, OffsetDateTime cancelledAt) {
    Order order = orderRepository.findByOrderNo(orderNo).orElseThrow(
        () -> new NoSuchElementException("No order with orderNo=%s.".formatted(orderNo)));

    Order cancelledOrder = order.cancel(cancelledAt);

    orderRepository.save(cancelledOrder);
  }
}
