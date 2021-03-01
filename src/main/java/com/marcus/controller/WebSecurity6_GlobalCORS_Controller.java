package com.marcus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cors-global")
public class WebSecurity6_GlobalCORS_Controller {

    @GetMapping
    public String get() {
        return "get /cors-global";
    }

    @PostMapping
    public String post() {
        return "post /cors-global";
    }
}
