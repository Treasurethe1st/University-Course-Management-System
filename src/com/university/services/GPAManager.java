package com.university.services;

import com.university.models.Grade;

import java.util.List;

public class GPAManager {

    public double calculateGPA(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0.0;
        int courseCount = 0;

        for (Grade grade : grades) {
            totalPoints += grade.getPoints();
            courseCount++;
        }

        return Math.round((totalPoints / courseCount) * 100.0) / 100.0;
    }
}
