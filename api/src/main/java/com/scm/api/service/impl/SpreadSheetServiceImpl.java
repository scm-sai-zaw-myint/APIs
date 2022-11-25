package com.scm.api.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.scm.api.bl.dto.spreadsheet.SpreadSheetDTO;
import com.scm.api.pkg.data.spreadsheet.SpreadSheetManager;
import com.scm.api.service.SpreadSheetService;
import com.scm.api.utils.SheetServiceUtil;

@Service
public class SpreadSheetServiceImpl implements SpreadSheetService{
    
    @Autowired
    SheetServiceUtil sheetServiceUtil;
    
    @Override
    public Sheets getCreditential() {
        try {
            return SpreadSheetManager.spreadSheetService();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Object, Object> getSheetData(String sheetId) {
        Sheets sheet = this.getCreditential();
        return null;
    }

    @Override
    public String createSheet(SpreadSheetDTO spreadsheet) {
        try {
            return SpreadSheetManager.createSheet(this.getCreditential(), spreadsheet);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
