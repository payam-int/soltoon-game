package ir.pint.soltoon.soltoongame.ui.elements;

import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

import javax.swing.*;

public class SuiPlayer extends JComponent {
    private Long id;
    private int money;
    private int score;
    private ComRemoteInfo remoteInfo;

    public SuiPlayer(long id, ComRemoteInfo remoteInfo) {
        this.id = id;
        this.remoteInfo = remoteInfo;
    }

    public SuiPlayer(Long id, int score, ComRemoteInfo remoteInfo) {
        this.id = id;
        this.money = 0;
        this.score = 0;
        this.remoteInfo = remoteInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ComRemoteInfo getRemoteInfo() {
        return remoteInfo;
    }

    public void setRemoteInfo(ComRemoteInfo remoteInfo) {
        this.remoteInfo = remoteInfo;
    }
}
