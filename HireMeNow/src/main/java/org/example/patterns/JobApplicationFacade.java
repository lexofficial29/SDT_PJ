package org.example.patterns;

import org.example.models.*;

public class JobApplicationFacade {
    private final DatabaseConnection db = DatabaseConnection.getInstance();

    public void apply(Student student, Job job) {
        Application app = new Application(student, job);
        app.submit();

        String sql = "INSERT INTO applications (student_id, job_id, status) VALUES (?, ?, 'Submitted')";
        try (var pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, job.getTitle());
            pstmt.executeUpdate();
            System.out.println("[DB] Application saved with student ID: " + student.getId());
        } catch (Exception e) {
            System.err.println("[DB] Apply error: " + e.getMessage());
        }
    }

    public void withdraw(Student student, Job job) {
        System.out.println(student.getName() + " withdrew application for " + job.getTitle());

        String sql = "DELETE FROM applications WHERE student_id = ? AND job_id = ?";
        try (var pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, job.getTitle());
            pstmt.executeUpdate();
            System.out.println("[DB] Application withdrawn (student ID: " + student.getId() + ")");
        } catch (Exception e) {
            System.err.println("[DB] Withdraw error: " + e.getMessage());
        }
    }
}