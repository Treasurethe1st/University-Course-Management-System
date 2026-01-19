package com.university.services;

import com.university.models.User;
import com.university.models.Student;
import com.university.models.Instructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String FILE_PATH = "data/users.ser";
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
    }

    // ================= USER OPERATIONS =================

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    // 🔹 Use USERNAME as the system identifier
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public boolean removeUser(String username) {
        User user = findByUsername(username);
        if (user != null) {
            users.remove(user);
            saveUsers();
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return users;
    }

    // ================= ROLE FILTERS =================

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Student) {
                students.add((Student) u);
            }
        }
        return students;
    }

    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Instructor) {
                instructors.add((Instructor) u);
            }
        }
        return instructors;
    }

    // ================= DISPLAY =================

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User u : users) {
            System.out.println(
                    u.getRole()
                    + " | Username: " + u.getUsername()
                    + " | Name: " + u.getFullName()
            );
        }
    }

    // ================= PERSISTENCE =================

    public void saveUsers() {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(FILE_PATH)
            );
            out.writeObject(users);
            out.close();

        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(FILE_PATH)
            );
            users = (List<User>) in.readObject();
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}
