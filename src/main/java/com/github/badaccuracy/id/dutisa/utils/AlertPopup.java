package com.github.badaccuracy.id.dutisa.utils;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.BoxBlur;
import javafx.stage.StageStyle;
import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class AlertPopup {

    private final File file = Utils.getFile("assets/css/style.css");
    private final String styleCss = file.toURI().toString();

    public void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.initStyle(StageStyle.TRANSPARENT);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(styleCss);
        dialogPane.getStyleClass().add("alert");
        dialogPane.applyCss();

        alert.showAndWait();
    }

    public void showAlert(String message, Alert.AlertType alertType, Scene currentScene) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.initStyle(StageStyle.TRANSPARENT);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(styleCss);
        dialogPane.getStyleClass().add("alert");
        dialogPane.applyCss();

        // blur scene
        currentScene.getRoot().setEffect(new BoxBlur(3, 3, 6));

        alert.showAndWait().get();
        // un blur
        currentScene.getRoot().setEffect(null);
    }
}
