package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField recoveryQuestionField;

    @FXML
    private TextField recoveryAnswerField;

    @FXML
    private Button signUpButton;

    @FXML
    public void SignUpHandle() throws IOException {
        // Form Validation
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty() ||
            passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() ||
            recoveryQuestionField.getText().isEmpty() || recoveryAnswerField.getText().isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        // Password and Confirm Password validation
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        // Placeholder logic for SignUp
        showAlert("Success", "Sign Up Successful!");

        // If sign-up is successful, navigate to Home Page (or any other page)
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Home.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}