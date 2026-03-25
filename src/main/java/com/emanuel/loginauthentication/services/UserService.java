package com.emanuel.loginauthentication.services;

import com.emanuel.loginauthentication.dto.LoginRequestDTO;
import com.emanuel.loginauthentication.dto.RegisterRequestDTO;
import com.emanuel.loginauthentication.models.User;
import com.emanuel.loginauthentication.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    //we are creating a user hence the reason we are using "User"
    public User registerUser(RegisterRequestDTO request)
    {
        //if the user exist throw a warning (check the username & email)
        if(userRepository.existsByUsername(request.getUsername()))
        {
            throw new RuntimeException("Username already exist");
        }
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already exist");
        }

        //create the user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }


    //login with authentication
    public Authentication loginUser(LoginRequestDTO request)
    {
        //this automatically checks if the user is in the db
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public User getUserByUsername(String username)
    {
        return userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found: " + username));
    }

    public User getUserByEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found: " + email));
    }

}
