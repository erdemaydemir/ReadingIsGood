package com.erdem.readingisgood.rest.controller;

import com.erdem.readingisgood.rest.model.common.Response;
import com.erdem.readingisgood.rest.model.request.CreateCustomerRequest;
import com.erdem.readingisgood.rest.model.response.CustomerResponse;
import com.erdem.readingisgood.rest.model.response.OrderResponse;
import com.erdem.readingisgood.rest.util.ResponseDispatcher;
import com.erdem.readingisgood.service.CustomerService;
import com.erdem.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @GetMapping
    public Response<List<CustomerResponse>> getOrders() {
        return ResponseDispatcher.successResponse(customerService.getCustomers(), HttpStatus.OK);
    }

    @PostMapping
    public Response<Void> createCustomer(@RequestBody @Valid CreateCustomerRequest customerCreateRequest) {
        customerService.saveWithRequest(customerCreateRequest);
        return ResponseDispatcher.successResponse(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteById(id);
        return ResponseDispatcher.successResponse(HttpStatus.OK);
    }

    @PostMapping("/{id}/orders")
    public Response<List<OrderResponse>> createCustomer(@PathVariable String id) {
        return ResponseDispatcher.successResponse(orderService.getOrdersByCustomerId(id), HttpStatus.CREATED);
    }
}
