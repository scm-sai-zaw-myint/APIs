package com.scm.api.pkg.google.spreadsheet.form;

import com.google.api.services.sheets.v4.model.ValueRange;

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
public class SpreadsheetUpdateForm {

    private String sheetId;
    
    private ValueRange range;
    
}
