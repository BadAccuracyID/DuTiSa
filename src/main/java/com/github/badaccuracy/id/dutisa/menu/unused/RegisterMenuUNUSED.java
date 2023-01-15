package com.github.badaccuracy.id.dutisa.menu.unused;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.menu.LoginMenu;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RegisterMenuUNUSED {

    private final Stage stage;
    private Scene scene;

    private TextField traineeNumber;
    private TextField traineeName;
    private PasswordField password;
    private TextField jurusan;
    private TextField angkatan;

    private File pfpFile = null;
    private Label errorLabel;

    public RegisterMenuUNUSED(Stage stage) {
        this.stage = stage;
        initUI();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initUI() {
        GridPane gridPane = new GridPane();

        traineeNumber = new TextField();
        traineeNumber.setOnAction(this.onSubmit());
        traineeName = new TextField();
        traineeName.setOnAction(this.onSubmit());
        password = new PasswordField();
        password.setOnAction(this.onSubmit());
        jurusan = new TextField();
        jurusan.setOnAction(this.onSubmit());
        angkatan = new TextField();
        angkatan.setOnAction(this.onSubmit());

        Label usernameLabel = new Label("Trainee Number");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(traineeNumber, 1, 1);

        Label nameLabel = new Label("Name");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gridPane.add(nameLabel, 0, 2);
        gridPane.add(traineeName, 1, 2);

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gridPane.add(passwordLabel, 0, 3);
        gridPane.add(password, 1, 3);

        Label registerLabel = new Label("Register");
        registerLabel.setAlignment(Pos.CENTER);
        registerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(registerLabel, 0, 0);

        Label jurusanLabel = new Label("Jurusan");
        jurusanLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gridPane.add(jurusanLabel, 0, 4);
        gridPane.add(jurusan, 1, 4);

        Label angkatanLabel = new Label("Angkatan");
        angkatanLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        gridPane.add(angkatanLabel, 0, 5);
        gridPane.add(angkatan, 1, 5);

        File backgroundFile = Utils.getFile("assets/images/gradient.png");
        Image backgroundImage = new Image(backgroundFile.toURI().toString());
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        gridPane.setBackground(new Background(background));


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Button button = new Button("Select profile picture");
        button.setOnAction(event -> {
            pfpFile = fileChooser.showOpenDialog(null);
            if (pfpFile != null) {
                System.out.println(pfpFile.getAbsolutePath());
            }
        });
        HBox fileChooserBox = new HBox(button);
        fileChooserBox.setAlignment(Pos.CENTER);
        gridPane.add(fileChooserBox, 1, 6);

        HBox loginRegister = new HBox();
        loginRegister.setAlignment(Pos.CENTER);
        loginRegister.setSpacing(15);

        Button loginButton = new Button("Create Account");
        loginButton.setMinWidth(75);
        loginButton.setMinHeight(30);
        loginButton.setOnAction(this.onSubmit());
        loginRegister.getChildren().add(loginButton);

        Button registerButton = new Button("Back to Login");
        registerButton.setMinWidth(75);
        registerButton.setMinHeight(30);
        registerButton.setOnAction(this.onRegister());
        loginRegister.getChildren().add(registerButton);

        gridPane.add(loginRegister, 1, 7);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(15);

        errorLabel = new Label();
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        errorLabel.setTextFill(Color.RED);
        gridPane.add(errorLabel, 1, 8);

        scene = new Scene(gridPane, 1080, 720);
    }

    private EventHandler<ActionEvent> onSubmit() {
        return event -> {
            String traineeNumber = this.traineeNumber.getText();
            String traineeName = this.traineeName.getText();
            String password = this.password.getText();
            String jurusan = this.jurusan.getText();
            String angkatan = this.angkatan.getText();

            if (traineeNumber.isEmpty() || password.isEmpty() || jurusan.isEmpty() || angkatan.isEmpty() || pfpFile == null) {
                errorLabel.setText("Please fill all the fields!");
                return;
            }

            boolean canRegister = DuTiSa.getInstance().getTraineeManager()
                    .canRegister(traineeNumber, traineeName, password, jurusan, angkatan, pfpFile);

            if (!canRegister) {
                errorLabel.setText("Username already taken!");
            }

            // main menu
        };
    }

    private EventHandler<ActionEvent> onRegister() {
        return event -> {
            new LoginMenu(stage);
        };
    }

}
