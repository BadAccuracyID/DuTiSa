package com.github.badaccuracy.id.dutisa.menu;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.manager.TraineeManager;
import com.github.badaccuracy.id.dutisa.database.objects.CommentData;
import com.github.badaccuracy.id.dutisa.database.objects.TraineeData;
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
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainMenu {

    private final Stage stage;
    private Scene scene;

    private String currentTraineeView;
    private String currentUser;
    private int currIdx = 0;

    private TextField searchField;

    private ImageView traineeProfilePicture;
    private Label fullTraineeNameLabel;
    private Label fullTraineeBinusianMajorLabel;

    private Label errorLabel;

    public MainMenu(Stage stage, String currentUser) {
        this.stage = stage;
        this.currentUser = currentUser;
        initUI();

        TraineeManager traineeManager = DuTiSa.getInstance().getTraineeManager();
        List<TraineeData> trainees = traineeManager.getTrainees();
        int size = trainees.size();

        // select random trainee
        int randomIndex = (int) (Math.random() * size);
        currIdx = randomIndex;
        TraineeData trainee = trainees.get(randomIndex);

        // set image
        traineeProfilePicture.setImage(new Image(trainee.getPhoto().toURI().toString()));
        fullTraineeNameLabel.setText(trainee.getTraineeNumber() + " - " + trainee.getTraineeName());
        fullTraineeBinusianMajorLabel.setText(trainee.getBinusian() + " - " + trainee.getMajor());

        currentTraineeView = trainee.getTraineeNumber();

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
        HBox nameBox = new HBox(fullTraineeNameLabel);
        nameBox.setAlignment(Pos.CENTER);

        fullTraineeBinusianMajorLabel = new Label();
        fullTraineeBinusianMajorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        fullTraineeBinusianMajorLabel.setTextFill(Color.WHITE);
        fullTraineeBinusianMajorLabel.setAlignment(Pos.CENTER);
        fullTraineeBinusianMajorLabel.setContentDisplay(ContentDisplay.CENTER);
        HBox binusianBox = new HBox(fullTraineeBinusianMajorLabel);
        binusianBox.setAlignment(Pos.CENTER);

        VBox nameAndBinusianBox = new VBox(nameBox, binusianBox);
        nameAndBinusianBox.setLayoutX(100);
        nameAndBinusianBox.setLayoutY(390);
        nameAndBinusianBox.setAlignment(Pos.CENTER);
        nameAndBinusianBox.setSpacing(10);
        leftPane.getChildren().add(nameAndBinusianBox);


        Button prevButton = new Button("Previous");
        prevButton.setLayoutX(100);
        prevButton.setLayoutY(500);
        prevButton.setPrefWidth(125);
        prevButton.setPrefHeight(35);
        prevButton.getStyleClass().add("login-btn");
        prevButton.getStylesheets().add(styleCss);
        prevButton.applyCss();
        prevButton.setOnAction(event -> {
            TraineeManager traineeManager = DuTiSa.getInstance().getTraineeManager();
            List<TraineeData> trainees = traineeManager.getTrainees();
            int size = trainees.size();

            currIdx = (currIdx - 1 + size) % size;
            TraineeData trainee = trainees.get(currIdx);

            // set image
            traineeProfilePicture.setImage(new Image(trainee.getPhoto().toURI().toString()));
            fullTraineeNameLabel.setText(trainee.getTraineeNumber() + " - " + trainee.getTraineeName());
            fullTraineeBinusianMajorLabel.setText(trainee.getBinusian() + " - " + trainee.getMajor());

            currentTraineeView = trainee.getTraineeNumber();
        });
        leftPane.getChildren().add(prevButton);

        Button nextButton = new Button("Next");
        nextButton.setLayoutX(275);
        nextButton.setLayoutY(500);
        nextButton.setPrefWidth(125);
        nextButton.setPrefHeight(35);
        nextButton.getStyleClass().add("login-btn");
        nextButton.getStylesheets().add(styleCss);
        nextButton.applyCss();
        nextButton.setOnAction(event -> {
            TraineeManager traineeManager = DuTiSa.getInstance().getTraineeManager();
            List<TraineeData> trainees = traineeManager.getTrainees();
            int size = trainees.size();

            currIdx = (currIdx + 1) % size;
            TraineeData trainee = trainees.get(currIdx);

            // set image
            traineeProfilePicture.setImage(new Image(trainee.getPhoto().toURI().toString()));
            fullTraineeNameLabel.setText(trainee.getTraineeNumber() + " - " + trainee.getTraineeName());
            fullTraineeBinusianMajorLabel.setText(trainee.getBinusian() + " - " + trainee.getMajor());

            currentTraineeView = trainee.getTraineeNumber();
        });
        leftPane.getChildren().add(nextButton);

        // search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search Trainee");
        searchField.setLayoutX(100);
        searchField.setLayoutY(550);
        searchField.setPrefWidth(300);
        searchField.setPrefHeight(35);
        searchField.getStyleClass().add("search-field");
        searchField.getStylesheets().add(styleCss);
        searchField.applyCss();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            TraineeManager traineeManager = DuTiSa.getInstance().getTraineeManager();
            List<TraineeData> trainees = traineeManager.getTrainees();
            int size = trainees.size();

            for (int i = 0; i < size; i++) {
                TraineeData trainee = trainees.get(i);
                if (trainee.getTraineeNumber().equals(newValue)) {
                    currIdx = i;
                    traineeProfilePicture.setImage(new Image(trainee.getPhoto().toURI().toString()));
                    fullTraineeNameLabel.setText(trainee.getTraineeNumber() + " - " + trainee.getTraineeName());
                    fullTraineeBinusianMajorLabel.setText(trainee.getBinusian() + " - " + trainee.getMajor());

                    currentTraineeView = trainee.getTraineeNumber();
                    break;
                }
            }
        });
        leftPane.getChildren().add(searchField);


        loginPane.getChildren().add(leftPane);

        // right
        AnchorPane rightPane = new AnchorPane();
        rightPane.setPrefWidth(520);
        rightPane.setPrefHeight(560);
        rightPane.setLayoutY(20);
        rightPane.setLayoutX(500);
        rightPane.setStyle("-fx-background-color: #F3F3F3;");
        rightPane.applyCss();

        // the comments, Y = 120 | X = 215?

        TextArea commentArea = new TextArea();
        commentArea.setPrefWidth(400);
        commentArea.setPrefHeight(90);
        commentArea.setLayoutX(60);
        commentArea.setLayoutY(420);
        commentArea.setWrapText(true);
        commentArea.setPromptText("Write your comments here...");
        commentArea.getStyleClass().add("text-area");
        commentArea.getStylesheets().add(styleCss);
        commentArea.applyCss();
        rightPane.getChildren().add(commentArea);

        Button submitButton = new Button("Submit");
        submitButton.setPrefWidth(100);
        submitButton.setPrefHeight(25);
        submitButton.getStyleClass().add("login-btn");
        submitButton.getStylesheets().add(styleCss);
        submitButton.applyCss();
        submitButton.setLayoutX(200);
        submitButton.setLayoutY(517);
        submitButton.setOnAction(this.onSubmit(commentArea));
        rightPane.getChildren().add(submitButton);

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

    private EventHandler<ActionEvent> onSubmit(TextArea commentArea) {
        return event -> {
            String text = commentArea.getText();


            CommentData commentData = new CommentData(0, currentTraineeView, text, currentUser, new Date(System.currentTimeMillis()));
            DuTiSa.getInstance().getTraineeManager().postComment(commentData);
        };
    }
}
