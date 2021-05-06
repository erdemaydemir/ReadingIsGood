package com.erdem.readingisgood.model;

import lombok.Data;

@Data
public class Item {

    private String productId;
    private String barcode;
    private int quantity;
}
