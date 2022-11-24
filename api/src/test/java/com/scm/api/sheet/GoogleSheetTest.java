package com.scm.api.sheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.scm.api.utils.SheetServiceUtil;

public class GoogleSheetTest {

    private static Sheets sheetService;
    
    private static final String SHEET_ID = "13R616U4qGPczsRfCZpzrepE8L_1VNBcyls8PofFuo7c";
    
    @BeforeClass
    public static void setup() throws IOException, GeneralSecurityException {
    }
    
    @Test
    public static void writeSheet() throws IOException {
        ValueRange body = new ValueRange().setValues(
                Arrays.asList(Arrays.asList(
                            Arrays.asList("Expenses"),
                            Arrays.asList("Books","1000"),
                            Arrays.asList("Pens","500"),
                            Arrays.asList("Clothes","5000")
                        ))
                );
        
        UpdateValuesResponse result = sheetService.spreadsheets()
                .values()
                .update(SHEET_ID, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }
    
}
