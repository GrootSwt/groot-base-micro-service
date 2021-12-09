package com.micro.base.web.result;

public abstract class BaseResultDTO {

    protected Status status;

    protected String message;

    public enum Status {
        success,failure;
    }
}
