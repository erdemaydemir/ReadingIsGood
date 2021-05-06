package com.erdem.readingisgood.model;

import com.erdem.readingisgood.model.enums.OrderStatusEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Order {

    @Id
    private String id;
    private String customerId;
    private List<Item> items;
    private OrderStatusEnum status;

    private double totalPrice;
}
