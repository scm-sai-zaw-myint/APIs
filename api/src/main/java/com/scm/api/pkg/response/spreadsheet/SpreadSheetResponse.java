package com.scm.api.pkg.response.spreadsheet;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.scm.api.pkg.google.spreadsheet.form.SpreadSheetForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheetResponse {
    
    @JsonInclude(Include.NON_NULL)
    private String sheetId;
    
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    @JsonInclude(Include.NON_NULL)
    private String sheetName;
    
    @JsonInclude(Include.NON_NULL)
    private String range;
    
    @JsonInclude(Include.NON_NULL)
    private List<List<Object>> values;
    
    public SpreadSheetResponse(SpreadSheetForm form) {
        this.name = form.getName();
        this.range = form.getRange();
        this.sheetId = form.getSheetId();
        this.values = form.getValues();
        this.sheetName = form.getSheetName();
    }
}
