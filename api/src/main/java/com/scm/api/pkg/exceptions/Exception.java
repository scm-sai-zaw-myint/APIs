package com.scm.api.pkg.exceptions;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class Exception {

    public static Object parseGoogleException(GoogleJsonResponseException e) {
        try {
            String jsonString = e.getDetails().toPrettyString();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, Map.class);
        } catch (IOException e1) {
            e1.printStackTrace();
            return e.getMessage();
        }
    }
    
}
