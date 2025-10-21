import java.io.*;
import java.util.*;

// Step 1: Student class
class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;
    private int age;

    public Student(String name, String rollNumber, String grade, int age) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.age = age;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber +
               ", Name: " + name +
               ", Age: " + age +
               ", Grade: " + grade;
    }
}

// Step 2: StudentManagementSystem class
class StudentManagementSystem {
    private List<Student> students;
    private final String DATA_FILE = "students.dat";

    public StudentManagementSystem() {
        students = loadStudents();
    }

    // Add a student
    public void addStudent(Student student) {
        students.add(student);
        saveStudents();
        System.out.println("Student added successfully!");
    }

    // Remove a student by roll number
    public void removeStudent(String rollNumber) {
        boolean removed = students.removeIf(s -> s.getRollNumber().equalsIgnoreCase(rollNumber));
        if (removed) {
            saveStudents();
            System.out.println("Student removed successfully!");
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    // Search for a student by roll number
    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    // Display all students
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("\n--- Student List ---");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    // Save students to file
    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    // Load students from file
    @SuppressWarnings("unchecked")
    private List<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (List<Student>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>(); // Return empty list if file not found
        }
    }
}

// Step 3 & 5: User interface
public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // Add Student
                    System.out.print("Enter Roll Number: ");
                    String roll = sc.nextLine().trim();
                    while (roll.isEmpty()) {
                        System.out.print("Roll Number cannot be empty. Enter again: ");
                        roll = sc.nextLine().trim();
                    }

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine().trim();
                    while (name.isEmpty()) {
                        System.out.print("Name cannot be empty. Enter again: ");
                        name = sc.nextLine().trim();
                    }

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine(); // consume newline
                    while (age <= 0) {
                        System.out.print("Age must be positive. Enter again: ");
                        age = sc.nextInt();
                        sc.nextLine();
                    }

                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine().trim();
                    while (grade.isEmpty()) {
                        System.out.print("Grade cannot be empty. Enter again: ");
                        grade = sc.nextLine().trim();
                    }

                    Student student = new Student(name, roll, grade, age);
                    sms.addStudent(student);
                    break;

                case 2: // Remove Student
                    System.out.print("Enter Roll Number to remove: ");
                    String removeRoll = sc.nextLine();
                    sms.removeStudent(removeRoll);
                    break;

                case 3: // Search Student
                    System.out.print("Enter Roll Number to search: ");
                    String searchRoll = sc.nextLine();
                    Student found = sms.searchStudent(searchRoll);
                    if (found != null) {
                        System.out.println("Student found: " + found);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4: // Display All Students
                    sms.displayAllStudents();
                    break;

                case 5: // Exit
                    System.out.println("Exiting Student Management System.");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 5);

        sc.close();
    }
}
