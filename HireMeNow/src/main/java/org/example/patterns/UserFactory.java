package org.example.patterns;

import org.example.models.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserFactory {

    private static final DatabaseConnection db = DatabaseConnection.getInstance();

    public static User createUser(String role, String name, String email) {
        User user = switch (role.toLowerCase()) {
            case "student" -> new Student(name, email);
            case "employer" -> new Employer(name, email);
            case "admin" -> new Admin(name, email);
            default -> throw new IllegalArgumentException("Invalid user role: " + role);
        };

        int generatedId = saveUserToDb(user);
        user.setId(generatedId);

        return user;
    }

    private static int saveUserToDb(User user) {
        String sql = "INSERT INTO users (name, email, role) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getRole());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Failed to save user");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("[DB] Saved user: " + user.getName() + " (ID: " + id + ")");
                    return id;
                } else {
                    throw new RuntimeException("No ID generated");
                }
            }
        } catch (Exception e) {
            System.err.println("[DB] Error saving user: " + e.getMessage());
            return -1;
        }
    }
}