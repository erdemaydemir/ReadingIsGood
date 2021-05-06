package com.erdem.readingisgood.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {

    @Id
    private String id;
    private String name;
    private String surname;
    private int age;
}
