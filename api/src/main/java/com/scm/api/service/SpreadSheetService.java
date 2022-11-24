package com.scm.api.service;

import java.util.Map;

import com.google.api.services.sheets.v4.Sheets;

public interface SpreadSheetService {

    public Sheets getCreditential();
    
    public Map<Object,Object> getSheetData();
    
}
