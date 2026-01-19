package com.university.models;

import java.io.Serializable;
import java.util.List;

public class Admin extends User implements Serializable {
    private String department;
    private List<String> permissions;

    public Admin(String adminId, String username, String password, String email, String fullName,
                 String department, List<String> permissions) {
        super(adminId, username, password, email, fullName); // pass ID first
        this.department = department;
        this.permissions = permissions;
    }

    public String getDepartment() { return department; }
    public List<String> getPermissions() { return permissions; }

    @Override
    public String getRole() { return "ADMIN"; }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getFullName() + " | Department: " + department);
    }
}
