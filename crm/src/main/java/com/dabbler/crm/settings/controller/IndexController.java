package com.dabbler.crm.settings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping("/")
    public String toLogin(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("loginAct".equals(c.getName())) {
                    model.addAttribute("loginAct",c.getValue());
                }
                if ("loginPwd".equals(c.getName())) {
                    model.addAttribute("loginPwd",c.getValue());
                }
            }
        }
        return "settings/qx/user/login";
    }
}
