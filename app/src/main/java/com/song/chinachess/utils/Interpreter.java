package com.song.chinachess.utils;

import android.content.Context;

import com.song.chinachess.Gps;
import com.song.chinachess.config.BoardLayout;
import com.song.chinachess.config.GameConfig;
import com.song.chinachess.pieces.ChessPieces;
import com.song.chinachess.view.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.utils
 * This Class is ...  棋盘解释器  解释位置信息  解释器为全局的 所以需要单例
 */
public class Interpreter {
    private static Interpreter interpreter;
    private Context context;
    private GameView gameView;

    public static Interpreter getInstance(Context context, GameView gameView) {
        if (interpreter == null) {
            synchronized (Interpreter.class) {
                if (interpreter == null) {
                    interpreter = new Interpreter(context, gameView);
                }
            }
        }
        return interpreter;
    }

    public static Interpreter getInstance() {
        if (interpreter == null) {
            synchronized (Interpreter.class) {
                if (interpreter == null) {
                    interpreter = new Interpreter();
                }
            }
        }
        return interpreter;
    }

    private Interpreter() {
    }

    private Interpreter(Context context, GameView gameView) {
        this.context = context;
        this.gameView = gameView;
    }

    public static List<ChessPieces> redPiecesList = new ArrayList<>();//这是在棋盘上的所有红棋子
    public static List<ChessPieces> blackPiecesList = new ArrayList<>();//这是在棋盘上的所有黑棋子
    public static List<Gps> gpsList = new ArrayList<>();//棋盘上所有的点的绝对坐标和相对坐标

    /**
     * 相对路径转换为绝对路径
     *
     * @return
     */
    public Gps transitionX(Gps gps) {
        for (Gps g : gpsList) {
            if (g.equalsX(gps)) {
                return g;
            }
        }
        return new Gps();
    }

    /**
     * 绝对路径转换为相对路径
     *
     * @return
     */
    public Gps transitionX_n(Gps gps) {
        for (Gps g : gpsList) {
            if (g.equalsX_n(gps)) {
                return g;
            }
        }
        return new Gps();
    }

    public void init() {
        redPiecesList.clear();
        GameConfig.getInitOverall(context, BoardLayout.BACK_RED);//初始化棋盘 棋子  这个方法在游戏未开始时调用
    }
}
