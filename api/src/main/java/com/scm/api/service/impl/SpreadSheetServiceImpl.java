package com.scm.api.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.scm.api.bl.dto.spreadsheet.SpreadSheetDTO;
import com.scm.api.pkg.data.spreadsheet.SpreadSheetManager;
import com.scm.api.pkg.property.ResponseCode;
import com.scm.api.pkg.resolver.FieldResolver;
import com.scm.api.pkg.resolver.Resolver;
import com.scm.api.pkg.resolver.SpreadSheetResolver;
import com.scm.api.pkg.response.Response;
import com.scm.api.service.SpreadSheetService;
import com.scm.api.utils.SheetServiceUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
public class SpreadSheetServiceImpl implements SpreadSheetService {

    @Autowired
    SheetServiceUtil sheetServiceUtil;

    @Override
    public Object resolveRequest(String sheetId, String range, String name, String fields) {
        if(sheetId == null) Response.send(ResponseCode.REQUIRED,false);
        Map<String,Object> fieldsResolve = new FieldResolver(fields).resolve().get();
        Map<String,Object> sheetResolve = new SpreadSheetResolver(sheetId,name,range).resolve().get();
        
        try {
            Object obj =SpreadSheetManager.getSpreadSheetData(this.getCreditential(),(String) sheetResolve.get("sheetId"),(String) sheetResolve.get("range"));
            return obj == null ? Response.send(ResponseCode.EMPTY,true) : Response.send(obj, ResponseCode.SUCCESS,true);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.send(ResponseCode.ERROR,false);
        }
    }

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
    public Map<Object, Object> getSheetData(String sheetId, String range) {
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

    @Override
    public Object getAPIDocumentation() {
        try {
            Object obj = SpreadSheetManager.getAPIDocumentation();
            return Response.send(obj,ResponseCode.SUCCESS, true);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.send(ResponseCode.ERROR, false);
        }
    }

}
