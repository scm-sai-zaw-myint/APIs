package com.scm.api.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import com.google.api.services.sheets.v4.Sheets;
import com.scm.api.pkg.google.spreadsheet.form.SpreadSheetForm;

public interface SpreadSheetService {
    
    public Object getAPIDocumentation();

    public Object resolveRequest(String sheetId,String range, String name, String fields);
    
    public Sheets getCreditential() throws IOException, GeneralSecurityException;
    
    public Map<Object,Object> getSheetData(String sheetId, String range);
    
    public Object createSpreadSheet(SpreadSheetForm spreadsheet);

    public Object updateSpreadSheet(String sheetId, SpreadSheetForm form);
    
}
