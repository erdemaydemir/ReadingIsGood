package com.erdem.readingisgood.repository;

import com.erdem.readingisgood.model.Order;
import com.erdem.readingisgood.model.enums.OrderStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);

    List<Order> findByStatus(OrderStatusEnum status);
}
