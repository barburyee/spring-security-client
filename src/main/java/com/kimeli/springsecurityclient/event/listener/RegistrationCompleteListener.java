package com.kimeli.springsecurityclient.event.listener;


import com.kimeli.springsecurityclient.entity.User;
import com.kimeli.springsecurityclient.event.RegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the verification Link of the User whenever they click, they will be redirected to

        //Send email to the user
        User user = event.getUser();
        String token = UUID.randomUUID().toString();//create an entity for this

    }
}
