package com.marcus.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @CrossOrigin(origins="*") adds the following HTTP Response Headers
 * - Vary: Origin
 * - Vary: Access-Control-Request-Method
 * - Vary: Access-Control-Request-Headers
 *
 * @CrossOrigin(allowCredentials="true") is handled in CustomWebSecurity5_local_cors_Config
 * via http.cors() call
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cors-local")
public class WebSecurity5_LocalCORS_Controller {

    @GetMapping
    public String get() {
        return "get /cors-local";
    }

    @PostMapping
    public String post() {
        return "post /cors-local";
    }
}
