package com.trendyol.svc.shopping.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductIsEmptyException extends GlobalResponseException {

    public ProductIsEmptyException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
