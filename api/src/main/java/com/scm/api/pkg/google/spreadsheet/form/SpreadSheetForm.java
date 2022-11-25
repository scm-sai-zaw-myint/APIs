package com.scm.api.pkg.google.spreadsheet.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SpreadSheetForm {

    private String sheetId;
    private String name;
    private String range;
    
}
