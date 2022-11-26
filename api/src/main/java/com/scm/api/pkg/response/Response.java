package com.scm.api.pkg.response;

import java.util.HashMap;
import java.util.Map;

import com.scm.api.pkg.property.ResponseCode;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Response {

    public static Map<?, ?> send(ResponseCode code,boolean status) {
        return resolveResponse(code.getCode(), code.getMessage(), null,status, null);
    }
    
    public static Map<?,?> send(Object data,ResponseCode code,boolean status){
        return resolveResponse(code.getCode(), code.getMessage(), data,status, null);
    }
    
    public static Map<?,?> send(ResponseCode code,boolean status,Object error){
        return resolveResponse(code.getCode(), code.getMessage(), null,status, error);
    }

    static Map<String,Object> resolveResponse(Integer code, String message, Object data,boolean status, Object error){
        Map<String, Object> response = new HashMap<>();
        response.put("ok", status);
        response.put("code", code);
        response.put("message", message);
        if(data!=null)
            response.put("data", data);
        if(error != null)
            response.put("error", error);
        return response;
    }
    
}
