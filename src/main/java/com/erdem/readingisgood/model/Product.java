package com.erdem.readingisgood.model;

import com.erdem.readingisgood.model.enums.ProductTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private String barcode;
    private ProductTypeEnum type;
    private double price;
    private long quantity;
}
