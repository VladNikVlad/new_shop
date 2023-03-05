package com.gmail.vladyslavnicko.shop.service;

import com.gmail.vladyslavnicko.shop.DTO.UserInfo;
import com.gmail.vladyslavnicko.shop.DTO.UserPassword;
import com.gmail.vladyslavnicko.shop.model.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User saveUser(User user);
    List<User> findAllUsers();
    void deleteUserById(Long id);
    UserInfo updateUser(long id, UserInfo user);
    UserInfo updateUserPassword(long id, UserPassword user);
    boolean chackPasssword(String password, User user);
}
