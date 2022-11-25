package com.scm.api.pkg.spreadsheet;

import java.util.List;

import lombok.Data;

@Data
public class SpreadSheet {

    private String name;
    
    private List<Object> columns;
    
    public SpreadSheet() {
        super();
    }
    
    public SpreadSheet(String name, List<Object> columns) {
        this.name = name;
        this.columns = columns;
    }
    
    public SpreadSheet(String name) {
        this.name = name;
    }

    public SpreadSheet name(String name) {
        this.name = name;
        return this;
    }

    public SpreadSheet columns(List<Object> columns) {
        this.columns = columns;
        return this;
    }
    
//    public SpreadSheet getData(SpreadSheet sheet) {
//        
//    }
}
