package com.erdem.readingisgood.rest.model.response;

import lombok.Data;

@Data
public class ItemResponse {

    private String productId;
    private String barcode;
    private int quantity;
}
