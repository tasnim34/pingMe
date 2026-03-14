
package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class ResetPasswordController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label passwordStrengthLabel;

    @FXML
    private Button resetPasswordButton;

    @FXML
    private Label errorMessage;

    @FXML
    private void resetPasswordHandle() {
        // Get the entered passwords
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate the passwords (ensure both fields match)
        if (newPassword.equals(confirmPassword)) {
            errorMessage.setVisible(false);  // Hide error message if passwords match
            System.out.println("Password reset successful!");
            // You can add more functionality here, like saving the new password in the database
        } else {
            errorMessage.setVisible(true);  // Show error if passwords don't match
            errorMessage.setText("Passwords do not match. Please try again.");
        }
    }

    @FXML
    private void backToSignIn() {
        System.out.println("Redirecting to Sign-In page...");
        // Logic to switch to Sign-In page
    }

    // Optional: You can implement logic to evaluate password strength here
    private void evaluatePasswordStrength(String password) {
        // Basic logic to determine password strength
        if (password.length() >= 8) {
            passwordStrengthLabel.setText("Password Strength: Strong");
        } else if (password.length() >= 5) {
            passwordStrengthLabel.setText("Password Strength: Medium");
        } else {
            passwordStrengthLabel.setText("Password Strength: Weak");
        }
    }

    @FXML
    private void initialize() {
        // This method can be used to add initial logic (e.g., password strength evaluation)
        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            evaluatePasswordStrength(newValue);
        });
    }
}