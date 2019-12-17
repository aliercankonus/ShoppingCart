package com.trendyol.svc.shopping.exceptions;

import lombok.Data;

@Data
public class GlobalResponseException extends RuntimeException{
    private final int id;
    private final String message;

    public GlobalResponseException(int id, String message){
        super(message);
        this.id = id;
        this.message = message;
    }
}
