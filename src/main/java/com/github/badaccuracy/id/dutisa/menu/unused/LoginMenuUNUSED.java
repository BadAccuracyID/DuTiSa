package com.github.badaccuracy.id.dutisa.menu.unused;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;

public class LoginMenuUNUSED {

    private final Stage stage;
    private Scene scene;

    private TextField traineeNumber;
    private PasswordField password;

    private Label errorLabel;

    public LoginMenuUNUSED(Stage stage) {
        this.stage = stage;
        initUI();

        stage.setOnCloseRequest((event) -> System.exit(0));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initUI() {
        GridPane gridPane = new GridPane();

        traineeNumber = new TextField();
        traineeNumber.setOnAction(this.onSubmit());
        password = new PasswordField();
        password.setOnAction(this.onSubmit());

        Label traineeNumberLabel = new Label("Trainee Number");
        traineeNumberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gridPane.add(traineeNumberLabel, 0, 1);
        gridPane.add(traineeNumber, 1, 1);

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(password, 1, 2);


        Label titleLabel = new Label("Login");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(titleLabel, 0, 0);

        File backgroundFile = Utils.getFile("assets/images/gradient.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        gridPane.setBackground(new Background(background));

        HBox loginRegister = new HBox();
        loginRegister.setAlignment(Pos.CENTER);
        loginRegister.setSpacing(15);

        Button loginButton = new Button("Login");
        loginButton.setMinWidth(75);
        loginButton.setMinHeight(30);
        loginButton.setOnAction(this.onSubmit());
        loginRegister.getChildren().add(loginButton);

        Button registerButton = new Button("Register");
        registerButton.setMinWidth(75);
        registerButton.setMinHeight(30);
        registerButton.setOnAction(this.onRegister());
        loginRegister.getChildren().add(registerButton);

        gridPane.add(loginRegister, 1, 3);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(15);

        errorLabel = new Label();
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        errorLabel.setTextFill(Color.RED);
        new Insets(0, 0, 0, 0);
        gridPane.add(errorLabel, 1, 4);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("blue-purple-background");
        anchorPane.getStylesheets().add("/assets/css/style.css");
        anchorPane.setPrefWidth(500);
        anchorPane.setPrefHeight(600);
        anchorPane.applyCss();
        gridPane.add(anchorPane, 0, 5);

        scene = new Scene(gridPane, 1080, 720);
    }

    private EventHandler<ActionEvent> onSubmit() {
        return event -> {
            String username = this.traineeNumber.getText();
            String password = this.password.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please fill in all fields");
                DuTiSa.getInstance().getExecutorManager()
                        .gocExecutor("UI")
                        .submit(() -> {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).whenComplete((a, throwable) -> errorLabel.setText(""));
                return;
            }

            boolean canLogin = DuTiSa.getInstance().getTraineeManager().canLogin(username, password);
            if (!canLogin) {
                errorLabel.setText("Invalid username or password");
                DuTiSa.getInstance().getExecutorManager()
                        .gocExecutor("UI")
                        .submit(() -> {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).whenComplete((a, throwable) -> errorLabel.setText(""));
                return;
            }

            new MainMenuUNUSED(stage);
        };
    }

    private EventHandler<ActionEvent> onRegister() {
        return event -> {
            new RegisterMenuUNUSED(stage);
        };
    }

}
