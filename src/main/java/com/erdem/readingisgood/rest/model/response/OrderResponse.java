package com.erdem.readingisgood.rest.model.response;

import com.erdem.readingisgood.model.enums.OrderStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private String id;
    private String customerId;
    private List<ItemResponse> items;
    private OrderStatusEnum status;

    private double totalPrice;
}
