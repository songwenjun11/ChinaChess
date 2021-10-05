package com.song.chinachess.config;

import android.content.Context;
import android.util.Log;

import com.song.chinachess.Gps;
import com.song.chinachess.pieces.ChessArms;
import com.song.chinachess.pieces.ChessCar;
import com.song.chinachess.pieces.ChessConnon;
import com.song.chinachess.pieces.ChessPieces;
import com.song.chinachess.pieces.ChessWill;
import com.song.chinachess.pieces.ChessElephant;
import com.song.chinachess.pieces.ChessHorse;
import com.song.chinachess.pieces.ChessMarale;

import static com.song.chinachess.utils.Interpreter.blackPiecesList;
import static com.song.chinachess.utils.Interpreter.redPiecesList;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/12
 * The package is com.song.chinachess.config
 * This Class is ...  游戏配置
 */
public class GameConfig {
    public static final int CAR = 1;//车
    public static final int HORSE = 2;//马
    public static final int CONNON = 3;//炮
    public static final int ELEPHANT = 4;//象
    public static final int MARALE = 5;//士
    public static final int WILL = 6;//将
    public static final int ARMS = 7;//兵

    public static char x[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};//相对路径x数组
    public static char y[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B'};//相对路径y数组
    private static char[] y_1 = {'车', '马', '象', '士', '将', '士', '象', '马', '车'};
    private static char[] y_4 = {'兵', '兵', '兵', '兵', '兵'};
    private static char[] y_3 = {'炮', '炮'};

    public static int getChessClass(char name) {
        switch (name) {
            case '车':
                return CAR;
            case '马':
                return HORSE;
            case '炮':
                return CONNON;
            case '象':
                return ELEPHANT;
            case '士':
                return MARALE;
            case '将':
                return WILL;
            case '兵':
                return ARMS;
        }
        return 0;
    }

    /**
     * 初始化所有棋子位置
     */
    public static void getInitOverall(Context context, BoardLayout boardLayout) {
        boolean back_red = boardLayout != BoardLayout.BACK_RED;
        //利用java的多态特性通过for循环创建我方和敌方的棋子
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < y_1.length; i++) {
                ChessPieces chessPieces = null;
                if (y_1[i] == '车') {
                    chessPieces = new ChessCar(context, j == 1 ? back_red : !back_red);
                } else if (y_1[i] == '马') {
                    chessPieces = new ChessHorse(context, j == 1 ? back_red : !back_red);
                } else if (y_1[i] == '象') {
                    chessPieces = new ChessElephant(context, j == 1 ? back_red : !back_red);
                } else if (y_1[i] == '士') {
                    chessPieces = new ChessMarale(context, j == 1 ? back_red : !back_red);
                } else if (y_1[i] == '将') {
                    chessPieces = new ChessWill(context, j == 1 ? back_red : !back_red);
                }
                Gps gps = new Gps();
                gps.setY_name(j == 1 ? '1' : 'A');
                gps.setX_name(x[i]);
                gps.tianChong();
                chessPieces.setGps(gps);
                if (j == 1 ? back_red : !back_red) {
                    redPiecesList.add(chessPieces);
                } else {
                    blackPiecesList.add(chessPieces);
                }
            }
            for (int i = 0; i < y_4.length * 2; i += 2) {
                ChessArms chessArms = new ChessArms(context, j == 1 ? back_red : !back_red);
                Gps gps = new Gps();
                gps.setY_name(j == 1 ? '4' : '7');
                gps.setX_name(x[i]);
                gps.tianChong();
                chessArms.setGps(gps);
                if (j == 1 ? back_red : !back_red) {
                    redPiecesList.add(chessArms);
                } else {
                    blackPiecesList.add(chessArms);
                }
            }
            for (int i = 0; i < x.length; i++) {
                if (i == 1 || i == 7) {
                    ChessConnon chessConnon = new ChessConnon(context, j == 1 ? back_red : !back_red);
                    Gps gps = new Gps();
                    gps.setX_name(x[i]);
                    gps.setY_name(j == 1 ? '3' : '8');
                    gps.tianChong();
                    chessConnon.setGps(gps);
                    if (j == 1 ? back_red : !back_red) {
                        redPiecesList.add(chessConnon);
                    } else {
                        blackPiecesList.add(chessConnon);
                    }
                }
            }
        }
    }

    /**
     * 字符加法
     *
     * @param ch
     * @param num 加多少
     * @return
     */
    public static char addChar(char ch, int num) {
        //不是最大值可以进行加法
        if (ch != x[x.length - 1] && ch != y[y.length - 1]) {
            Log.e("dasdasdas", (char) (ch + num) + "");
            return (char) (ch + num);
        } else {
            if (ch == x[x.length + 1]) {
                return x[x.length + 1];
            } else {
                return y[y.length - 1];
            }
        }
    }

    /**
     * 字符减法
     *
     * @param ch
     * @param num 减多少
     * @return
     */
    public static char removeChar(char ch, int num) {
        if (ch != x[0] && ch != y[0]) {
            return (char) (ch - num);
        } else {
            if (ch == x[0]) {
                return x[0];
            } else {
                return y[0];
            }
        }
    }

    /**
     * 游戏规则
     */
    public static void rule() {

    }
}
