package com.kimeli.springsecurityclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hellowThereKimeli(){
        return "Hello, Welcome to SpringBoot Project 18";
    }
}
