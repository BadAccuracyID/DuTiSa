package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.connector.Results;
import com.github.badaccuracy.id.dutisa.database.objects.CommentData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CommentDatastore {

    private final DuTiSa duTiSa;
    private final MySQL mySQL;
    private final ConcurrentHashMap<String, List<CommentData>> commentMap;

    public CommentDatastore(DuTiSa duTiSa, MySQL mySQL) {
        this.duTiSa = duTiSa;
        this.commentMap = new ConcurrentHashMap<>();
        this.mySQL = mySQL;

        this.loadComments();
    }

    public void addComment(CommentData commentData) {
        if (commentMap.containsKey(commentData.getTraineeNumber())) {
            commentMap.get(commentData.getTraineeNumber()).add(commentData);
        } else {
            commentMap.put(commentData.getTraineeNumber(), new ArrayList<>(List.of(commentData)));
        }
    }

    public List<CommentData> getComments(String traineeNumber) {
        return commentMap.get(traineeNumber);
    }

    public void reloadComments() {
        commentMap.clear();
        this.loadComments();
    }

    public void insertNewComment(CommentData commentData) {
        insertNewCommentAsync(commentData);
    }

    private void loadComments() {
        duTiSa.getExecutorManager().gocExecutor("CommentDL")
                .execute(() -> {
                    try (Results results = mySQL.results("SELECT * FROM Motivation;")) {
                        while (true) {
                            ResultSet set = results.getResultSet();
                            if (!set.next())
                                break;

                            CommentData commentData = new CommentData(
                                    set.getInt("ID"),
                                    set.getString("TraineeNumber"),
                                    set.getString("Message"),
                                    set.getString("SenderTraineeNumber"),
                                    set.getDate("SentDate")
                            );
                            addComment(commentData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void insertNewCommentAsync(CommentData commentData) {
        duTiSa.getExecutorManager().gocExecutor("CommentUL")
                .execute(() -> {
                    try {
                        mySQL.executeQuery("INSERT INTO Motivation (TraineeNumber, Message, SenderTraineeNumber) VALUES ('" +
                                commentData.getTraineeNumber() + "', '" +
                                commentData.getComment() + "', '" +
                                commentData.getCommenter() + "');");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }
}
