package com.github.badaccuracy.id.dutisa;

import com.github.badaccuracy.id.dutisa.async.ExecutorManager;
import com.github.badaccuracy.id.dutisa.database.manager.TraineeManager;
import com.github.badaccuracy.id.dutisa.database.objects.DatabaseConfig;
import com.github.badaccuracy.id.dutisa.menu.LoginMenu;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class DuTiSa extends Application {

    @Getter
    private static DuTiSa instance;
    private final ExecutorManager executorManager = new ExecutorManager();
    private final TraineeManager traineeManager;
    private Stage stage;
    private Scene scene;

    public DuTiSa() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            executorManager.shutdown();
        }));
        instance = this;


        DatabaseConfig databaseConfig = null;

        File file = Utils.getFile("assets/dbConfig.json");
        try (InputStream inputStream = new FileInputStream(file)) {
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {

                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                databaseConfig = new DatabaseConfig(
                        jsonObject.get("host").getAsString(),
                        jsonObject.get("port").getAsInt(),
                        jsonObject.get("username").getAsString(),
                        jsonObject.get("password").getAsString(),
                        jsonObject.get("database").getAsString(),
                        jsonObject.get("ssl").getAsBoolean()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (databaseConfig == null) {
            System.out.println("Failed to load database config");
            System.exit(1);
        }

        this.traineeManager = new TraineeManager(this, databaseConfig);
    }

    public static void main(String[] args) {
        new DuTiSa();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        new LoginMenu(this.stage);
    }

}
