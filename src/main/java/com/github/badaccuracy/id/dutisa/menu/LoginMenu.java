package com.github.badaccuracy.id.dutisa.menu;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class LoginMenu {

    private final Stage stage;
    private Scene scene;

    private TextField usernameField;
    private PasswordField passwordField;


    private Label errorLabel;

    public LoginMenu(Stage stage) {
        this.stage = stage;
        initUI();

        AtomicReference<Double> x = new AtomicReference<>(0.0);
        AtomicReference<Double> y = new AtomicReference<>(0.0);
        scene.setOnMousePressed(event -> {
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x.get());
            stage.setY(event.getScreenY() - y.get());
        });
        scene.setFill(Color.TRANSPARENT);

        stage.setOnCloseRequest((event) -> System.exit(0));
        stage.setScene(scene);
        stage.setTitle("NAR 23-1");
        stage.setResizable(false);
        stage.show();
    }

    private void initUI() {
        StackPane rootPane = new StackPane();
        rootPane.setStyle("-fx-background-color: transparent;");

        String cssFile = "assets/css/style.css";
        File file = Utils.getFile(cssFile);
        String styleCss = file.toURI().toString();

        AnchorPane loginPane = new AnchorPane();
        loginPane.setStyle("-fx-background-color: transparent;");
        loginPane.getStyleClass().add("blue-purple-gradient");
        loginPane.getStylesheets().add(styleCss);
        loginPane.applyCss();
        rootPane.getChildren().add(loginPane);

        // left
        AnchorPane leftPane = new AnchorPane();
        leftPane.setLayoutY(-4.0);
        leftPane.setPrefWidth(500);
        leftPane.setPrefHeight(600);
        leftPane.getStylesheets().add(styleCss);
        leftPane.getStyleClass().add("blue-purple-gradient");
        leftPane.applyCss();

        VBox imageBox = new VBox();
        imageBox.setLayoutY(70);
        imageBox.setLayoutX(120);
        ImageView imageView = new ImageView();
        File logoFile = Utils.getFile("assets/images/logo.png");
        imageView.setImage(new Image(logoFile.toURI().toString()));
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(272);
        imageView.setFitHeight(272);

        imageBox.getChildren().add(imageView);
        leftPane.getChildren().add(imageBox);

        Label narlabel = new Label("NAR 23-1");
        narlabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 28));
        narlabel.setTextFill(Color.WHITE);
        narlabel.setAlignment(Pos.CENTER);
        narlabel.setContentDisplay(ContentDisplay.CENTER);
        HBox labelBox = new HBox(narlabel);
        labelBox.setLayoutX(190);
        labelBox.setLayoutY(390);
        labelBox.setAlignment(Pos.CENTER);
        leftPane.getChildren().add(labelBox);

        Label mottoLabel = new Label("\"Breaking and Overcoming Challenges Through \n          Courage, Hard Work and Persistence\"");
        mottoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        mottoLabel.setTextFill(Color.WHITE);
        mottoLabel.setAlignment(Pos.CENTER);
        mottoLabel.setContentDisplay(ContentDisplay.CENTER);
        HBox mottoBox = new HBox(mottoLabel);
        mottoBox.setLayoutX(120);
        mottoBox.setLayoutY(430);
        mottoBox.setAlignment(Pos.CENTER);
        leftPane.getChildren().add(mottoBox);

        loginPane.getChildren().add(leftPane);

        // right
        AnchorPane rightPane = new AnchorPane();
        rightPane.setPrefWidth(520);
        rightPane.setPrefHeight(560);
        rightPane.setLayoutY(20);
        rightPane.setLayoutX(500);
        rightPane.setStyle("-fx-background-color: #F3F3F3;");
        rightPane.applyCss();

        Label loginLabel = new Label("Login");
        loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        loginLabel.setTextFill(Paint.valueOf("#e34c9d"));
        loginLabel.setAlignment(Pos.CENTER);
        loginLabel.setContentDisplay(ContentDisplay.CENTER);
        loginLabel.setLayoutX(215);
        loginLabel.setLayoutY(120);
        rightPane.getChildren().add(loginLabel);

        usernameField = new TextField();
        usernameField.setPromptText("Trainee Number");
        usernameField.setPrefWidth(400);
        usernameField.setPrefHeight(30);
        usernameField.setAlignment(Pos.CENTER);
        usernameField.getStylesheets().add(styleCss);
        usernameField.getStyleClass().add("login-field");
        usernameField.applyCss();
        usernameField.setLayoutX(50);
        usernameField.setLayoutY(195);
        rightPane.getChildren().add(usernameField);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(400);
        passwordField.setPrefHeight(30);
        passwordField.setAlignment(Pos.CENTER);
        passwordField.getStylesheets().add(styleCss);
        passwordField.getStyleClass().add("login-field");
        passwordField.applyCss();
        passwordField.setLayoutX(50);
        passwordField.setLayoutY(255);
        rightPane.getChildren().add(passwordField);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(50);
        buttonsBox.setPrefHeight(100);
        buttonsBox.setPrefWidth(500);
        buttonsBox.setLayoutX(1);
        buttonsBox.setLayoutY(370);
        buttonsBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(175);
        loginButton.setPrefHeight(50);
        loginButton.getStylesheets().add(styleCss);
        loginButton.getStyleClass().add("login-btn");
        loginButton.setTextAlignment(TextAlignment.CENTER);
        loginButton.applyCss();
        loginButton.setOnAction(this.onSubmit());
        buttonsBox.getChildren().add(loginButton);

        Button registerButton = new Button("Register");
        registerButton.setPrefWidth(175);
        registerButton.setPrefHeight(50);
        registerButton.getStylesheets().add(styleCss);
        registerButton.getStyleClass().add("login-btn");
        registerButton.setTextAlignment(TextAlignment.CENTER);
        registerButton.applyCss();
        registerButton.setOnAction(this.onRegister());
        buttonsBox.getChildren().add(registerButton);

        rightPane.getChildren().add(buttonsBox);

        errorLabel = new Label();
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        errorLabel.setTextFill(Paint.valueOf("#ff0000"));
        errorLabel.setAlignment(Pos.CENTER);
        errorLabel.setContentDisplay(ContentDisplay.CENTER);
        errorLabel.setLayoutX(170);
        errorLabel.setLayoutY(500);
        rightPane.getChildren().add(errorLabel);

        loginPane.getChildren().add(rightPane);

        scene = new Scene(rootPane);
    }

    private EventHandler<ActionEvent> onSubmit() {
        return event -> {
            String username = this.usernameField.getText();
            String password = this.passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please fill in all fields");
                return;
            }

            boolean canLogin = DuTiSa.getInstance().getTraineeManager().canLogin(username, password);
            if (!canLogin) {
                errorLabel.setText("Invalid username or password");
                return;
            }

            new MainMenu(stage);
        };
    }

    private EventHandler<ActionEvent> onRegister() {
        return event -> {
            new RegisterMenu(stage);
        };
    }

}
