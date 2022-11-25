package com.scm.api.pkg.resolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldResolver extends Resolver {

    private String fields;
    
    @SuppressWarnings("unchecked")
    public Resolver resolve() {
        return super.resolve((Map<String, Object>) new HashMap<>().put("fields", this.exchange()));
    }
    
    private List<String> exchange(){
        if(this.fields == null) this.fields = "id";
        return Arrays.asList(this.getFields().split(","));
    }
    
    @Override
    public String toJson() {
        return super.toJson();
    }
}
