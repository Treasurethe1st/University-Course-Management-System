package com.university.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends User implements Serializable {
    private String department;
    private List<String> coursesTaught = new ArrayList<>();

    public Instructor(String instructorId, String username, String password, String email, String fullName, String department) {
        super(instructorId, username, password, email, fullName); // pass ID first
        this.department = department;
    }

    public String getDepartment() { return department; }
    public List<String> getCoursesTaught() { return new ArrayList<>(coursesTaught); }

    public void addCourseTaught(String courseCode) {
        if (!coursesTaught.contains(courseCode)) coursesTaught.add(courseCode);
    }

    public void removeCourseTaught(String courseCode) {
        coursesTaught.remove(courseCode);
    }

    @Override
    public String getRole() { return "INSTRUCTOR"; }

    @Override
    public void displayInfo() {
        System.out.println("Instructor: " + getFullName() + " | Department: " + department);
    }
}
