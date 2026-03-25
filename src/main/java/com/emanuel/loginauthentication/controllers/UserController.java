package com.emanuel.loginauthentication.controllers;

import com.emanuel.loginauthentication.dto.LoginRequestDTO;
import com.emanuel.loginauthentication.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true") //this allows to connect to angular (will use later)
public class UserController {

    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request, HttpSession session)
    {
        Authentication auth = userService.loginUser(request);
        session.setAttribute("USER", auth.getName());
        return ResponseEntity.ok(
                Map.of("message", "Login successful", "username", auth.getName())
        );
    }

    //logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session)
    {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    //register

    //me
}
