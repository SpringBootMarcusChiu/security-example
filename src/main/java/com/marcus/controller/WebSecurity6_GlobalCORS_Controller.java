package com.marcus.controller;

import org.springframework.web.bind.annotation.*;

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
