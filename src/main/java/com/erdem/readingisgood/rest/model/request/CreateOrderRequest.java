package com.erdem.readingisgood.rest.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String customerId;
    @NotEmpty
    private List<@Valid ItemRequest> items;
}
