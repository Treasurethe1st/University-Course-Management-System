package com.university.reports;

import com.university.models.Course;

public class CourseRosterReport extends Report {

    private Course course;

    public CourseRosterReport(Course course) {
        super("Course Roster: " + course.getCourseCode());
        this.course = course;
    }

    @Override
    public void generate() {
        printHeader();

        if (course.getEnrolledStudents().isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        for (String studentId : course.getEnrolledStudents()) {
            System.out.println("Student ID: " + studentId);
        }
    }
}
