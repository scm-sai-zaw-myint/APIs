package com.scm.api.pkg.data.spreadsheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.scm.api.pkg.Property;
import com.scm.api.pkg.google.GoogleConnections;
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
    
    private static Sheets getSpreadSheet(Property props) throws IOException, GeneralSecurityException {
        Credential credential = GoogleConnections.connect(props);
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), FACTORY, credential)
                .setApplicationName("Google Sheet API")
                .build();
    }
    
    
}
