package cac;

import java.util.Optional;

public interface OrderRepository {
  Optional<Order> findByOrderNo(String orderNo);

  void save(Order order);
}
