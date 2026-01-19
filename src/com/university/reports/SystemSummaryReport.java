package com.university.reports;

import com.university.services.CourseManager;
import com.university.services.UserManager;

public class SystemSummaryReport extends Report {

    private UserManager userManager;
    private CourseManager courseManager;

    public SystemSummaryReport(UserManager userManager, CourseManager courseManager) {
        super("System Summary Report");
        this.userManager = userManager;
        this.courseManager = courseManager;
    }

    @Override
    public void generate() {
        printHeader();

        System.out.println("Total Users: " + userManager.getAllUsers().size());
        System.out.println("Total Courses: " + courseManager.getAllCourses().size());
    }
}
