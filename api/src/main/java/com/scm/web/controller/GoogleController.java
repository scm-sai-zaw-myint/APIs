package com.scm.web.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.services.sheets.v4.Sheets;
import com.scm.api.service.SpreadSheetService;
import com.scm.api.utils.SheetServiceUtil;

@Controller
@RequestMapping("/google")
public class GoogleController {

    @Autowired
    private SpreadSheetService spreadSheetService;
    
    @GetMapping("/")
    public String googleGreet(Model model) {
        Sheets sheet = this.spreadSheetService.getCreditential();
        return sheet == null ? "error/unauthorized": "google/greet";
    }
    
    @PostMapping("/spreadsheet")
    public String configSpreedSheet(@RequestParam String sheetId,Model model) throws IOException, GeneralSecurityException {
        return "google/spreadsheet";
    }
    
}
