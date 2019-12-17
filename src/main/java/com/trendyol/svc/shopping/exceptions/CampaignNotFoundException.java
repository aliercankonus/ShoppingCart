package com.trendyol.svc.shopping.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CampaignNotFoundException extends GlobalResponseException {

    public CampaignNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }
}
