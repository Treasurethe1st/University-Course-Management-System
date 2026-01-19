package com.university.reports;

import com.university.models.Instructor;

import java.util.List;

public class TeachingLoad extends Report {

    private Instructor instructor;

    public TeachingLoad(Instructor instructor) {
        super("Teaching Load");
        this.instructor = instructor;
    }

    @Override
    public void generate() {
        System.out.println("\n--- Teaching Load for " + instructor.getFullName() + " ---");
        List<String> courses = instructor.getCoursesTaught();
        if (courses.isEmpty()) System.out.println("No courses assigned.");
        else courses.forEach(System.out::println);
    }
}
