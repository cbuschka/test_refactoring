package cac;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderTest {

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ReceiptGenerator receiptGenerator;
  @InjectMocks
  private CancelOrderUseCase cancelOrderUseCase;
  @Captor
  private ArgumentCaptor<Order> orderCaptor;

  @Test
  void shouldCancelOrder() {
    // given is an order not cancelled yet
    Order order = new Order("O1", OffsetDateTime.now(), null, null);
    when(orderRepository.findByOrderNo("O1")).thenReturn(Optional.of(order));
    String nextReceipt = "C1";
    when(receiptGenerator.nextReceipt()).thenReturn(nextReceipt);

    // when the order is cancelled
    OffsetDateTime cancelledAt = OffsetDateTime.now();
    String cancellationReceipt = cancelOrderUseCase.cancel("O1", cancelledAt);

    // then it should be in cancelled state and save
    assertThat(cancellationReceipt).isEqualTo(nextReceipt);
    verify(orderRepository).save(orderCaptor.capture());
    Order orderSaved = orderCaptor.getValue();
    assertThat(orderSaved.cancelledAt()).isEqualTo(cancelledAt);
  }
}