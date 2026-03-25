package com.emanuel.loginauthentication.controllers;

import com.emanuel.loginauthentication.dto.LoginRequestDTO;
import com.emanuel.loginauthentication.dto.RegisterRequestDTO;
import com.emanuel.loginauthentication.models.User;
import com.emanuel.loginauthentication.repository.UserRepository;
import com.emanuel.loginauthentication.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;

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

    //login (this works)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request, HttpSession session)
    {
        Authentication auth = userService.loginUser(request);
        session.setAttribute("USER", auth.getName());
        return ResponseEntity.ok(
                Map.of("message", "Login successful", "username", auth.getName())
        );
    }

    //logout (this works)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session)
    {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    //register (this works)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request)
    {
        User user = userService.registerUser(request);
        return ResponseEntity.status(201).body(Map.of("message", "Login successful", "username", user.getUsername()));
    }


    //me
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session)
    {
        String username = (String) session.getAttribute("USER");

        if(username == null)
        {
            return ResponseEntity.status(401).body(Map.of("message", "Not logged in"));
        }
        User user = userService.getUserByUsername(username);
        return ResponseEntity.status(404).body(Map.of("message", "me", "username",  user.getUsername()));
    }

}
