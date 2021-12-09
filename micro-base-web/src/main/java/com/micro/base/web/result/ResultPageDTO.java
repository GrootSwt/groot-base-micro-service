package com.micro.base.web.result;

import org.springframework.data.domain.Page;

import java.util.List;

public class ResultPageDTO<T> extends BaseResultDTO {

    private List<T> data;

    private long total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public ResultPageDTO(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultPageDTO(Status status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultPageDTO<Void> failure(String message) {
        return new ResultPageDTO<>(Status.failure, message);
    }


    public static ResultPageDTO<Void> success(String message) {
        return new ResultPageDTO<>(Status.success, message);
    }

    public static <T> ResultPageDTO<T> success(String message, Page<T> data) {
        ResultPageDTO<T> result = new ResultPageDTO<>(Status.success, message);
        return pageToList(result, data);
    }


    public static <T> ResultPageDTO<T> pageToList(ResultPageDTO<T> result, Page<T> data) {
        result.data = data.getContent();
        result.total = data.getTotalElements();
        return result;
    }
}
