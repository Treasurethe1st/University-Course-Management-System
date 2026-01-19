# University Management System (OOP Project)
Uwayezu Jean Marie Tresor(29807/2025)  - User Management (Component 1)
Uwayezu Jean Marie Tresor(29807/2025)  - User Management (Component 1)
Hozifa Adam(29677/2025)                - Course Management (Component 2)
Aurore UYISENGA(30095/2025)            - Enrollment System (Component 3)
Ruyange Adelin(20553/2022)             - Grade Management (Component 4)
Allogo allogo(30588/2025)              - Notification System (Component 5)
ISHIMWE Geraldine(30699/2025)          - Reporting System (Component 6)
Albine UWINEZA(32364/2025)             - Authentication & Data Persistence (Components 7 & 8)

A Java console-based University Management System demonstrating Object-Oriented Programming concepts.  

This system manages students, instructors, courses, enrollments, and grades with role-based access (Admin, Instructor, Student).

---

## 1. How to Compile and Run

### Using Bash (Linux / macOS)
```bash
# Make sure you are in the project root
./compile.sh   # Compile all Java files
./run.sh       # Run the program

Using Windows Command Prompt
compile.bat    # Compile all Java files
run.bat        # Run the program


Requirements:

JDK 11 or higher

Git (optional, for cloning)

Java IDE (optional)

2. OOP Concepts Demonstrated
2.1 Inheritance

User is the base class

Student, Instructor, Admin inherit from User

public class Instructor extends User {
    private String department;
    // ...
}

2.2 Encapsulation

Fields are private with getters/setters (where needed)

Sensitive data like passwords are protected

private String password;
public boolean checkPassword(String pass) { return this.password.equals(pass); }

2.3 Polymorphism

displayInfo() method is overridden in each subclass

User u = new Student(...);
u.displayInfo();  // Calls Student version

2.4 Abstraction

User is abstract: forces subclasses to implement getRole() and displayInfo()

3. Features Implemented
Admin

Manage users (add, delete)

Create, update, delete courses

View system-wide reports (all courses, all grades)

Send announcements to all users

Instructor

View assigned courses

View course rosters

Assign grades to students

View teaching load

Student

Browse available courses

Enroll in courses (checks prerequisites)

Drop courses

View grades and transcript

4. Design Decisions

Role-based design: separate menus for Admin, Instructor, Student

Persistence: Java Serializable used to save/load users, courses, enrollments, grades

Modular classes: Managers for each functionality (UserManager, CourseManager, EnrollmentManager, GradeManager, GPAService)

OOP-first approach: clear abstraction and inheritance hierarchy for easy future extensions

CLI-based interface: simple, text-based menus for testing

5. Sample Workflow

Login as Admin

Add a new Instructor or Student

Create a new Course and assign it to an Instructor

Login as Student

Browse courses and enroll in one

Login as Instructor

Assign grade to the enrolled student

Login as Student

View grades and GPA

6. Project Structure
UniversityManagementSystem/
│
├─ src/                # Java source files
├─ lib/                # External libraries (if any)
├─ compile.sh / compile.bat
├─ run.sh / run.bat
├─ .gitignore          # Ignore build/dist/test folders
└─ README.md

7. Notes

Preloaded users (for testing):

S001 | student1 | pass123
I001 | prof1    | profpass
A001 | admin    | admin123


Preloaded courses: CS101, MATH101, CS201

Bin folders and compiled files are ignored in Git (build/, dist/, test/)
