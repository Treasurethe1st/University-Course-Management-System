package com.university;

import com.university.auth.AuthenticationService;
import com.university.models.Course;
import com.university.models.Grade;
import com.university.models.User;
import com.university.services.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static AuthenticationService authService = new AuthenticationService();
    private static Scanner scanner = new Scanner(System.in);

    // Managers
    private static UserManager userManager = new UserManager();
    private static CourseManager courseManager = new CourseManager();
    private static EnrollmentManager enrollmentManager = new EnrollmentManager(courseManager);
    private static GradeManager gradeManager = new GradeManager();
    private static GPAService gpaService = new GPAService();

    public static void main(String[] args) {

        // Load saved data
        userManager.loadUsers();
        courseManager.loadCourses();
        enrollmentManager.loadEnrollments();
        gradeManager.loadGrades();

        // Preload specific users
        if (userManager.getAllUsers().isEmpty()) {
            userManager.addUser(new com.university.models.Student(
                    "S001", "student1", "pass123", "student1@university.edu", "John Doe", "CS"));

            userManager.addUser(new com.university.models.Instructor(
                    "I001", "prof1", "profpass", "prof1@university.edu", "Dr. Jane Smith", "Computer Science"));

            userManager.addUser(new com.university.models.Admin(
                    "A001", "admin", "admin123", "admin@university.edu", "Uwayezu Tresor", "IT", List.of("ALL"))
            );

            userManager.saveUsers();
        }

        // Preload sample courses
        if (courseManager.getAllCourses().isEmpty()) {
            courseManager.addCourse(new Course("CS101", "Intro to Programming", 3, "prof1", 30, null, "CS"));
            courseManager.addCourse(new Course("MATH101", "Calculus I", 4, "prof1", 40, null, "Math"));
            courseManager.addCourse(new Course("CS201", "Data Structures", 3, "prof1", 25, List.of("CS101"), "CS"));
            courseManager.saveCourses();
        }

        // Main loop
        while (true) {
            if (!authService.isLoggedIn()) loginMenu();
            else roleMenu();
        }
    }

    // ================= LOGIN =================
    private static void loginMenu() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (!authService.login(username, password)) {
            System.out.println("Invalid username or password!");
        }
    }

    // ================= ROLE MENU =================
    private static void roleMenu() {
        User current = authService.getCurrentUser();
        String role = current.getRole();
        System.out.println("\nLogged in as: " + role);
        current.displayInfo();

        switch (role) {
            case "STUDENT" -> studentMenu();
            case "INSTRUCTOR" -> instructorMenu();
            case "ADMIN" -> adminMenu();
            default -> System.out.println("Unknown role!");
        }
    }

    // ================= STUDENT MENU =================
    private static void studentMenu() {
        String studentUsername = authService.getCurrentUser().getUsername();
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. Browse Courses");
            System.out.println("2. Enroll in Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Grades / Transcript");
            System.out.println("5. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> courseManager.displayAllCourses();

                case "2" -> {
                    System.out.print("Course Code to enroll: ");
                    String code = scanner.nextLine();
                    if (enrollmentManager.enroll(studentUsername, code)) {
                        enrollmentManager.saveEnrollments();
                        System.out.println("GPA: " + gpaService.calculateGPA(studentUsername, gradeManager));
                    }
                }

                case "3" -> {
                    System.out.print("Course Code to drop: ");
                    String code = scanner.nextLine();
                    if (enrollmentManager.drop(studentUsername, code)) {
                        enrollmentManager.saveEnrollments();
                        System.out.println("GPA: " + gpaService.calculateGPA(studentUsername, gradeManager));
                    }
                }

                case "4" -> gradeManager.displayStudentGrades(studentUsername);

                case "5" -> {
                    authService.logout();
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= INSTRUCTOR MENU =================
    private static void instructorMenu() {
        String instructorUsername = authService.getCurrentUser().getUsername();
        while (true) {
            System.out.println("\n--- Instructor Menu ---");
            System.out.println("1. View My Courses");
            System.out.println("2. View Course Roster");
            System.out.println("3. Assign Grade");
            System.out.println("4. View Teaching Load");
            System.out.println("5. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> courseManager.getAllCourses().stream()
                        .filter(c -> c.getInstructorId().equals(instructorUsername))
                        .forEach(Course::displayCourse);

                case "2" -> {
                    System.out.print("Course Code: ");
                    String code = scanner.nextLine();
                    Course course = courseManager.findByCode(code);
                    if (course != null && course.getInstructorId().equals(instructorUsername)) {
                        System.out.println("Enrolled Students: " + course.getEnrolledStudents());
                    } else {
                        System.out.println("Invalid course or not assigned to you.");
                    }
                }

                case "3" -> {
                    System.out.print("Student Username: ");
                    String studentUsername = scanner.nextLine();
                    System.out.print("Course Code: ");
                    String courseCode = scanner.nextLine();
                    System.out.print("Score: ");
                    double score = Double.parseDouble(scanner.nextLine());

                    Course course = courseManager.findByCode(courseCode);
                    if (course == null) {
                        System.out.println("Course not found!");
                        break;
                    }
                    Grade grade = new Grade(studentUsername, courseCode, score, course.getCredits());
                    gradeManager.assignGrade(grade);
                    gradeManager.saveGrades();
                    System.out.println("Grade assigned.");
                }

                case "4" -> {
                    System.out.println("Teaching Load:");
                    courseManager.getAllCourses().stream()
                            .filter(c -> c.getInstructorId().equals(instructorUsername))
                            .forEach(c -> System.out.println(c.getCourseCode() + " | " + c.getTitle()));
                }

                case "5" -> {
                    authService.logout();
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= ADMIN MENU =================
    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Users");
            System.out.println("2. Add User");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Manage Courses");
            System.out.println("6. View Reports");
            System.out.println("7. Send Announcement");
            System.out.println("8. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> userManager.displayAllUsers();
                case "2" -> addUserMenu();
                case "3" -> updateUserMenu();
                case "4" -> {
                    System.out.print("Username to delete: ");
                    String username = scanner.nextLine();
                    if (userManager.removeUser(username)) System.out.println("User deleted.");
                    else System.out.println("User not found.");
                }
                case "5" -> manageCoursesMenu();
                case "6" -> {
                    System.out.println("--- System Reports ---");
                    courseManager.displayAllCourses();
                    gradeManager.displayAllGrades();
                }
                case "7" -> {
                    System.out.print("Enter announcement message: ");
                    String msg = scanner.nextLine();
                    System.out.println("Announcement sent to all users: " + msg);
                }
                case "8" -> {
                    authService.logout();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= ADMIN HELPERS =================
    private static void addUserMenu() {
        System.out.print("Role (ADMIN / INSTRUCTOR / STUDENT): ");
        String role = scanner.nextLine().toUpperCase();

        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        switch (role) {
            case "ADMIN" -> userManager.addUser(new com.university.models.Admin(
                    id, username, password, email, name, "IT", List.of("ALL")));
            case "INSTRUCTOR" -> {
                System.out.print("Department: ");
                String dept = scanner.nextLine();
                userManager.addUser(new com.university.models.Instructor(
                        id, username, password, email, name, dept));
            }
            case "STUDENT" -> {
                System.out.print("Program: ");
                String program = scanner.nextLine();
                userManager.addUser(new com.university.models.Student(
                        id, username, password, email, name, program));
            }
            default -> System.out.println("Invalid role.");
        }
        userManager.saveUsers();
        System.out.println("User added successfully.");
    }

    private static void updateUserMenu() {
        System.out.print("Username to update: ");
        String username = scanner.nextLine();
        User user = userManager.findByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("New Email (leave blank to keep): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) user.setEmail(email);

        System.out.print("New Password (leave blank to keep): ");
        String pass = scanner.nextLine();
        if (!pass.isEmpty()) user.setPassword(pass);

        userManager.saveUsers();
        System.out.println("User updated successfully.");
    }

    private static void manageCoursesMenu() {
        while (true) {
            System.out.println("\n--- Manage Courses ---");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Delete Course");
            System.out.println("4. Back");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Credits: ");
                    int credits = Integer.parseInt(scanner.nextLine());
                    System.out.print("Instructor Username: ");
                    String instr = scanner.nextLine();
                    System.out.print("Capacity: ");
                    int cap = Integer.parseInt(scanner.nextLine());
                    System.out.print("Department: ");
                    String dept = scanner.nextLine();

                    courseManager.addCourse(new Course(code, title, credits, instr, cap, null, dept));
                    courseManager.saveCourses();
                    System.out.println("Course added!");
                }
                case "2" -> {
                    System.out.print("Course Code to update: ");
                    String code = scanner.nextLine();
                    Course c = courseManager.findByCode(code);
                    if (c == null) {
                        System.out.println("Course not found.");
                        break;
                    }
                    System.out.print("New Title (leave blank to keep): ");
                    String title = scanner.nextLine();
                    System.out.print("Credits (0 to keep current): ");
                    int credits = Integer.parseInt(scanner.nextLine());
                    System.out.print("Instructor Username (leave blank to keep): ");
                    String instr = scanner.nextLine();
                    System.out.print("Capacity (0 to keep current): ");
                    int cap = Integer.parseInt(scanner.nextLine());
                    System.out.print("Department (leave blank to keep): ");
                    String dept = scanner.nextLine();

                    if (!title.isEmpty()) c.setTitle(title);
                    if (credits > 0) c.setCredits(credits);
                    if (!instr.isEmpty()) c.setInstructorId(instr);
                    if (cap > 0) c.setCapacity(cap);
                    if (!dept.isEmpty()) c.setDepartment(dept);

                    courseManager.saveCourses();
                    System.out.println("Course updated!");
                }
                case "3" -> {
                    System.out.print("Course Code to delete: ");
                    String code = scanner.nextLine();
                    if (courseManager.removeCourse(code)) System.out.println("Course deleted!");
                    else System.out.println("Course not found.");
                }
                case "4" -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
