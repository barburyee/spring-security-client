package com.kimeli.springsecurityclient.controller;

import com.kimeli.springsecurityclient.entity.User;
import com.kimeli.springsecurityclient.entity.VerificationToken;
import com.kimeli.springsecurityclient.event.RegistrationCompleteEvent;
import com.kimeli.springsecurityclient.model.PasswordModel;
import com.kimeli.springsecurityclient.model.UserModel;
import com.kimeli.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher; //for creating event to handle sending email to user to activate account
    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        //send email to user to verify account
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return "Registration Successful";
    }
    @GetMapping("/verifyRegistration")
    public String verifyingUser(@RequestParam("token") String token){
       String result = userService.verifyUserWithToken(token);
       if(result.equalsIgnoreCase("valid")){
            return "User Verified Successfully";
       }
        return result;
    }
    @GetMapping("/resendVerification")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        //resend the verification link
        resendVerificationMailToken(user, applicationUrl(request), verificationToken);
        return "Link Sent";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest httpRequest){
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if(user!=null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetForUser(token, user);
            url = paswordResetTokenMail(user, applicationUrl(httpRequest), token);
        }
        return url;
    }
    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel){
        String results = userService.validatePasswordResetToken(token);

        if(!results.equalsIgnoreCase("valid")){
            return "Token Not Invalid !!.:--- "+results;
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully. : "+results;
        }else{
            return "Invalid Token!!.: "+results;
        }

    }
    @PostMapping("changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if(!userService.checkIfValidOldPassword(user, passwordModel.getOldPassword())){
            return "Invalid Old Password";
        }
        //save New Password Now
        userService.changePassword(user, passwordModel.getNewPassword());
        return "Password Changed Successfully";

    }

    private String paswordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl +"/savePassword?token=" + token;
        log.info("Click this link to Reset Your Password: {}", url);
        return url;
    }


    private void resendVerificationMailToken(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl +"/verifyRegistration?token=" + verificationToken.getToken();
        log.info("Click this link to verify: {}", url);
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()
                +":"+request.getServerPort()
                +request.getContextPath();
    }
}
