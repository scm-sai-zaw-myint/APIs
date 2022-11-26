package com.scm.api.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.scm.api.pkg.data.spreadsheet.SpreadSheetManager;
import com.scm.api.pkg.exceptions.Exception;
import com.scm.api.pkg.google.spreadsheet.form.SpreadSheetForm;
import com.scm.api.pkg.property.ResponseCode;
import com.scm.api.pkg.resolver.FieldResolver;
import com.scm.api.pkg.resolver.SpreadSheetResolver;
import com.scm.api.pkg.response.Response;
import com.scm.api.service.SpreadSheetService;
import com.scm.api.utils.SheetServiceUtil;

@Service
public class SpreadSheetServiceImpl implements SpreadSheetService {

    @Autowired
    SheetServiceUtil sheetServiceUtil;

    @SuppressWarnings("unchecked")
    @Override
    public Object resolveRequest(String sheetId, String range, String name, String fields) {
        if(sheetId == null) Response.send(ResponseCode.REQUIRED,false);
        Map<String,Object> fieldsResolve = (Map<String, Object>) new FieldResolver(fields).resolve().get();
        Map<String,Object> sheetResolve = (Map<String, Object>) new SpreadSheetResolver(sheetId,name,range).resolve().get();
        
        try {
            Object obj =SpreadSheetManager.getSpreadSheetData(this.getCreditential(),(String) sheetResolve.get("sheetId"),(String) sheetResolve.get("range"));
            return obj == null ? Response.send(ResponseCode.EMPTY,true) : Response.send(obj, ResponseCode.SUCCESS,true);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            Object message = e instanceof GoogleJsonResponseException ? Exception.parseGoogleException((GoogleJsonResponseException) e) : e.getMessage();
            return Response.send(ResponseCode.ERROR,false,message);
        }
    }

    @Override
    public Sheets getCreditential() throws IOException, GeneralSecurityException {
        return SpreadSheetManager.spreadSheetService();
    }

    @Override
    public Map<Object, Object> getSheetData(String sheetId, String range) {
        return null;
    }

    @Override
    public Object createSpreadSheet(SpreadSheetForm spreadsheet) {
        if(spreadsheet == null || (spreadsheet != null && spreadsheet.getName() == null)) return Response.send(ResponseCode.REQUIRED,false,"Spreadsheet name is required");
        try {
            Object data = SpreadSheetManager.createSpreadSheet(this.getCreditential(), spreadsheet);
            return Response.send(data, ResponseCode.SUCCESS, true);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            Object message = e instanceof GoogleJsonResponseException ? Exception.parseGoogleException((GoogleJsonResponseException) e) : e.getMessage();
            return Response.send(ResponseCode.ERROR,false,message);
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

    @Override
    public Object updateSpreadSheet(String sheetId, SpreadSheetForm form) {
        if(sheetId == null ) return Response.send(ResponseCode.REQUIRED,false,"Spreadsheet Id is required");
        form.setSheetId(sheetId);
        try {
            Object data = SpreadSheetManager.updateSpreadSheet(getCreditential(), form, "RAW");
            return Response.send(data, ResponseCode.SUCCESS, true);
        }catch(IOException | GeneralSecurityException e) {
            e.printStackTrace();
            Object message = e instanceof GoogleJsonResponseException ? Exception.parseGoogleException((GoogleJsonResponseException) e) : e.getMessage();
            return Response.send(ResponseCode.ERROR,false,message);
        }
    }

}
