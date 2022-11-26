package com.scm.api.controller;

import java.util.Map;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scm.api.pkg.google.spreadsheet.form.SpreadSheetForm;
import com.scm.api.service.SpreadSheetService;

@RestController
@RequestMapping("/scm/api/spreadsheet")
public class SpreadSheetController {
    
    @Autowired
    SpreadSheetService spreadSheetService;

    @GetMapping("/")
    public Map<?, ?> getDocumentation(){
        return (Map<?, ?>) this.spreadSheetService.getAPIDocumentation();
    }
    
    @GetMapping("/{sheetId}")
    @ResponseBody
    public Map<?, ?> getSpreadSheet(@PathVariable Object sheetId, @Nullable @ModelAttribute SpreadSheetForm form,@Nullable @RequestParam String fields){
        return (Map<?, ?>) spreadSheetService.resolveRequest((String)sheetId, form.getRange(), form.getName(), fields);
    }
    
    @PostMapping("/create")
    @ResponseBody
    public Map<?, ?> createSpreadSheet(@Nullable@RequestBody SpreadSheetForm form){
        return (Map<?, ?>) spreadSheetService.createSpreadSheet(form);
    }
    
    @PostMapping("/{sheetId}/update")
    @ResponseBody
    public Map<?,?> updateSpreadsheet(@PathVariable String sheetId, @RequestBody SpreadSheetForm form){
        return (Map<?,?>) spreadSheetService.updateSpreadSheet(sheetId,form);
    }
    
    
}
