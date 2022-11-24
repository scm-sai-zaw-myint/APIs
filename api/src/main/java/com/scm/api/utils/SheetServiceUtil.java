package com.scm.api.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

/**
 * <h2> SheetServiceUtil Class</h2>
 * <p>
 * Process for Displaying SheetServiceUtil
 * </p>
 * 
 * @author SaiZawMyint
 *
 */
public class SheetServiceUtil {
    @Value("${application.name}")
    private static String appName;
    
    public static Sheets getSheetService() throws IOException, GeneralSecurityException {
        Credential credential = GoogleAuthorizationUtil.authorize();
        return new Sheets
                .Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(appName)
                .build();
    }
}
