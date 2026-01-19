package com.university.services;

import com.university.models.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private static final String FILE_PATH = "data/courses.ser";
    private List<Course> courses;

    public CourseManager() {
        courses = new ArrayList<>();
    }

    // ================= CREATE =================
    public boolean addCourse(Course course) {
        if (findByCode(course.getCourseCode()) != null) {
            System.out.println("Course already exists.");
            return false;
        }
        courses.add(course);
        saveCourses();
        return true;
    }

    // ================= READ =================
    public Course findByCode(String courseCode) {
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(courseCode)) {
                return c;
            }
        }
        return null;
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course c : courses) {
            c.displayCourse();
        }
    }

    // ================= UPDATE =================
    public boolean updateCourse(
            String courseCode,
            String newTitle,
            Integer newCredits,
            String newInstructorId,
            Integer newCapacity,
            String newDepartment  // <-- added
    ) {
        Course course = findByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return false;
        }

        if (newTitle != null) course.setTitle(newTitle);
        if (newCredits != null) course.setCredits(newCredits);
        if (newInstructorId != null) course.setInstructorId(newInstructorId);
        if (newCapacity != null) course.setCapacity(newCapacity);
        if (newDepartment != null) course.setDepartment(newDepartment); // <-- added

        saveCourses();
        return true;
    }

    // ================= DELETE =================
    public boolean removeCourse(String courseCode) {
        Course course = findByCode(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
            return false;
        }
        courses.remove(course);
        saveCourses();
        return true;
    }

    // ================= REPORT HELPERS =================
    public List<Course> getCoursesByInstructor(String instructorId) {
        List<Course> result = new ArrayList<>();
        for (Course c : courses) {
            if (c.getInstructorId().equals(instructorId)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Course> getCoursesByDepartment(String department) {  // <-- added
        List<Course> result = new ArrayList<>();
        for (Course c : courses) {
            if (department.equals(c.getDepartment())) {
                result.add(c);
            }
        }
        return result;
    }

    // ================= PERSISTENCE =================
    public void saveCourses() {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            out.writeObject(courses);
            out.close();
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadCourses() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH));
            courses = (List<Course>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }
}
