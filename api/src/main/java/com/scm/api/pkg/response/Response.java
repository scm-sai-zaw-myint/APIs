package com.scm.api.pkg.response;

import java.util.HashMap;
import java.util.Map;

import com.scm.api.pkg.property.ResponseCode;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice.This;

@Data
@Getter
@Setter
public class Response {

    public static Map<?, ?> send(ResponseCode code,boolean status) {
        return resolveResponse(code.getCode(), code.getMessage(), null,status);
    }
    
    public static Map<?,?> send (Object data,ResponseCode code,boolean status){
        return resolveResponse(code.getCode(), code.getMessage(), data,status);
    }

    static Map<String,Object> resolveResponse(Integer code, String message, Object data,boolean status){
        Map<String, Object> response = new HashMap<>();
        response.put("ok", status);
        response.put("code", code);
        response.put("message", message);
        if(data!=null)
            response.put("data", data);
        return response;
    }
    
}
