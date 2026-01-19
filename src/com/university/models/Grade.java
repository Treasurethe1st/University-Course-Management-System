package com.university.models;

import java.io.Serializable;

public class Grade implements Serializable {
    private String studentId;
    private String courseCode;
    private double score;
    private String letterGrade;
    private int credits;

    public Grade(String studentId, String courseCode, double score, int credits) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.score = score;
        this.credits = credits;
        this.letterGrade = calculateLetterGrade(score);
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public double getScore() { return score; }
    public String getLetterGrade() { return letterGrade; }
    public int getCredits() { return credits; }

    public double getGradePoint() {
        return switch (letterGrade) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            default -> 0.0;
        };
    }

    // ===== Fix for GPAManager and ReportManager =====
    public double getPoints() {
        return getGradePoint();
    }

    public String getLetter() {
        return getLetterGrade();
    }

    public void displayGrade() {
        System.out.println(courseCode + " | " + score + " | " + letterGrade);
    }

    private String calculateLetterGrade(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}
