package com.kimeli.springsecurityclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hellowThereKimeli(Principal principal){
        return "Hello,"+principal.getName()+" Welcome to SpringBoot Project 18";
    }
}
