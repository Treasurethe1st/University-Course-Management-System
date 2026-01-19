package com.university.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String id;          // unique user ID
    private String username;
    private String password;
    private String email;
    private String fullName;

    public User(String id, String username, String password, String email, String fullName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

    // ===== Getters =====
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }

    // ===== Setters (for admin updates) =====
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }

    // ===== Check password =====
    public boolean checkPassword(String password) { return this.password.equals(password); }

    // ===== Abstract methods =====
    public abstract String getRole();
    public abstract void displayInfo();
}
