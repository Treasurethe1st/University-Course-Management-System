package com.university.reports;

import com.university.models.Course;
import com.university.services.CourseManager;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentSummaryReport extends Report {

    private final String department;
    private final CourseManager courseManager;

    public DepartmentSummaryReport(String department, CourseManager courseManager) {
        super("Department Summary Report");
        this.department = department;
        this.courseManager = courseManager;
    }

    @Override
    public void generate() {
        printHeader();

        // Filter courses by department
        List<Course> courses = courseManager.getAllCourses().stream()
                .filter(c -> department.equals(c.getDepartment()))
                .collect(Collectors.toList());

        if (courses.isEmpty()) {
            System.out.println("No courses found in department: " + department);
            return;
        }

        System.out.println("--- Courses ---");
        for (Course c : courses) {
            System.out.println("- " + c.getCourseCode() + ": " + c.getEnrolledStudents().size() + " students");
        }
    }
}
