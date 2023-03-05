package com.gmail.vladyslavnicko.shop.service.impl;

import com.gmail.vladyslavnicko.shop.DTO.UserInfo;
import com.gmail.vladyslavnicko.shop.DTO.UserPassword;
import com.gmail.vladyslavnicko.shop.exception.ConflictException;
import com.gmail.vladyslavnicko.shop.model.Address;
import com.gmail.vladyslavnicko.shop.model.User;
import com.gmail.vladyslavnicko.shop.repository.UserRepository;
import com.gmail.vladyslavnicko.shop.security.PasswordHashing;
import com.gmail.vladyslavnicko.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordHashing passwordHashing;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordHashing passwordHashing) {
        this.userRepository = userRepository;
        this.passwordHashing = passwordHashing;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        String password = user.getPassword();
        byte[] salt = passwordHashing.generateSalt();
        user.setSalt(salt);
        user.setPasswordHash(passwordHashing.hashPassword(password, salt));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public UserInfo updateUser(long id, UserInfo user) {
        User findUser = userRepository.findById(id);
        if (findUser == null) {
            throw new ConflictException("Invalid id");
        }
        Address address = null;
        if (user.getCity() != null){
            System.out.println("Hello");
            address = new Address();
            address.setId(findUser.getAddress().getId());
            address.setCity(user.getCity());
            if (user.getStreet() != null) {
                address.setStreet(user.getStreet());
            }
            if (user.getState() != null) {
                address.setState(user.getState());
            }
            if (user.getZip() != null) {
                address.setZip(user.getZip());
            }
            findUser.setAddress(address);
        }

        if (user.getFirstName() == null ||  user.getFirstName().isBlank()) {
            throw new ConflictException("The name cannot be empty");
        }
        findUser.setFirstName(user.getFirstName().trim());
        if (user.getLastName() != null && !user.getLastName().isBlank()) {
            findUser.setLastName(user.getLastName());
        }
        if (user.getDateOfBirth() != null && !user.getDateOfBirth().isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(user.getDateOfBirth(), formatter);
            findUser.setDateOfBirth(localDate);
        }
        UserInfo info = new UserInfo();
        return info.getUserInfoFromUser(userRepository.save(findUser));
    }

    @Transactional
    @Override
    public UserInfo updateUserPassword(long id, UserPassword user){
        User findUser = userRepository.findById(id);
        if (findUser == null || !findUser.getPassword().equals(user.getOldPassword())) {
            throw new ConflictException("Invalid email or password");
        }
        if (user.getOldPassword().isBlank() || user.getOldPassword() == null) {
            throw new ConflictException("Old password is empty");
        }
        if (user.getNewPassword().isBlank() || user.getNewPassword() == null) {
            throw new ConflictException("New password is empty");
        }
        if (user.getOldPassword().equals(user.getNewPassword())) {
            throw new ConflictException("The entered password is identical to the old one");
        }
      //  findUser.setPassword(user.getNewPassword());
        return new UserInfo().getUserInfoFromUser(userRepository.save(findUser));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean chackPasssword(String password, User user) {
        // для проверки пароля, запрашиваем соль и захэшированный пароль из базы данных
        byte[] storedSalt = user.getSalt();
        byte[] storedHashedPassword = user.getPasswordHash();
        // проверяем, соответствует ли введенный пользователем пароль сохраненному захэшированному паролю
        return passwordHashing.verifyPassword(password, storedSalt, storedHashedPassword);
    }
}
