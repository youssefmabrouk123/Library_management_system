package com.example.library.controller;

import com.example.library.Main;
import com.example.library.database.DatabaseUtil;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.sql.*;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label lblMessage;
    private final Main mainApp;

    public LoginController(Main mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private void handleLoginButtonAction() {
        // Implement your login logic here
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection != null) {
                DatabaseMetaData metadata = connection.getMetaData();
                ResultSet tables = metadata.getTables(null, null, "admin", null);

                if (tables.next()) {
                    // admin table exists
                    String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, password);

                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            // Login successful
                            lblMessage.setText("Login successful!");
                            mainApp.showMainView();
                        } else {
                            lblMessage.setText("Invalid credentials. Please try again.");
                        }
                    }
                } else {
                    // admin table does not exist
                    lblMessage.setText("Table 'admin' does not exist in the database.");
                }
            } else {
                lblMessage.setText("Unable to establish a connection to the database.");
            }
        } catch (SQLException e) {
            lblMessage.setText("Error during login. Please try again.");
            e.printStackTrace();
        }
    }
}
