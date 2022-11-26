package com.scm.api.pkg.data.spreadsheet;

import java.io.BufferedReader;
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

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateSheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.UpdateSpreadsheetPropertiesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.scm.api.pkg.Property;
import com.scm.api.pkg.google.GoogleConnections;
import com.scm.api.pkg.google.spreadsheet.form.SpreadSheetForm;
import com.scm.api.pkg.response.spreadsheet.SpreadSheetResponse;

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

    public static Object createSpreadSheet(Sheets sheet, SpreadSheetForm sheetform) throws IOException {
        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties().setTitle(sheetform.getName()));
        spreadsheet = sheet.spreadsheets().create(spreadsheet).setFields("spreadsheetId").execute();

        if (sheetform.getSheetName() != null) {
            renameSheet(sheet, spreadsheet, sheetform.getSheetName());
        }

        if (sheetform.getValues() != null && sheetform.getValues().size() > 0)
            updateSheet(sheet, spreadsheet.getSpreadsheetId(), "A1", sheetform.getValues());

        sheetform.setSheetId(spreadsheet.getSpreadsheetId());

        return new SpreadSheetResponse(sheetform);
    }

    public static Map<Object, Object> getSpreadSheetData(Sheets sheet, String sheetId, String range)
            throws IOException {
        Map<Object, Object> responseData = new HashMap<>();

        ValueRange value = sheet.spreadsheets().values().get(sheetId, range).execute();

        List<List<Object>> response = value.getValues();
        int count = 0;
        if (response == null) {
            return null;
        }
        for (List<Object> row : response) {
            String rowstr = "row" + (++count);
            List<Object> list = new ArrayList<>();
            for (Object col : row) {
                list.add(col);
            }
            responseData.put(rowstr, list);
        }
        return responseData;
    }

    public static Object getAPIDocumentation() throws StreamWriteException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream in = SpreadSheetManager.class.getResourceAsStream("/google-sheet-api-doc.json");
        String result = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
        return mapper.readValue(result, Map.class);
    }

    public static Object updateSpreadSheet(Sheets sheet, SpreadSheetForm form, String options) throws IOException {

        List<ValueRange> data = new ArrayList<>();
        data.add(new ValueRange().setValues(form.getValues()).setRange("A1"));

        Spreadsheet spreadsheet = sheet.spreadsheets().get(form.getSheetId()).execute();

        // rename spreadsheet process
        if (form.getName() != null)
            renameSpreadsheet(sheet, spreadsheet, form);

        // rename sheet process
        if (form.getSheetName() != null)
            renameSheet(sheet, spreadsheet,
                    form.getSheetName());

        BatchUpdateValuesRequest request = new BatchUpdateValuesRequest();
        request.setValueInputOption(options);
        request.setData(data);

        BatchUpdateValuesResponse response = sheet.spreadsheets().values().batchUpdate(form.getSheetId(), request)
                .execute();

        Map<Object, Object> result = new HashMap<>();
        result.put("spreadSheet", new SpreadSheetResponse(form));
        result.put("record", response);
        return result;
    }

    private static Sheets getSpreadSheet(Property props) throws IOException, GeneralSecurityException {
        Credential credential = GoogleConnections.connect(props);
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), FACTORY, credential)
                .setApplicationName("Google Sheet API").build();
    }

    @SuppressWarnings("unchecked")
    private static Sheets updateSheet(Sheets sheet, String sheetid, String range, Object values) throws IOException {
        ValueRange valueRange = new ValueRange().setValues((List<List<Object>>) values);
        sheet.spreadsheets().values().update(sheetid, range, valueRange).setValueInputOption("RAW").execute();
        return sheet;
    }

    private static boolean renameSheet(Sheets sheet, Spreadsheet spreadsheet, String newName) throws IOException {
        String sheetName = spreadsheet.getSheets().get(0).getProperties().getTitle();
        int index = getSheetIndexByName(sheet, spreadsheet, sheetName);
        index = index < 0 ? 0 : index;
        if (!sheetName.equals(newName)) {
            spreadsheet.getSheets().get(index).setProperties(new SheetProperties().setTitle(newName));
            UpdateSheetPropertiesRequest request = new UpdateSheetPropertiesRequest().setFields("Title")
                    .setProperties(new SheetProperties().setTitle(newName));
            List<Request> list = new ArrayList<>();
            list.add(new Request().setUpdateSheetProperties(request));
            BatchUpdateSpreadsheetRequest updateRequest = new BatchUpdateSpreadsheetRequest().setRequests(list);
            sheet.spreadsheets().batchUpdate(spreadsheet.getSpreadsheetId(), updateRequest).execute();
            return true;
        } else {
            return false;
        }
    }

    private static boolean renameSpreadsheet(Sheets sheet, Spreadsheet spreadsheet, SpreadSheetForm form)
            throws IOException {
        String title = spreadsheet.getProperties().getTitle();
        if (form.getSheetName().equals(title)) {
            return false;
        } else {
            UpdateSpreadsheetPropertiesRequest request = new UpdateSpreadsheetPropertiesRequest().setFields("Title")
                    .setProperties(new SpreadsheetProperties().setTitle(form.getName()));
            List<Request> list = new ArrayList<>();
            list.add(new Request().setUpdateSpreadsheetProperties(request));
            BatchUpdateSpreadsheetRequest updateRequest = new BatchUpdateSpreadsheetRequest().setRequests(list);
            sheet.spreadsheets().batchUpdate(form.getSheetId(), updateRequest).execute();
            return true;
        }
    }

    private static int getSheetIndexByName(Sheets sheets, Spreadsheet spreadsheet, Object findName) {
        return spreadsheet.getSheets().indexOf(findName);
    }
}
