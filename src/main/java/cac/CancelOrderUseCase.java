package cac;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CancelOrderUseCase  {
  private final OrderRepository orderRepository;
  private final ReceiptGenerator receiptGenerator;

  public String cancel(String orderNo, OffsetDateTime cancelledAt) {
    Order order = orderRepository.findByOrderNo(orderNo).orElseThrow(
        () -> new NoSuchElementException("No order with orderNo=%s.".formatted(orderNo)));

    String receipt = receiptGenerator.nextReceipt();
    Order cancelledOrder = order.cancel(cancelledAt, receipt);

    orderRepository.save(cancelledOrder);

    return cancelledOrder.cancellationReceipt();
  }
}
