package org.example.models;

import org.example.patterns.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Job {
    private int id;
    private String title;
    private String description;
    private double payRate;
    private String category;

    public Job(String title, String category, double payRate) {
        this.title = title;
        this.category = category;
        this.payRate = payRate;
        this.id = saveToDatabase();
    }

    private int saveToDatabase() {
        String sql = "INSERT INTO jobs (title, category, pay_rate) VALUES (?, ?, ?)";
        try (var conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, title);
            pstmt.setString(2, category);
            pstmt.setDouble(3, payRate);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("[DB] Saved job: " + title + " (ID: " + generatedId + ")");
                    return generatedId;
                }
            }
        } catch (Exception e) {
            System.err.println("[DB] Failed to save job: " + e.getMessage());
        }
        return -1;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public double getPayRate() { return payRate; }

    @Override
    public String toString() {
        return title + " (" + category + ", $" + payRate + ")";
    }
}