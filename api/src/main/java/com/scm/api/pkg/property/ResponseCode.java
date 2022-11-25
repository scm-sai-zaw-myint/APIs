package com.scm.api.pkg.property;

import lombok.Getter;

@Getter
public enum ResponseCode {

    EMPTY("Empty Response",204),
    ERROR("Error Response",500),
    SUCCESS("Get data success",200),
    REQUIRED("Missing required field!",450);

    private final String message;
    private final Integer code;
    
    ResponseCode(final String string,final Integer code) {
        this.message = string;
        this.code = code;
    }
}
