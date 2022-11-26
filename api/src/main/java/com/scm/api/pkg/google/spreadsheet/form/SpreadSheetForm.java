package com.scm.api.pkg.google.spreadsheet.form;

import java.util.List;

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
public class SpreadSheetForm {

    private String sheetId;

    private String name;

    private String sheetName;

    private String range;

    private List<List<Object>> values;

}
