package com.emanuel.loginauthentication.models;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password", nullable = false)
    private String passwordHashed;

    //constructor
    public User(){}

    public User(String username, String email, String passwordHashed)
    {
        this.username = username;
        this.email = email;
        this.passwordHashed = passwordHashed;
    }

    //getters & setters
    public Long getId() { return id; }
    public String getPasswordHashed() { return passwordHashed; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setPassword(String passwordHashed) { this.passwordHashed = passwordHashed; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
}
