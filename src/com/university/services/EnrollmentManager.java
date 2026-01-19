package com.university.services;

import com.university.models.Course;
import com.university.notifications.InAppNotification;

import java.io.*;
import java.util.*;

public class EnrollmentManager implements Serializable {

    private static final int MAX_CREDITS = 21;
    private final String FILE_PATH = "data/enrollments.ser";

    private Map<String, List<String>> enrollments; // studentId -> courseCodes
    private CourseManager courseManager;
    private NotificationManager notificationManager;

    public EnrollmentManager(CourseManager courseManager) {
        this.courseManager = courseManager;
        this.enrollments = new HashMap<>();
        this.notificationManager = new NotificationManager();
        loadEnrollments();
    }

    // ===================== ENROLL =====================
    public boolean enroll(String studentId, String courseCode) {
        Course course = courseManager.findByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return false;
        }

        List<String> studentCourses = enrollments.computeIfAbsent(studentId, k -> new ArrayList<>());

        if (studentCourses.contains(courseCode)) {
            System.out.println("Already enrolled in this course.");
            return false;
        }

        // prerequisites
        for (String pre : course.getPrerequisites()) {
            if (!studentCourses.contains(pre)) {
                System.out.println("Missing prerequisite: " + pre);
                return false;
            }
        }

        if (course.isFull()) {
            System.out.println("Course is full.");
            return false;
        }

        if (calculateTotalCredits(studentCourses) + course.getCredits() > MAX_CREDITS) {
            System.out.println("Credit limit exceeded (max " + MAX_CREDITS + ").");
            return false;
        }

        course.enrollStudent(studentId);
        studentCourses.add(courseCode);

        saveEnrollments();
        courseManager.saveCourses();

        // Notify student
        notificationManager.notify(new InAppNotification(studentId, "Enrolled in course " + courseCode));

        System.out.println("Enrollment successful.");
        return true;
    }

    // ===================== DROP =====================
    public boolean drop(String studentId, String courseCode) {
        Course course = courseManager.findByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return false;
        }

        List<String> studentCourses = enrollments.get(studentId);
        if (studentCourses == null || !studentCourses.contains(courseCode)) {
            System.out.println("You are not enrolled in this course.");
            return false;
        }

        course.dropStudent(studentId);
        studentCourses.remove(courseCode);

        saveEnrollments();
        courseManager.saveCourses();

        notificationManager.notify(new InAppNotification(studentId, "Dropped course " + courseCode));

        System.out.println("Course dropped successfully.");
        return true;
    }

    public List<String> getStudentCourses(String studentId) {
        return enrollments.getOrDefault(studentId, new ArrayList<>());
    }

    private int calculateTotalCredits(List<String> courseCodes) {
        int sum = 0;
        for (String code : courseCodes) {
            Course c = courseManager.findByCode(code);
            if (c != null) sum += c.getCredits();
        }
        return sum;
    }

    // ===================== PERSISTENCE =====================
    public void saveEnrollments() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(enrollments);
        } catch (IOException e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadEnrollments() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj = in.readObject();
            if (obj instanceof Map) {
                enrollments = (Map<String, List<String>>) obj;
            } else {
                enrollments = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
            enrollments = new HashMap<>();
        }
    }
}
