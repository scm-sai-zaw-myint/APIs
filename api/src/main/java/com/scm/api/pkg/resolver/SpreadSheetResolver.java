package com.scm.api.pkg.resolver;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheetResolver extends Resolver {
    private String sheedId;
    private String name;
    private String range;
    
    public Resolver resolve() {
        return super.resolve(this.exchange());
    }
    private Map<String, Object> exchange() {
        Map<String, Object> map = new HashMap<>();
        map.put("sheetId", this.sheedId);
        String range = this.range == null ? (this.name != null ? this.name + "!" : "") + "A1:E5"
                : (this.name != null ? this.name + "!" : "") + this.range;
        map.put("range", range);
        return map;
    }
    
    @Override
    public String toJson() {
        return super.toJson();
    }
}
