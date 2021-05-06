package com.erdem.readingisgood.rest.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCustomerRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private int age;
}
