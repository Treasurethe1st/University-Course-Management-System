package com.university.services;

import com.university.models.Grade;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GradeManager {
    private final String FILE_PATH = "data/grades.ser";
    private List<Grade> grades;

    public GradeManager() {
        grades = new ArrayList<>();
        loadGrades();
    }

    // Assign a new grade to a student
    public void assignGrade(Grade grade) {
        grades.add(grade);
        saveGrades();
    }

    // Get all grades for a student
    public List<Grade> getGradesForStudent(String studentId) {
        List<Grade> studentGrades = new ArrayList<>();
        for (Grade g : grades) {
            if (g.getStudentId().equals(studentId)) studentGrades.add(g);
        }
        return studentGrades;
    }

    // Get all grades in the system
    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }

    // Display grades for a student along with GPA
    public void displayStudentGrades(String studentId) {
        List<Grade> studentGrades = getGradesForStudent(studentId);
        if (studentGrades.isEmpty()) {
            System.out.println("No grades found for student " + studentId);
            return;
        }

        System.out.println("--- Grades for Student: " + studentId + " ---");
        for (Grade g : studentGrades) g.displayGrade();
        System.out.printf("GPA: %.2f%n", calculateGPA(studentGrades));
    }

    // Display all grades in the system (ADMIN use)
    public void displayAllGrades() {
        if (grades.isEmpty()) {
            System.out.println("No grades recorded in the system.");
            return;
        }

        System.out.println("--- All Grades in System ---");
        for (Grade g : grades) {
            System.out.println("Student: " + g.getStudentId() +
                    " | Course: " + g.getCourseCode() +
                    " | Score: " + g.getScore() +
                    " | Letter: " + calculateLetterGrade(g.getScore()));
        }
    }

    // Calculate GPA (4.0 scale), accounting for course credits
    public double calculateGPA(List<Grade> studentGrades) {
        if (studentGrades.isEmpty()) return 0.0;

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Grade g : studentGrades) {
            int credits = g.getCredits();        // use credits from Grade
            totalPoints += g.getGradePoint() * credits;
            totalCredits += credits;
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    // ===== Persistence =====
    public void saveGrades() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(grades);
        } catch (IOException e) {
            System.out.println("Error saving grades: " + e.getMessage());
        }
    }

    public void loadGrades() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            grades = (List<Grade>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading grades: " + e.getMessage());
        }
    }

    // Convert numeric score to letter grade
    public String calculateLetterGrade(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}
