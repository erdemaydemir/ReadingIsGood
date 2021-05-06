package com.erdem.readingisgood.rest.controller;

import com.erdem.readingisgood.model.enums.OrderStatusEnum;
import com.erdem.readingisgood.rest.model.common.Response;
import com.erdem.readingisgood.rest.model.request.CreateOrderRequest;
import com.erdem.readingisgood.rest.model.response.OrderResponse;
import com.erdem.readingisgood.rest.util.ResponseDispatcher;
import com.erdem.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Response<List<OrderResponse>> getOrders() {
        return ResponseDispatcher.successResponse(orderService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public Response<List<OrderResponse>> getOrdersByStatus(@RequestParam OrderStatusEnum status) {
        return ResponseDispatcher.successResponse(orderService.getOrdersByStatus(status), HttpStatus.OK);
    }

    @PostMapping
    public Response<Void> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        orderService.saveWithRequest(createOrderRequest);
        return ResponseDispatcher.successResponse(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/status")
    public Response<Void> createOrder(@PathVariable String id, @RequestParam @Valid OrderStatusEnum status) {
        orderService.updateOrderStatus(id, status);
        return ResponseDispatcher.successResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteById(id);
        return ResponseDispatcher.successResponse(HttpStatus.OK);
    }
}
