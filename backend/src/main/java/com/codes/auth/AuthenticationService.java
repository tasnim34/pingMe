package com.codes.auth;

import com.codes.database.DatabaseManager;
import com.codes.model.User;
import com.codes.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {

    // Signup (create a new user)
    public boolean signUp(String firstName, String lastName,
                          String email, String password, String confirmPassword,
                          String recoveryQuestion, String recoveryAnswer) {

        // Check if the passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return false;
        }

        // Encrypt the password and recovery answer
        String encryptedPassword = PasswordUtil.encryptPassword(password);
        String encryptedRecoveryAnswer = PasswordUtil.encryptPassword(recoveryAnswer);

        String sql = """
            INSERT INTO Users (firstName, lastName, email, password, 
                               recoveryQuestion, recoveryAnswer)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, encryptedPassword);
            pstmt.setString(5, recoveryQuestion);
            pstmt.setString(6, encryptedRecoveryAnswer);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Account created: " + email);
                return true;
            }
            return false;

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE") || e.getMessage().contains("PRIMARY KEY")) {
                System.out.println("An account with this email already exists!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    // Login check
    public User login(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (PasswordUtil.checkPassword(password, storedPassword)) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    return user;
                }
            }
            return null; // wrong email or password

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Step 1: Get recovery question using email
    public String getRecoveryQuestion(String email) {
        String sql = "SELECT recoveryQuestion FROM Users WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("recoveryQuestion");
            }
            return null; // email not found

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Step 2: Check answer and set new password + confirm password
    public boolean resetPasswordByQuestion(String email, String answer,
                                           String newPassword, String confirmNewPassword) {

        // First check: do new password and confirm password match?
        if (!newPassword.equals(confirmNewPassword)) {
            System.out.println("New password and confirm password do not match!");
            return false;
        }

        // Check the recovery answer
        String sql = "SELECT recoveryAnswer FROM Users WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedEncryptedAnswer = rs.getString("recoveryAnswer");
                String encryptedInputAnswer = PasswordUtil.encryptPassword(answer);

                if (encryptedInputAnswer.equals(storedEncryptedAnswer)) {
                    // Answer matched → encrypt and save new password
                    String encryptedNewPass = PasswordUtil.encryptPassword(newPassword);

                    String updateSql = "UPDATE Users SET password = ? WHERE email = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, encryptedNewPass);
                        updateStmt.setString(2, email);
                        updateStmt.executeUpdate();
                    }

                    System.out.println("Password reset successful: " + email);
                    return true;
                } else {
                    System.out.println("Recovery answer did not match!");
                    return false;
                }
            }
            System.out.println("Email not found");
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Test main
    public static void main(String[] args) {
        AuthenticationService auth = new AuthenticationService();

        boolean success = auth.signUp(
                "Tasnim",
                "Zannat",
                "sara@gmail.com",
                "MyPass123",
                "MyPass123",
                "What is your pet name?",
                "Tom"
        );

        System.out.println("Signup successful: " + success);

        User loggedIn = auth.login("sara@gmail.com", "MyPass123");
        if (loggedIn != null) {
            System.out.println("Login successful! Welcome " + loggedIn.getFirstName());
        } else {
            System.out.println("Login failed");
        }
    }
}
