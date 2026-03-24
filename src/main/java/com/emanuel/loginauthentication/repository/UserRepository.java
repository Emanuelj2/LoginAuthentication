package com.emanuel.loginauthentication.repository;

import com.emanuel.loginauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // Registration validation — prevent duplicate usernames/emails
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
