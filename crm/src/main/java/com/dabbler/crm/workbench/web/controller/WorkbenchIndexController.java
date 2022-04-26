package com.dabbler.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkbenchIndexController {
    @GetMapping("/workbench/index.do")
    public String index() {
        return "workbench/index";
    }
}
