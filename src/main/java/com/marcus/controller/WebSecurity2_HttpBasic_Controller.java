package com.marcus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web-security-2-http-basic")
public class WebSecurity2_HttpBasic_Controller {

    @GetMapping
    public String get() {
        return "get /web-security-2-http-basic";
    }

    @PostMapping
    public String post() {
        return "post /web-security-2-http-basic";
    }
}
