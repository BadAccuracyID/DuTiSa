package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.connector.Results;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.TraineeData;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;

public class TraineeDatastore {

    private final DuTiSa duTiSa;
    @Getter
    private final ConcurrentHashMap<String, TraineeData> traineeMap;
    private final MySQL mySQL;

    public TraineeDatastore(DuTiSa duTiSa, MySQL mySQL) {
        this.duTiSa = duTiSa;
        this.traineeMap = new ConcurrentHashMap<>();
        this.mySQL = mySQL;

        this.loadTrainees();
    }

    public void addTrainee(TraineeData traineeData) {
        traineeMap.put(traineeData.getTraineeNumber(), traineeData);
    }

    public TraineeData getTrainee(String traineeNumber) {
        return traineeMap.get(traineeNumber);
    }

    public void reloadTrainees() {
        traineeMap.clear();
        this.loadTrainees();
    }

    public void insertNewTrainee(TraineeData traineeData) {
        this.insertNewTraineeAsync(traineeData);
    }

    private void loadTrainees() {
        duTiSa.getExecutorManager().gocExecutor("TraineeDL")
                .execute(() -> {
                    File folder = new File("assets/trainee");
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    try (Results results = mySQL.results("SELECT * FROM ListTrainee;")) {
                        while (true) {
                            ResultSet set = results.getResultSet();
                            if (!set.next())
                                break;


                            Blob blob = set.getBlob("TraineePicture");
                            File file = new File("assets/trainee/", set.getString("TraineeNumber") + ".jpeg");
                            file.createNewFile();
                            file.deleteOnExit();
                            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                                byte[] bytes = blob.getBytes(1, (int) blob.length());
                                outputStream.write(bytes);
                            }

                            System.out.println("Loaded trainee " + set.getString("TraineeNumber") + " - " + set.getString("TraineeName"));
                            System.out.println("Saved to " + file.getAbsolutePath());

                            TraineeData traineeData = new TraineeData(
                                    set.getString("TraineeNumber"),
                                    set.getString("TraineeName"),
                                    set.getString("TraineeMajor"),
                                    set.getString("TraineeGeneration"),
                                    file
                            );

                            LoginData loginData = new LoginData(
                                    set.getString("TraineeNumber"),
                                    set.getString("TraineePassword")
                            );
                            traineeData.setLoginData(loginData);
                            addTrainee(traineeData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private synchronized void insertNewTraineeAsync(TraineeData traineeData) {
        duTiSa.getExecutorManager().gocExecutor("TraineeUL")
                .execute(() -> {
                    try {
                        String prepared = "INSERT INTO ListTrainee (TraineeNumber, TraineeName, TraineePassword, TraineeMajor, TraineeGeneration, TraineePicture) VALUES (?, ?, ?, ?, ?, ?);";
                        PreparedStatement statement = mySQL.prepareQuery(prepared);
                        statement.setString(1, traineeData.getTraineeNumber());
                        statement.setString(2, traineeData.getTraineeName());
                        statement.setString(3, traineeData.getLoginData().getHashedPassword());
                        statement.setString(4, traineeData.getMajor());
                        statement.setString(5, traineeData.getBinusian());
                        statement.setBlob(6, new FileInputStream(traineeData.getPhoto()));
                        System.out.println(statement);

                        statement.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
