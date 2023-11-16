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

  private static final String NEXT_RECEIPT = "C1";
  private static final String ORDER_NO = "O1";
  private static final OffsetDateTime CANCELLED_AT = OffsetDateTime.now();
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ReceiptGenerator receiptGenerator;
  @Captor
  private ArgumentCaptor<Order> orderCaptor;
  @InjectMocks
  private CancelOrderUseCase cancelOrderUseCase;
  private Order givenOrder;
  private String resultingCancellationReceipt;

  @Test
  void shouldCancelOrder() {
    givenIsAnOrderNotCancelledYet();
    givenIsAnreceiptForCancellation();

    whenCancellationIsRequested();

    thenTheCancellationReceiptIsReturned();
    thenTheOrderWasSaved();
    thenTheSavedOrderWasCancelled();
  }

  private void thenTheCancellationReceiptIsReturned() {
    assertThat(resultingCancellationReceipt).isEqualTo(NEXT_RECEIPT);
  }

  private void thenTheSavedOrderWasCancelled() {
    Order orderSaved = orderCaptor.getValue();
    assertThat(orderSaved.cancelledAt()).isEqualTo(CANCELLED_AT);
  }

  private void thenTheOrderWasSaved() {
    verify(orderRepository).save(orderCaptor.capture());
  }

  private void whenCancellationIsRequested() {
    resultingCancellationReceipt = cancelOrderUseCase.cancel(ORDER_NO, CANCELLED_AT);
  }

  private void givenIsAnreceiptForCancellation() {
    when(receiptGenerator.nextReceipt()).thenReturn(NEXT_RECEIPT);
  }

  private void givenIsAnOrderNotCancelledYet() {
    givenOrder = new Order(ORDER_NO, OffsetDateTime.now(), null, null);
    when(orderRepository.findByOrderNo(ORDER_NO)).thenReturn(Optional.of(givenOrder));
  }
}