package com.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DbTest {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/student";
        String user = "postgres";
        String pass = "root";

        Scanner sc = new Scanner(System.in);

        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            System.out.println("Connected Successfully 🔥");

            System.out.print("Enter Name: ");
            String name = sc.next();

            System.out.print("Enter Email: ");
            String email = sc.next();

            System.out.print("Enter Course: ");
            String course = sc.next();

            String sql = "INSERT INTO students(name,email,course) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, course);

            ps.executeUpdate();

            System.out.println("Student Added Successfully ✔");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}