package com.song.chinachess.pieces;

import android.graphics.Bitmap;

import com.song.chinachess.Gps;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.pieces
 * This Class is ...  象棋  棋子的公共操作方法
 */
public interface PiecesInterface {

    /**
     * 判断这个棋子是否可以送startGps位置走到endGps的位置
     *
     * @param endGps   即将要去的位置
     * @return true为可以移动去过
     */
    boolean isExecute(Gps endGps);

    /**
     * 创建位图  （创建棋子）
     */
    Bitmap createBitmap();

    /**
     * 获取棋子的种类
     *
     * @return
     */
    int getChessClass();
}
