package com.erdem.readingisgood.rest.model.request;

import com.erdem.readingisgood.model.enums.ProductTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateOrUpdateProductRequest {

    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String barcode;
    @NotNull
    private ProductTypeEnum type;
    private double price;
    private long quantity;
}
