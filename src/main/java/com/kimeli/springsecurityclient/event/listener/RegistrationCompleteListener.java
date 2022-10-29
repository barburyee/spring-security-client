package com.kimeli.springsecurityclient.event.listener;


import com.kimeli.springsecurityclient.entity.User;
import com.kimeli.springsecurityclient.event.RegistrationCompleteEvent;
import com.kimeli.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;
@Slf4j
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the verification Link of the User whenever they click, they will be redirected to

        User user = event.getUser();
        String token = UUID.randomUUID().toString();//create an entity for this

        userService.saveVerificationTokenForUser(token,user);
        //Send email to the user
        String url = event.getApplicationUrl()+"verifyRegistration?token="+token;
        log.info("Click this link to verify: {}",url);
        //Implement send email method here

    }
}
