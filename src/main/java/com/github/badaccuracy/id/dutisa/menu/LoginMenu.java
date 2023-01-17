package com.github.badaccuracy.id.dutisa.menu;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.utils.AlertPopup;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.util.Duration;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class LoginMenu {

    private final Stage stage;
    private Scene scene;

    private TextField usernameField;
    private PasswordField passwordField;

    private Group foxGroup;

    public LoginMenu(Stage stage) {
        this.stage = stage;
        makeFox();
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
        imageBox.setLayoutY(200);
        imageBox.setLayoutX(120);
        imageBox.getChildren().add(foxGroup);
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
        usernameField.setOnAction(this.onSubmit());
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
        passwordField.setOnAction(this.onSubmit());
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

        // exit button
        Button exitButton = new Button("X");
        exitButton.setPrefWidth(30);
        exitButton.setPrefHeight(30);
        exitButton.getStylesheets().add(styleCss);
        exitButton.getStyleClass().add("exit-btn");
        exitButton.setTextAlignment(TextAlignment.CENTER);
        exitButton.applyCss();
        exitButton.setLayoutX(470);
        exitButton.setLayoutY(10);
        exitButton.setOnAction(event -> {
            System.exit(0);
        });
        rightPane.getChildren().add(exitButton);

        loginPane.getChildren().add(rightPane);

        scene = new Scene(rootPane);
    }

    private void makeFox() {
        ImageView fox1 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog0.png").toURI().toString()));
        ImageView fox2 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog1.png").toURI().toString()));
        ImageView fox3 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog2.png").toURI().toString()));
        ImageView fox4 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog3.png").toURI().toString()));
        ImageView fox5 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog4.png").toURI().toString()));
        ImageView fox6 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog5.png").toURI().toString()));
        ImageView fox7 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog6.png").toURI().toString()));
        ImageView fox8 = new ImageView(new Image(Utils.getFile("assets/images/dog/dog7.png").toURI().toString()));

        foxGroup = new Group();

        foxGroup.setTranslateX(100);
        foxGroup.setTranslateY(0);

        Timeline keyFrames = new Timeline();
        keyFrames.setCycleCount(Timeline.INDEFINITE);
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(400), (ActionEvent event) -> foxGroup.getChildren().setAll(fox4)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(800), (ActionEvent event) -> foxGroup.getChildren().setAll(fox5)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(1200), (ActionEvent event) -> foxGroup.getChildren().setAll(fox6)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(1600), (ActionEvent event) -> foxGroup.getChildren().setAll(fox7)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(2000), (ActionEvent event) -> foxGroup.getChildren().setAll(fox6)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(2400), (ActionEvent event) -> foxGroup.getChildren().setAll(fox5)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(2800), (ActionEvent event) -> foxGroup.getChildren().setAll(fox4)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(3200), (ActionEvent event) -> foxGroup.getChildren().setAll(fox5)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(3600), (ActionEvent event) -> foxGroup.getChildren().setAll(fox6)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(4000), (ActionEvent event) -> foxGroup.getChildren().setAll(fox7)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(4400), (ActionEvent event) -> foxGroup.getChildren().setAll(fox6)
        ));
        keyFrames.getKeyFrames().add(new KeyFrame(
                Duration.millis(4800), (ActionEvent event) -> foxGroup.getChildren().setAll(fox5)
        ));
        keyFrames.play();
    }

    private EventHandler<ActionEvent> onSubmit() {
        return event -> {
            String username = this.usernameField.getText();
            String password = this.passwordField.getText();

            DuTiSa instance = DuTiSa.getInstance();
            if (!instance.getTraineeManager().getMySQL().isConnected()) {
                AlertPopup.showAlert("MySQL is still connecting!", Alert.AlertType.ERROR, this.scene);
                return;
            }

            if (username.isEmpty() || password.isEmpty()) {
                AlertPopup.showAlert("Please fill in all fields", Alert.AlertType.WARNING, this.scene);
                return;
            }

            boolean canLogin = instance.getTraineeManager().canLogin(username, password);
            if (!canLogin) {
                AlertPopup.showAlert("Invalid username or password", Alert.AlertType.ERROR, this.scene);
                return;
            }

            new MainMenu(stage, username);
        };
    }

    private EventHandler<ActionEvent> onRegister() {
        return event -> {
            new RegisterMenu(stage);
        };
    }

}
