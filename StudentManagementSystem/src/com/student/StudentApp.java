package com.student;

import java.sql.*;
import java.util.Scanner;

public class StudentApp {

    // ✅ Change only these 3 if needed
    static final String URL  = "jdbc:postgresql://localhost:5432/student"; // or studentdb
    static final String USER = "postgres";
    static final String PASS = "root";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("✅ Connected Successfully!");

            while (true) {
                System.out.println("\n===== STUDENT MANAGEMENT =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student Course");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> addStudent(con, sc);
                    case 2 -> viewStudents(con);
                    case 3 -> updateStudentCourse(con, sc);
                    case 4 -> deleteStudent(con, sc);
                    case 5 -> {
                        System.out.println("👋 Thank you!");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Name: ");
        String name = sc.next();

        System.out.print("Email: ");
        String email = sc.next();

        System.out.print("Course: ");
        String course = sc.next();

        String sql = "INSERT INTO students(name,email,course) VALUES(?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Student Added!" : "❌ Not Added!");
        }
    }

    static void viewStudents(Connection con) throws SQLException {
        String sql = "SELECT id, name, email, course FROM students ORDER BY id";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nID | Name | Email | Course");
            System.out.println("--------------------------");

            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getString("course")
                );
            }
            if (!any) System.out.println("No records found.");
        }
    }

    static void updateStudentCourse(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        System.out.print("New Course: ");
        String newCourse = sc.next();

        String sql = "UPDATE students SET course=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newCourse);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Updated!" : "❌ ID not found!");
        }
    }

    static void deleteStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM students WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ Deleted!" : "❌ ID not found!");
        }
    }
}