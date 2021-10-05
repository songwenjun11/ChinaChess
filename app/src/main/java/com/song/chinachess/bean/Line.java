package com.song.chinachess.bean;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.bean
 * This Class is ... 线条类  棋盘上所有的线条
 */
public class Line {
    private int startX;
    private int startY;
    private int stopX;
    private int stopY;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStopX() {
        return stopX;
    }

    public void setStopX(int stopX) {
        this.stopX = stopX;
    }

    public int getStopY() {
        return stopY;
    }

    public void setStopY(int stopY) {
        this.stopY = stopY;
    }
}
