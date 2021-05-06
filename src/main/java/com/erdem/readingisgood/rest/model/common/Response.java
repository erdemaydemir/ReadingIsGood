package com.erdem.readingisgood.rest.model.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Response<T> {
    private int code;
    private T body;
}
