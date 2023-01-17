package com.github.badaccuracy.id.dutisa.tasks;

import com.github.badaccuracy.id.dutisa.DuTiSa;

public class ReloadTask implements Runnable {

    private final DuTiSa duTiSa;

    public ReloadTask(DuTiSa duTiSa) {
        this.duTiSa = duTiSa;
    }

    @Override
    public void run() {
        duTiSa.getTraineeManager().reload();
    }
}
