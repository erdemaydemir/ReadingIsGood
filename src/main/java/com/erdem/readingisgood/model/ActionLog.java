package com.erdem.readingisgood.model;

import lombok.Data;

@Data
public class ActionLog<T> {

    private long timestamp;
    private String username;

    private ActionLogTypeEnum type;
    private String detail;
    private T relatedObject;

    public enum ActionLogTypeEnum {
        INSERT, UPDATE, DELETE;
    }
}
