package com.erdem.readingisgood.rest.model.response;

import com.erdem.readingisgood.model.enums.ProductTypeEnum;
import lombok.Data;

@Data
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private String barcode;
    private ProductTypeEnum type;
    private double price;
    private long quantity;
}
