package com.scm.api.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
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
@Component
public class SheetServiceUtil {
    @Value("${spring.application.name}")
    private String appName;
    
    @Autowired
    GoogleAuthorizationUtil googleAuthorizationUtil;
    
    public Sheets getSheetService() throws IOException, GeneralSecurityException {
        NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = googleAuthorizationUtil.authorize(transport);
        return new Sheets
                .Builder(transport, JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(appName)
                .build();
    }
}
