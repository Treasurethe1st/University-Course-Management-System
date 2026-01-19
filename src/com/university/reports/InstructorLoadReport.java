package com.university.reports;

import com.university.models.Course;
import com.university.services.CourseManager;

public class InstructorLoadReport extends Report {

    private String instructorId;
    private CourseManager courseManager;

    public InstructorLoadReport(String instructorId, CourseManager courseManager) {
        super("Instructor Teaching Load");
        this.instructorId = instructorId;
        this.courseManager = courseManager;
    }

    @Override
    public void generate() {
        printHeader();

        int count = 0;

        for (Course c : courseManager.getAllCourses()) {
            if (c.getInstructorId().equals(instructorId)) {
                c.displayCourse();
                count++;
            }
        }

        System.out.println("\nTotal Courses: " + count);
    }
}
