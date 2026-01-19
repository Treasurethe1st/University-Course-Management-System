package com.university.services;

import com.university.models.Grade;
import java.util.List;

public class GPAService {

    // GPA calculation using GradeManager
    public double calculateGPA(String studentId, GradeManager gradeManager) {
        List<Grade> grades = gradeManager.getGradesForStudent(studentId);
        if (grades.isEmpty()) return 0.0;

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Grade g : grades) {
            int credits = g.getCredits(); // <-- if Grade has credits, use it; otherwise default
            totalPoints += letterToPoint(g.getLetterGrade()) * credits;
            totalCredits += credits;
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    private double letterToPoint(String letter) {
        return switch (letter.toUpperCase()) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            default -> 0.0;
        };
    }
}
