package com.github.badaccuracy.id.dutisa.menu;

import com.github.badaccuracy.id.dutisa.database.objects.CommentData;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;
import java.util.concurrent.atomic.AtomicReference;

public class MainMenu {

    private final Stage stage;
    private Scene scene;

    private TextField searchField;

    private ImageView traineeProfilePicture;
    private Label fullTraineeNameLabel;
    private Label fullTraineeBinusianMajorLabel;

    private Label errorLabel;

    public MainMenu(Stage stage) {
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

        traineeProfilePicture = new ImageView();
        traineeProfilePicture.setPreserveRatio(true);
        traineeProfilePicture.setPickOnBounds(true);
        traineeProfilePicture.setFitWidth(272);
        traineeProfilePicture.setFitHeight(272);

        imageBox.getChildren().add(traineeProfilePicture);
        leftPane.getChildren().add(imageBox);

        fullTraineeNameLabel = new Label();
        fullTraineeNameLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 28));
        fullTraineeNameLabel.setTextFill(Color.WHITE);
        fullTraineeNameLabel.setAlignment(Pos.CENTER);
        fullTraineeNameLabel.setContentDisplay(ContentDisplay.CENTER);
        HBox labelBox = new HBox(fullTraineeNameLabel);
        labelBox.setLayoutX(190);
        labelBox.setLayoutY(390);
        labelBox.setAlignment(Pos.CENTER);
        leftPane.getChildren().add(labelBox);

        fullTraineeBinusianMajorLabel = new Label();
        fullTraineeBinusianMajorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        fullTraineeBinusianMajorLabel.setTextFill(Color.WHITE);
        fullTraineeBinusianMajorLabel.setAlignment(Pos.CENTER);
        fullTraineeBinusianMajorLabel.setContentDisplay(ContentDisplay.CENTER);
        HBox mottoBox = new HBox(fullTraineeBinusianMajorLabel);
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

//        Label loginLabel = new Label("Login");
//        loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
//        loginLabel.setTextFill(Paint.valueOf("#e34c9d"));
//        loginLabel.setAlignment(Pos.CENTER);
//        loginLabel.setContentDisplay(ContentDisplay.CENTER);
//        loginLabel.setLayoutX(215);
//        loginLabel.setLayoutY(120);
//        rightPane.getChildren().add(loginLabel);

        ScrollPane scrollPane = new ScrollPane();

        TableView<CommentData> tableView = new TableView<>();

        TableColumn<CommentData, String> commenterColumn = new TableColumn<>("Commenter");
        commenterColumn.setCellValueFactory(new PropertyValueFactory<>("commenter"));
        commenterColumn.setPrefWidth(200);

        TableColumn<CommentData, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(200);

        TableColumn<CommentData, String> commentColumn = new TableColumn<>("Comment");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentColumn.setPrefWidth(200);

        tableView.getColumns().add(commenterColumn);
        tableView.getColumns().add(dateColumn);
        tableView.getColumns().add(commentColumn);

        rightPane.getChildren().add(scrollPane);

        scrollPane.setLayoutX(215);
        scrollPane.setLayoutY(120);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(50);
        buttonsBox.setPrefHeight(100);
        buttonsBox.setPrefWidth(500);
        buttonsBox.setLayoutX(1);
        buttonsBox.setLayoutY(370);
        buttonsBox.setAlignment(Pos.CENTER);

//        Button loginButton = new Button("Login");
//        loginButton.setPrefWidth(175);
//        loginButton.setPrefHeight(50);
//        loginButton.getStylesheets().add(styleCss);
//        loginButton.getStyleClass().add("login-btn");
//        loginButton.setTextAlignment(TextAlignment.CENTER);
//        loginButton.applyCss();
//        loginButton.setOnAction(this.onSubmit());
//        buttonsBox.getChildren().add(loginButton);
//
//        Button registerButton = new Button("Register");
//        registerButton.setPrefWidth(175);
//        registerButton.setPrefHeight(50);
//        registerButton.getStylesheets().add(styleCss);
//        registerButton.getStyleClass().add("login-btn");
//        registerButton.setTextAlignment(TextAlignment.CENTER);
//        registerButton.applyCss();
//        registerButton.setOnAction(this.onRegister());
//        buttonsBox.getChildren().add(registerButton);

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

}
