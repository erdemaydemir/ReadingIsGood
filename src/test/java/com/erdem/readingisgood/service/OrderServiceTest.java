package com.erdem.readingisgood.service;

import com.erdem.readingisgood.model.Item;
import com.erdem.readingisgood.model.Order;
import com.erdem.readingisgood.model.Product;
import com.erdem.readingisgood.model.enums.OrderStatusEnum;
import com.erdem.readingisgood.model.enums.ProductTypeEnum;
import com.erdem.readingisgood.repository.OrderRepository;
import com.erdem.readingisgood.rest.exception.OrderHavingDupItemException;
import com.erdem.readingisgood.rest.exception.ProductOutOfQuantityException;
import com.erdem.readingisgood.rest.exception.ResourceNotFoundException;
import com.erdem.readingisgood.rest.model.request.CreateOrderRequest;
import com.erdem.readingisgood.rest.model.request.ItemRequest;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ActionLogService actionLogService;
    @Mock
    private ProductService productService;
    @Mock
    private DozerBeanMapper dozerBeanMapper;

    private CreateOrderRequest createOrderRequest;

    private static Product product;

    @BeforeAll
    static void before() {
        product = new Product();
        product.setId("1");
        product.setQuantity(100);
        product.setBarcode("1");
        product.setPrice(10);
        product.setName("TEST");
        product.setType(ProductTypeEnum.BOOK);
    }

    @Test
    void saveWithRequest_success() {
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId("1");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setProductId("1");
        itemRequest.setQuantity(1);
        itemRequest.setBarcode("1");
        createOrderRequest.setItems(List.of(itemRequest));

        Order order = new Order();
        order.setStatus(OrderStatusEnum.PREPARING);
        order.setCustomerId("1");
        Item item = new Item();
        item.setProductId("1");
        item.setQuantity(1);
        item.setBarcode("1");
        order.setItems(List.of(item));

        when(customerService.existsById(any())).thenReturn(true);
        when(productService.findProductById(any())).thenReturn(product);
        when(dozerBeanMapper.map(any(CreateOrderRequest.class), any())).thenReturn(order);

        orderService.saveWithRequest(createOrderRequest);
        assertEquals(10, order.getTotalPrice());
    }

    @Test
    void saveWithRequest_throwResourceNotFoundException1() {
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId("1");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setProductId("1");
        itemRequest.setQuantity(1);
        itemRequest.setBarcode("1");
        createOrderRequest.setItems(List.of(itemRequest));

        when(customerService.existsById(any())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderService.saveWithRequest(createOrderRequest));
    }

    @Test
    void saveWithRequest_throwResourceNotFoundException2() {
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId("1");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setProductId("1");
        itemRequest.setQuantity(1);
        itemRequest.setBarcode("1");
        createOrderRequest.setItems(List.of(itemRequest));

        when(customerService.existsById(any())).thenReturn(true);
        when(productService.findProductById(any())).thenThrow(new ResourceNotFoundException());

        assertThrows(ResourceNotFoundException.class, () -> orderService.saveWithRequest(createOrderRequest));
    }

    @Test
    void saveWithRequest_throwProductOutOfQuantityException() {
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId("1");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setProductId("1");
        itemRequest.setQuantity(200);
        itemRequest.setBarcode("1");
        createOrderRequest.setItems(List.of(itemRequest));

        when(customerService.existsById(any())).thenReturn(true);
        when(productService.findProductById(any())).thenReturn(product);

        assertThrows(ProductOutOfQuantityException.class, () -> orderService.saveWithRequest(createOrderRequest));
    }

    @Test
    void saveWithRequest_throwOrderHavingDupItemException() {
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId("1");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setProductId("1");
        itemRequest.setQuantity(1);
        itemRequest.setBarcode("1");
        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setProductId("1");
        itemRequest2.setQuantity(10);
        itemRequest2.setBarcode("1");
        createOrderRequest.setItems(List.of(itemRequest, itemRequest2));

        Order order = new Order();
        order.setStatus(OrderStatusEnum.PREPARING);
        order.setCustomerId("1");
        Item item = new Item();
        item.setProductId("1");
        item.setQuantity(1);
        item.setBarcode("1");
        order.setItems(List.of(item));

        assertThrows(OrderHavingDupItemException.class, () -> orderService.saveWithRequest(createOrderRequest));
    }
}