package com.example.library;

import com.example.library.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("com/example/library/img/icon_book.png"));
        showLoginScene();
    }

    public void showLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            loader.setController(new LoginController(this));
            Parent root = loader.load();
            //primaryStage.getIcons().add(new javafx.scene.image.Image("/com/example/library/img/icon_book.png"));

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 700, 500));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            // Set controller if needed
            Parent root = loader.load();

            Stage mainStage = new Stage();
            mainStage.getIcons().add(new Image("/com/example/library/img/icon_book.png"));  // Set the icon
            mainStage.setTitle("Main View");
            mainStage.setScene(new Scene(root, 660, 400));
            mainStage.show();

            primaryStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
