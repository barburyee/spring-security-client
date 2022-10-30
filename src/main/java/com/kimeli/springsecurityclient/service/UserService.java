package com.kimeli.springsecurityclient.service;

import com.kimeli.springsecurityclient.entity.User;
import com.kimeli.springsecurityclient.entity.VerificationToken;
import com.kimeli.springsecurityclient.model.PasswordModel;
import com.kimeli.springsecurityclient.model.UserModel;

import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String verifyUserWithToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetForUser(String token, User user);

    String validatePasswordResetToken(String token, PasswordModel passwordModel);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);
}
