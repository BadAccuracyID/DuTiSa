package com.github.badaccuracy.id.dutisa.menu.unused;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.manager.TraineeManager;
import com.github.badaccuracy.id.dutisa.database.objects.TraineeData;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MainMenuUNUSED {

    private final DuTiSa main = DuTiSa.getInstance();

    private final Stage stage;
    private Scene scene;

    private TextField searchField;

    private ImageView imageView;
    private Label fullTraineeNameLabel;
    private Label traineeMajor;
    private Label traineeBinusian;

    private Button nextButton;
    private Button previousButton;

    public MainMenuUNUSED(Stage stage) {
        this.stage = stage;
        initUI();

        TraineeManager traineeManager = main.getTraineeManager();
        List<TraineeData> trainees = traineeManager.getTrainees();
        int size = trainees.size();

        // select random trainee
        int randomIndex = (int) (Math.random() * size);
        TraineeData trainee = trainees.get(randomIndex);

        // set image
        imageView.setImage(new Image(trainee.getPhoto().toURI().toString()));
        fullTraineeNameLabel.setText(trainee.getTraineeNumber() + " - " + trainee.getTraineeName());
        traineeMajor.setText(trainee.getMajor());
        traineeBinusian.setText(trainee.getBinusian());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initUI() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(25));

        searchField = new TextField();
        searchField.setPromptText("TXXX");
        VBox searchBox = new VBox(searchField);
        searchBox.setAlignment(Pos.CENTER);
        borderPane.setTop(searchBox);

        borderPane.setTop(searchField);

        VBox vBox = new VBox();
        vBox.setSpacing(10);

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(450);

        fullTraineeNameLabel = new Label();
        traineeMajor = new Label();
        traineeBinusian = new Label();

        vBox.getChildren().addAll(imageView, fullTraineeNameLabel, traineeMajor, traineeBinusian);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);

        nextButton = new Button("Next");
        previousButton = new Button("Previous");

        HBox buttons = new HBox(nextButton, previousButton);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        borderPane.setBottom(buttons);

        File backgroundFile = Utils.getFile("assets/images/gradient.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        borderPane.setBackground(new Background(background));

        scene = new Scene(borderPane);
    }
}
