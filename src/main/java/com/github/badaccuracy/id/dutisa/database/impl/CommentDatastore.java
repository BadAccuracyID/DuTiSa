package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.connector.Results;
import com.github.badaccuracy.id.dutisa.database.objects.CommentData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CommentDatastore {

    private final DuTiSa duTiSa;
    private final MySQL mySQL;
    private final ConcurrentHashMap<Integer, CommentData> commentMap;

    public CommentDatastore(DuTiSa duTiSa, MySQL mySQL) {
        this.duTiSa = duTiSa;
        this.commentMap = new ConcurrentHashMap<>();
        this.mySQL = mySQL;

        this.loadComments();
    }

    public void addComment(CommentData commentData) {
        commentMap.put(commentData.getCommentId(), commentData);
    }

    public CommentData getComment(Integer commentId) {
        return commentMap.get(commentId);
    }

    public List<CommentData> getComments(String traineeNumber) {
        return commentMap.values().stream()
                .filter(commentData -> commentData.getTraineeNumber().equals(traineeNumber)).collect(Collectors.toList());
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
                    try (Results results = mySQL.results("SELECT * FROM Motivasi;")) {
                        while (true) {
                            ResultSet set = results.getResultSet();
                            if (!set.next())
                                break;

                            CommentData commentData = new CommentData(
                                    set.getInt("CommentID"),
                                    set.getString("TraineeNumber"),
                                    set.getString("PesanMotivasi"),
                                    set.getString("SenderID"),
                                    set.getDate("CommentDate")
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
                        mySQL.executeQuery("INSERT INTO Motivasi (TraineeNumber, PesanMotivasi, SenderID) VALUES ('" +
                                commentData.getTraineeNumber() + "', '" +
                                commentData.getComment() + "', '" +
                                commentData.getCommenter() + "');");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }
}
