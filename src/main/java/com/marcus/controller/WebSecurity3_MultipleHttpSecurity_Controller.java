package com.marcus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web-security-3-multiple-http-security")
public class WebSecurity3_MultipleHttpSecurity_Controller {

    @GetMapping("/api")
    public String apiGet() {
        return "api get /web-security-3-multiple-http-security";
    }

    @PostMapping("/api")
    public String apiPost() {
        return "api post /web-security-3-multiple-http-security";
    }

    @GetMapping
    public String loginGet() {
        return "login get /web-security-3-multiple-http-security";
    }

    @PostMapping
    public String loginPost() {
        return "login post /web-security-3-multiple-http-security";
    }
}
