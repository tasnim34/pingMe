
package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField recoveryQuestionField;

    @FXML
    private TextField answerField;

    @FXML
    private Button recoverButton;

    @FXML
    private Label errorMessage;

    @FXML
    private void recoverPasswordHandle() {
        // Get the entered values
        String email = emailField.getText();
        String recoveryQuestion = recoveryQuestionField.getText();
        String answer = answerField.getText();

        // Logic for recovering the password (replace this with real recovery logic)
        if (isValidRecovery(email, recoveryQuestion, answer)) {
            errorMessage.setVisible(false);  // Hide error if recovery is successful
            System.out.println("Password recovery successful!");
            // You can add more functionality here, like sending a recovery email
        } else {
            errorMessage.setVisible(true);  // Show error if recovery fails
            errorMessage.setText("Invalid details! Please check your email and recovery question.");
        }
    }

    private boolean isValidRecovery(String email, String recoveryQuestion, String answer) {
        // For demonstration, assume the correct details
        String validEmail = "user@example.com";
        String validQuestion = "What is your favorite color?";
        String validAnswer = "blue";

        // Check if the entered values match the predefined valid ones
        return email.equals(validEmail) && recoveryQuestion.equals(validQuestion) && answer.equals(validAnswer);
    }

    @FXML
    private void backToSignIn() {
        System.out.println("Redirecting to Sign-In page...");
        // Logic to switch to Sign-In page
    }
}