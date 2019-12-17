package com.trendyol.svc.shopping.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends GlobalResponseException {

    public CategoryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }
}
