package com.scm.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.application.version}")
    private String version;
    
    @Autowired
    HttpSession session;
    
    @GetMapping("/")
    public String greet(Model model) {
        session.setAttribute("appName", appName);
        session.setAttribute("version", version);
        return "index";
    }
}
