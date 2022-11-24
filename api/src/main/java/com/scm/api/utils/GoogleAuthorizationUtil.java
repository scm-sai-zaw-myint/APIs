package com.scm.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

/**
 * <h2> GoogleAuthorizationUtil Class</h2>
 * <p>
 * Process for Displaying GoogleAuthorizationUtil
 * </p>
 * 
 * @author SaiZawMyint
 *
 */
@Component
public class GoogleAuthorizationUtil {
    
    @Value("${auth.server.port}")
    private int port;
    
    private final String CREDENTIALS_FILE_PATH = "/google-client-secret.json";
    
    private final JacksonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    private final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    
    private final String TOKENS_DIRECTORY_PATH = "spreadsheet/tokens";
    
    public Credential authorize(NetHttpTransport transport) throws IOException, GeneralSecurityException {
        InputStream in = TestSheetUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
          throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                transport, JACKSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("online")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081).setCallbackPath("/google/").build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
