package com.scm.api.pkg.data.spreadsheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.scm.api.pkg.Property;
import com.scm.api.pkg.google.GoogleConnections;
import com.scm.api.pkg.property.ResponseCode;
import com.scm.api.pkg.response.Response;
import com.scm.api.pkg.spreadsheet.SpreadSheet;

import lombok.Data;

@Data
public class SpreadSheetManager {

    private static JacksonFactory FACTORY = JacksonFactory.getDefaultInstance();
    
    public SpreadSheetManager() {
        super();
    }
    
    public static Sheets spreadSheetService(Property props) throws IOException, GeneralSecurityException {
        return getSpreadSheet(props);
    }
    
    public static Sheets spreadSheetService() throws IOException, GeneralSecurityException {
        Property prop = new Property();
        prop.setClientSecretPath("/google-client-secret.json");
        prop.setPort(8081);
        prop.setCallBack("/google/");
        prop.setStoreTokenPath("spreadsheet/token");
        prop.setAccessType("offline");
        prop.setScope(Collections.singletonList(SheetsScopes.SPREADSHEETS));
        return getSpreadSheet(prop);
    }
    
    public static String createSheet(Sheets sheet, SpreadSheet spreadsheetdata) throws IOException {
        Spreadsheet spreadsheet = new Spreadsheet().setProperties(
                new SpreadsheetProperties().setTitle(spreadsheetdata.getName())
                );
        spreadsheet = sheet.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();
        return spreadsheet.getSpreadsheetId();
    }
    
    public static Map<Object, Object> getSpreadSheetData(Sheets sheet,String sheetId,String range) throws IOException {
        Map<Object,Object> responseData = new HashMap<>();
        
        ValueRange value = sheet.spreadsheets().values().get(sheetId, range).execute();
        
        List<List<Object>> response = value.getValues();
        int row = 0;
        if(response == null) {
            return null;
        }
        for(List<Object> outer:response) {
            String rowstr = "row"+ (++row);
            List<Object> list = new ArrayList<>();
            for(Object obj: outer) {
                list.add(obj);
            }
            
            responseData.put(rowstr, list);
        }
        return responseData;
    }
    
    public static Object getAPIDocumentation() throws StreamWriteException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream in = SpreadSheetManager.class.getResourceAsStream("/google-sheet-api-doc.json");
        String result = new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n"));
        return mapper.readValue(result, Map.class);
    }
    
    private static Sheets getSpreadSheet(Property props) throws IOException, GeneralSecurityException {
        Credential credential = GoogleConnections.connect(props);
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), FACTORY, credential)
                .setApplicationName("Google Sheet API")
                .build();
    }
    
    
}
