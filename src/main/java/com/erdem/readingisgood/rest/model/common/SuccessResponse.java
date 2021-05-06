package com.erdem.readingisgood.rest.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SuccessResponse<T> extends Response<T> {
}
