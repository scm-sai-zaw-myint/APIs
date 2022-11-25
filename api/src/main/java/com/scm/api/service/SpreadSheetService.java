package com.scm.api.service;

import java.util.Map;

import com.google.api.services.sheets.v4.Sheets;
import com.scm.api.bl.dto.spreadsheet.SpreadSheetDTO;

public interface SpreadSheetService {
    
    public Object getAPIDocumentation();

    public Object resolveRequest(String sheetId,String range, String name, String fields);
    
    public Sheets getCreditential();
    
    public Map<Object,Object> getSheetData(String sheetId, String range);
    
    public String createSheet(SpreadSheetDTO spreadsheet);
    
}
