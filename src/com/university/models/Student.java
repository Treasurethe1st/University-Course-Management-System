package com.university.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    private String program;
    private int year;
    private double gpa;
    private List<String> completedCourses;

    public Student(String studentId, String username, String password, String email, String fullName, String program) {
        super(studentId, username, password, email, fullName); // pass ID first
        this.program = program;
        this.completedCourses = new ArrayList<>();
        this.gpa = 0.0;
    }

    public String getProgram() { return program; }
    public List<String> getCompletedCourses() { return completedCourses; }
    public double getGPA() { return gpa; }
    public void setGPA(double gpa) { this.gpa = gpa; }

    @Override
    public String getRole() { return "STUDENT"; }

    @Override
    public void displayInfo() {
        System.out.println("Student: " + getFullName() + " | Program: " + program + " | GPA: " + gpa);
    }
}
