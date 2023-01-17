package com.github.badaccuracy.id.dutisa.database.manager;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.impl.CommentDatastore;
import com.github.badaccuracy.id.dutisa.database.impl.TraineeDatastore;
import com.github.badaccuracy.id.dutisa.database.objects.CommentData;
import com.github.badaccuracy.id.dutisa.database.objects.DatabaseConfig;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.TraineeData;
import com.github.badaccuracy.id.dutisa.googleapi.SheetAPI;
import com.github.badaccuracy.id.dutisa.utils.Utils;
import lombok.Getter;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TraineeManager {

    private final DuTiSa main;
    @Getter
    private final MySQL mySQL;
    private final SheetAPI sheetAPI;
    private CommentDatastore commentDatastore;
    private TraineeDatastore traineeDatastore;


    public TraineeManager(DuTiSa main, DatabaseConfig databaseConfig) {
        this.main = main;
        this.mySQL = new MySQL(
                databaseConfig.getHost(),
                databaseConfig.getPort(),
                databaseConfig.getDatabase(),
                databaseConfig.getUsername(),
                databaseConfig.getPassword(),
                databaseConfig.isUseSSL()
        );
        this.sheetAPI = new SheetAPI();

        main.getExecutorManager().gocExecutor("MySQLLoader")
                .execute(() -> {
                    try {
                        this.mySQL.connect();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    this.commentDatastore = new CommentDatastore(main, mySQL);
                    this.traineeDatastore = new TraineeDatastore(main, mySQL);
                });
    }

    public void reload() {
        this.commentDatastore.reloadComments();
        this.traineeDatastore.reloadTrainees();
    }

    public List<TraineeData> getTrainees() {
        return new ArrayList<>(traineeDatastore.getTraineeMap().values());
    }

    public boolean canLogin(String traineeNumber, String password) {
        String hashedPassword = Utils.hashPassword(password);
        TraineeData trainee = traineeDatastore.getTrainee(traineeNumber);
        if (trainee == null) {
            return false;
        }

        return trainee.getLoginData().getHashedPassword().equals(hashedPassword);
    }

    public boolean canRegister(String traineeNumber, String traineeName, String password, String jurusan, String angkatan, File photo) {
        TraineeData trainee = traineeDatastore.getTrainee(traineeNumber);
        if (trainee != null) {
            return false;
        }

        String hashedPassword = Utils.hashPassword(password);
        TraineeData traineeData = new TraineeData(traineeNumber, traineeName, jurusan, angkatan, photo);

        LoginData loginData = new LoginData(traineeNumber, hashedPassword);
        traineeData.setLoginData(loginData);

        traineeDatastore.addTrainee(traineeData);
        traineeDatastore.insertNewTrainee(traineeData);
        main.getExecutorManager().gocExecutor("SheetCreator")
                .execute(() -> sheetAPI.createNewSheet(traineeNumber));
        return true;
    }

    public List<CommentData> getComments(String traineeNumber) {
        return commentDatastore.getComments(traineeNumber);
    }

    public void postComment(CommentData commentData) {
        commentDatastore.addComment(commentData);
        commentDatastore.insertNewComment(commentData);

        main.getExecutorManager().gocExecutor("CommentPoster")
                .execute(() -> sheetAPI.doWrite(commentData.getTraineeNumber(), Collections.singletonList(commentData)));

    }

}
