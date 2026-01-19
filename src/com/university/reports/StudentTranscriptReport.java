package com.university.reports;

import com.university.models.Grade;
import com.university.services.GradeManager;

import java.util.List;

public class StudentTranscriptReport extends Report {

    private final String studentId;
    private final GradeManager gradeManager;

    public StudentTranscriptReport(String studentId, GradeManager gradeManager) {
        super("Student Transcript");
        this.studentId = studentId;
        this.gradeManager = gradeManager;
    }

    @Override
    public void generate() {
        printHeader();

        // Fetch all grades for the student
        List<Grade> grades = gradeManager.getGradesForStudent(studentId);

        if (grades.isEmpty()) {
            System.out.println("No grades found for student: " + studentId);
            return;
        }

        double totalPoints = 0.0;
        int totalCredits = 0;

        System.out.println("--- Grades ---");
        for (Grade g : grades) {
            System.out.println(
                g.getCourseCode() + " | Score: " + g.getScore() + " | Grade: " + g.getLetterGrade()
            );
            totalPoints += g.getGradePoint() * g.getCredits();
            totalCredits += g.getCredits();
        }

        double gpa = totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
        System.out.println("\nGPA: " + String.format("%.2f", gpa));
    }
}
