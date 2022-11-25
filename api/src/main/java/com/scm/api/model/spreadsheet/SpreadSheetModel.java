package com.scm.api.model.spreadsheet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpreadSheetModel {
    private String name;
    private List<?> columns;
}
