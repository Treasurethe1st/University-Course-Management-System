package com.university.services;

import com.university.models.Course;
import com.university.models.Grade;

import java.util.List;
import java.util.stream.Collectors;

public class ReportManager {
    private CourseManager courseManager;
    private GradeManager gradeManager;

    public ReportManager(CourseManager courseManager, GradeManager gradeManager) {
        this.courseManager = courseManager;
        this.gradeManager = gradeManager;
    }

    public void generateTranscript(String studentId) {
        System.out.println("\n--- Transcript for " + studentId + " ---");
        List<Grade> grades = gradeManager.getAllGrades().stream()
                .filter(g -> g.getStudentId().equals(studentId))
                .collect(Collectors.toList());
        if (grades.isEmpty()) System.out.println("No grades available.");
        grades.forEach(g -> System.out.println(g.getCourseCode() + " | Score: " + g.getScore() + " | " + g.getLetter()));
    }

    public void displayCourseRoster(String courseCode) {
        Course c = courseManager.findByCode(courseCode);
        if (c == null) { System.out.println("Course not found."); return; }
        System.out.println("\n--- Roster for " + courseCode + " ---");
        c.getEnrolledStudents().forEach(System.out::println);
    }

    public void generateTeachingLoadReport(String instructorId) {
        System.out.println("\n--- Teaching Load for " + instructorId + " ---");
        courseManager.getAllCourses().stream()
                .filter(c -> c.getInstructorId().equals(instructorId))
                .forEach(c -> System.out.println(c.getCourseCode() + " | " + c.getTitle() + " | Enrolled: " + c.getEnrolledStudents().size()));
    }

    public void displayAllReports() {
        System.out.println("\n--- System Reports ---");
        courseManager.getAllCourses().forEach(c -> {
            System.out.println("Course: " + c.getCourseCode() + " | " + c.getTitle() + " | Instructor: " + c.getInstructorId());
            displayCourseRoster(c.getCourseCode());
        });
    }
}
