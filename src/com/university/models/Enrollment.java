package com.university.models;

import java.io.Serializable;

public class Enrollment implements Serializable {
    private String enrollmentId;
    private String studentId;
    private String courseCode;
    private String semester;
    private String status; // ACTIVE, DROPPED, COMPLETED

    public Enrollment(String enrollmentId, String studentId, String courseCode, String semester, String status) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
        this.status = status;
    }

    public String getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public String getSemester() { return semester; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public void displayEnrollment() {
        System.out.println(enrollmentId + " | Student: " + studentId + " | Course: " + courseCode +
                           " | Semester: " + semester + " | Status: " + status);
    }
}
