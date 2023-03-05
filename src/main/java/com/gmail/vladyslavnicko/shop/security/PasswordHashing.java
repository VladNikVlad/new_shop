package com.gmail.vladyslavnicko.shop.security;

public interface PasswordHashing {
    byte[] generateSalt();
    byte[] hashPassword(String password, byte[] salt);
    boolean verifyPassword(String password, byte[] salt, byte[] storedHashedPassword);
}
