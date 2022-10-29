package com.kimeli.springsecurityclient.service;

import com.kimeli.springsecurityclient.entity.User;
import com.kimeli.springsecurityclient.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);
}
