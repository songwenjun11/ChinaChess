package com.song.chinachess.event;

import com.song.chinachess.config.BoardLayout;
import com.song.chinachess.pieces.ChessPieces;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/14
 * The package is com.song.chinachess.event
 * This Class is ...  游戏过程中的一些事件
 */
public interface GameChessListener {
    /**
     * 棋子选中事件
     *
     * @param chessPieces 选中的棋子
     */
    void selectChess(ChessPieces chessPieces);

    /**
     * 棋子移动事件
     *
     * @param chessPieces 选中的棋子
     */
    void moveChess(ChessPieces chessPieces);

    /**
     * 游戏结束回调
     *
     * @param boardLayout 胜出的棋子
     */
    void gameOver(BoardLayout boardLayout);

    /**
     * 吃棋的事件
     *
     * @param move   移动的棋子
     * @param remove 被吃掉的棋子
     */
    void eat(ChessPieces move, ChessPieces remove);
}
