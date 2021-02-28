package com.marcus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web-security-1-form-login")
public class WebSecurity1_FormLogin_Controller {

    @GetMapping
    public String get() {
        return "get /web-security-1-form-login";
    }

    @PostMapping
    public String post() {
        return "post /web-security-1-form-login";
    }
}
