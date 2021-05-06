package com.erdem.readingisgood.rest.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ItemRequest {

    @NotBlank
    private String productId;

    @NotBlank
    private String barcode;

    @Min(1)
    private int quantity;
}
