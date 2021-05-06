package com.erdem.readingisgood.rest.util;

import com.erdem.readingisgood.rest.model.common.ErrorResponse;
import com.erdem.readingisgood.rest.model.common.Response;
import com.erdem.readingisgood.rest.model.common.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ResponseDispatcher {

    public static <T> Response<T> successResponse(T body, HttpStatus httpStatus) {
        return SuccessResponse.<T>builder()
                .body(body)
                .code(httpStatus.value())
                .build();
    }

    public static <T> Response<T> successResponse(HttpStatus httpStatus) {
        return SuccessResponse.<T>builder()
                .body(null)
                .code(httpStatus.value())
                .build();
    }

    public static <T> Response<T> failureResponse(HttpStatus httpStatus, String exception, String failedMessage, T body) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return ErrorResponse.<T>builder()
                .body(body)
                .exception(exception)
                .failedMessage(failedMessage)
                .requestPath(request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI).toString())
                .code(httpStatus.value())
                .build();
    }

    public static <T> Response<T> failureResponse(HttpStatus httpStatus, String exception, String failedMessage, T body, List<String> validations) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return ErrorResponse.<T>builder()
                .body(body)
                .validations(validations)
                .exception(exception)
                .failedMessage(failedMessage)
                .requestPath(request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI).toString())
                .code(httpStatus.value())
                .build();
    }
}
