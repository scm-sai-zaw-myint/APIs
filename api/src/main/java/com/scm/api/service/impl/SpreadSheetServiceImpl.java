package com.scm.api.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.scm.api.service.SpreadSheetService;
import com.scm.api.utils.SheetServiceUtil;

@Service
public class SpreadSheetServiceImpl implements SpreadSheetService{
    
    @Autowired
    SheetServiceUtil sheetServiceUtil;
    
    @Override
    public Sheets getCreditential() {
        
        try {
            return sheetServiceUtil.getSheetService();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Object, Object> getSheetData() {
        
        return null;
    }

}
