package com.gmail.vladyslavnicko.shop.mvc.controller.rest;

import com.gmail.vladyslavnicko.shop.DTO.UserInfo;
import com.gmail.vladyslavnicko.shop.DTO.UserPassword;
import com.gmail.vladyslavnicko.shop.exception.ConflictException;
import com.gmail.vladyslavnicko.shop.model.User;
import com.gmail.vladyslavnicko.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User findUser = userService.findByEmail(user.getEmail());
        if (findUser == null || !findUser.getPassword().equals(user.getPassword())) {
            throw new ConflictException("Invalid email or password");
        } else {
            return ResponseEntity.ok(findUser);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null) {
            throw new ConflictException("Email already exists");
        } else {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        }
    }

    @PutMapping("/update/{id:[0-9]*}")
    public ResponseEntity<UserInfo> update(@PathVariable long id, @RequestBody UserInfo user) {
        UserInfo savedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/update/password/{id:[0-9]*}")
    public ResponseEntity<UserInfo> updatePassword(@PathVariable long id, @RequestBody UserPassword user) {
        UserInfo savedUser = userService.updateUserPassword(id, user);
        return ResponseEntity.ok(savedUser);
    }

}

