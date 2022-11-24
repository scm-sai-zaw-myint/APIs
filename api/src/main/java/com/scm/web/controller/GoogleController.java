package com.scm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/google")
public class GoogleController {

    @GetMapping("/")
    public String googleGreet(Model model) {
        return "google/greet";
    }
    
}
