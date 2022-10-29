package com.kimeli.springsecurityclient.service;

import com.kimeli.springsecurityclient.entity.User;
import com.kimeli.springsecurityclient.entity.VerificationToken;
import com.kimeli.springsecurityclient.model.UserModel;
import com.kimeli.springsecurityclient.repository.UserRepository;
import com.kimeli.springsecurityclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setAddress(userModel.getAddress());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));//encrypt the password before saving to db
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }
}
