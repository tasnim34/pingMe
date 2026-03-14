package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class SignInController {

    // FXML components from SignIn.fxml
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label errorMessage; // Label to display error message

    // Method called when the user clicks on the "Sign In" button
    @FXML
    private void signInHandle() {
        // Get the email and password from the user input
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate credentials (for demonstration, using hardcoded values)
        if (isValidCredentials(email, password)) {
            errorMessage.setVisible(false);  // Hide error message
            // Proceed with successful login (e.g., navigate to another scene or dashboard)
            System.out.println("Login successful!");
            // You can perform other actions here, such as transitioning to a new scene or dashboard.
        } else {
            errorMessage.setVisible(true);  // Show error message if credentials are invalid
        }
    }

    // Simple method to validate user credentials
    // (Replace this with your actual credential checking logic)
    private boolean isValidCredentials(String email, String password) {
        // For this example, we assume these are the correct credentials
        String validEmail = "user@example.com";
        String validPassword = "password123";

        // Check if entered email and password match the valid credentials
        return email.equals(validEmail) && password.equals(validPassword);
    }
}