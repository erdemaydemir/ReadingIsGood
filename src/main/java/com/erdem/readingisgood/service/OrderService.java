package com.erdem.readingisgood.service;

import com.erdem.readingisgood.model.ActionLog;
import com.erdem.readingisgood.model.Order;
import com.erdem.readingisgood.model.Product;
import com.erdem.readingisgood.model.enums.OrderStatusEnum;
import com.erdem.readingisgood.repository.OrderRepository;
import com.erdem.readingisgood.rest.exception.OrderHavingDupItemException;
import com.erdem.readingisgood.rest.exception.ProductOutOfQuantityException;
import com.erdem.readingisgood.rest.exception.ResourceNotFoundException;
import com.erdem.readingisgood.rest.model.request.CreateOrderRequest;
import com.erdem.readingisgood.rest.model.request.ItemRequest;
import com.erdem.readingisgood.rest.model.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ActionLogService actionLogService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final DozerBeanMapper dozerBeanMapper;

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(order -> dozerBeanMapper.map(order, OrderResponse.class)).collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatusEnum status) {
        return orderRepository.findByStatus(status).stream().map(order -> dozerBeanMapper.map(order, OrderResponse.class)).collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(order -> dozerBeanMapper.map(order, OrderResponse.class)).collect(Collectors.toList());
    }

    @Transactional
    public void saveWithRequest(CreateOrderRequest createOrderRequest) {
        checkItems(createOrderRequest);
        checkCustomer(createOrderRequest.getCustomerId());
        Map<String, Product> productMap = checkProductsAndProductQuantities(createOrderRequest);
        createOrderRequest.getItems().forEach(itemRequest -> {
            Product product = productMap.get(itemRequest.getProductId());
            long diffQuantity = product.getQuantity() - itemRequest.getQuantity();
            product.setQuantity(diffQuantity);
            productService.update(product);
        });
        Order order = dozerBeanMapper.map(createOrderRequest, Order.class);
        order.setStatus(OrderStatusEnum.PREPARING);
        order.setTotalPrice(createOrderRequest.getItems().stream().mapToDouble(value -> {
            Product product = productMap.get(value.getProductId());
            return product.getPrice() * value.getQuantity();
        }).sum());
        this.save(order);
    }

    public void updateOrderStatus(String id, OrderStatusEnum status) {
        Order order = orderRepository.findById(id).orElseThrow();
        if (!order.getStatus().equals(status)) {
            order.setStatus(status);
            this.update(order);
        }
    }

    public Order save(Order order) {
        Order savedOrder = orderRepository.save(order);
        actionLogService.save(savedOrder, ActionLog.ActionLogTypeEnum.INSERT, "Order object inserted");
        return savedOrder;
    }

    public Order update(Order order) {
        Order updatedOrder = orderRepository.save(order);
        actionLogService.save(updatedOrder, ActionLog.ActionLogTypeEnum.UPDATE, "Order object update");
        return updatedOrder;
    }

    public void deleteById(String id) {
        orderRepository.deleteById(id);
        actionLogService.save(id, ActionLog.ActionLogTypeEnum.DELETE, "Order object deleted id = " + id);
    }

    private Map<String, Product> checkProductsAndProductQuantities(CreateOrderRequest createOrderRequest) {
        return createOrderRequest.getItems().stream()
                .map(itemRequest -> {
                    Product product = productService.findProductById(itemRequest.getProductId());
                    if (product.getQuantity() < itemRequest.getQuantity()) {
                        throw new ProductOutOfQuantityException();
                    }
                    return product;
                })
                .collect(Collectors.toMap(Product::getId, product -> product));
    }

    private void checkCustomer(String customerId) {
        if (!customerService.existsById(customerId)) {
            throw new ResourceNotFoundException();
        }
    }

    private void checkItems(CreateOrderRequest createOrderRequest) {
        boolean havingDupProductInItems = createOrderRequest.getItems().stream()
                .collect(Collectors.groupingBy(ItemRequest::getProductId, Collectors.counting()))
                .entrySet()
                .stream().anyMatch(count -> count.getValue() > 1);

        if (havingDupProductInItems) {
            throw new OrderHavingDupItemException();
        }
    }
}
