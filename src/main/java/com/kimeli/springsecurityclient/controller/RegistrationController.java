package com.kimeli.springsecurityclient.controller;

import com.kimeli.springsecurityclient.entity.User;
import com.kimeli.springsecurityclient.event.RegistrationCompleteEvent;
import com.kimeli.springsecurityclient.model.UserModel;
import com.kimeli.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher; //for creating event to handle sending email to user to activate account
    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel){
        User user = userService.registerUser(userModel);
        //send email to user to verify account
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(
                user,
                "url"
        ));
        return "Registration Successful";
    }
}
