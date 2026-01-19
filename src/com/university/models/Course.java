package com.university.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {

    private String courseCode;
    private String title;
    private int credits;
    private String instructorId;
    private int capacity;
    private List<String> prerequisites;
    private List<String> enrolledStudents;
    private String department;  // <-- new field

    // Updated constructor to include department
    public Course(
            String courseCode,
            String title,
            int credits,
            String instructorId,
            int capacity,
            List<String> prerequisites,
            String department   // <-- new parameter
    ) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.prerequisites = (prerequisites != null) ? prerequisites : new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
        this.department = department;   // <-- assign department
    }

    // ================= GETTERS =================
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructorId() { return instructorId; }
    public int getCapacity() { return capacity; }
    public List<String> getPrerequisites() { return prerequisites; }
    public List<String> getEnrolledStudents() { return enrolledStudents; }
    public String getDepartment() { return department; }  // <-- new getter

    // ================= SETTERS (IMPORTANT) =================
    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    public void setCapacity(int capacity) {
        if (capacity >= enrolledStudents.size()) {
            this.capacity = capacity;
        } else {
            System.out.println("Capacity cannot be less than enrolled students.");
        }
    }
    public void setPrerequisites(List<String> prerequisites) { this.prerequisites = prerequisites; }
    public void setDepartment(String department) { this.department = department; }  // <-- optional setter

    // ================= ENROLLMENT LOGIC =================
    public boolean isFull() { return enrolledStudents.size() >= capacity; }

    public boolean enrollStudent(String studentId) {
        if (isFull()) {
            System.out.println("Course is full.");
            return false;
        }
        if (enrolledStudents.contains(studentId)) {
            System.out.println("Student already enrolled.");
            return false;
        }
        enrolledStudents.add(studentId);
        return true;
    }

    public boolean dropStudent(String studentId) { return enrolledStudents.remove(studentId); }

    // ================= DISPLAY =================
    public void displayCourse() {
        System.out.println(
                courseCode + " | " + title +
                " | Credits: " + credits +
                " | Instructor: " + instructorId +
                " | Department: " + department +   // <-- display department
                " | Enrolled: " + enrolledStudents.size() + "/" + capacity
        );
    }
}
