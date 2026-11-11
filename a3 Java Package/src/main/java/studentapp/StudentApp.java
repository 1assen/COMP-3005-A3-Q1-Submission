package studentapp;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDate;

public class StudentApp {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/assignment3";
    private static final String DB_USER = "postgres"; 
    private static final String DB_PASSWORD = "162534sql"; 

    public static void main(String[] args) {
        // initializes studentapp, runs the methods to demonstrate the functionality of the StudentApp class
        StudentApp app = new StudentApp();

        System.out.println("--- 1. Initial Student List ---");
        app.getAllStudents();

        System.out.print("Press Enter to continue..."); //after each step it pauses just so its easier to go through the output and see what is happening
        Scanner s = new Scanner(System.in);
        s.nextLine();

        System.out.println("\n--- 2. Adding a New Student ---");
        app.addStudent("Alice", "Johnson", "alice.johnson@example.com", LocalDate.of(2023, 9, 3));
        app.getAllStudents();


        System.out.print("Press Enter to continue...");
        s.nextLine();

        System.out.println("\n--- 3. Updating Student Email (ID: 1) ---");
        app.updateStudentEmail(1, "john.newemail@example.com");
        app.getAllStudents();

        System.out.print("Press Enter to continue...");
        s.nextLine();

        System.out.println("\n--- 4. Deleting a Student (ID: 3) ---");
        app.deleteStudent(3);
        app.getAllStudents();
    }
    //retrives and displays records from the student table
    public void getAllStudents() {
        String query = "SELECT student_id, first_name, last_name, email, enrollment_date FROM students";

        //connect to the database and executes query, with result statement given
        //all of the methods use this same try-catch block to handle SQL exceptions and ensure resources are closed properly so I will only comment on this one
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            //get each field, and print it
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date date = rs.getDate("enrollment_date");

                System.out.printf("ID: %d | Name: %s %s | Email: %s | Enrolled: %s%n",
                        id, firstName, lastName, email, date);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
    }

    //inserts a new student record into the students table
    public void addStudent(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollmentDate));

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added student: " + firstName + " " + lastName);
            }

        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    //updates the email with a specified student ID
    public void updateStudentEmail(int studentId, String newEmail) {
        String query = "UPDATE students SET email = ? WHERE student_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated email for student ID: " + studentId);
            } else {
                System.out.println("Student ID " + studentId + " not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating email: " + e.getMessage());
        }
    }

    //deletes stduent with the student id
    public void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            //check if the deletion worked and inform user of status
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully deleted student ID: " + studentId);
            } else {
                System.out.println("Student ID " + studentId + " not found.");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
}

