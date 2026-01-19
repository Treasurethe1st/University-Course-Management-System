package com.university.auth;

import com.university.models.User;
import com.university.services.UserManager;

public class AuthenticationService {
    private UserManager userManager = new UserManager();
    private User currentUser;

    public boolean login(String username, String password) {
        userManager.loadUsers();
        User u = userManager.findByUsername(username);
        if (u != null && u.checkPassword(password)) {
            currentUser = u;
            return true;
        }
        return false;
    }

    public boolean isLoggedIn() { return currentUser != null; }
    public User getCurrentUser() { return currentUser; }
    public void logout() { currentUser = null; }
}
