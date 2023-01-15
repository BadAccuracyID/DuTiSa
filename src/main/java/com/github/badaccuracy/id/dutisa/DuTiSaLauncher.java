package com.github.badaccuracy.id.dutisa;

import com.github.badaccuracy.id.dutisa.database.objects.CommentData;
import com.github.badaccuracy.id.dutisa.googleapi.SheetAPI;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DuTiSaLauncher {

    public static void main(String[] args) {
//        DuTiSa.main(args);
        SheetAPI sheetAPI = new SheetAPI();

        List<CommentData> commentDataList = new ArrayList<>();
        commentDataList.add(new CommentData(0, "T142", "Gajelas u...", "T999", new Date(System.currentTimeMillis())));
        commentDataList.add(new CommentData(1, "T142", "Anjayyyyyyyy", "T999", new Date(System.currentTimeMillis())));
        sheetAPI.doWrite("T142", commentDataList);
    }

}
