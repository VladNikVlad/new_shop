package com.gmail.vladyslavnicko.shop.repository;

import com.gmail.vladyslavnicko.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstName(String username);
    User findByEmail(String email);
    User findById(long id);
}
