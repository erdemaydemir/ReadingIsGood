package com.erdem.readingisgood.rest.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ErrorResponse<T> extends Response<T> {
    private String failedMessage;
    private String requestPath;
    private String exception;
    private List<String> validations;
}
