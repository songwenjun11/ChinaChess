package com.song.chinachess.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.song.chinachess.Gps;
import com.song.chinachess.pieces.ChessPieces;
import com.song.chinachess.view.GameView;

import static com.song.chinachess.utils.Interpreter.redPiecesList;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/12
 * The package is com.song.chinachess.utils
 * This Class is ...  动画工具  负责棋子的运动
 */
public class AnimationUtils {
    private static AnimationUtils animationUtils;
    private GameView gameView;
    private Gps startGps;
    private Gps endGps;
    private final int time = 500;//运动时间  500毫秒
    private ChessPieces chessPieces;

    private AnimationUtils(GameView gameView) {
        this.gameView = gameView;
    }

    public static AnimationUtils getInstance(GameView gameView) {
        if (animationUtils == null) {
            synchronized (AnimationUtils.class) {
                if (animationUtils == null) {
                    animationUtils = new AnimationUtils(gameView);
                }
            }
        }
        return animationUtils;
    }

    public void moveing(ChessPieces pieces, Gps endGps) {
        this.endGps = endGps;
        startGps = pieces.getGps();
        chessPieces = pieces;

        ThreadPoolManager.getInstance().execute(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //  公式 ： 速度  *  时间  =  距离
            int s_x = startGps.getX();
            int s_y = startGps.getY();
            int e_x = endGps.getX();
            int e_y = endGps.getY();
            if (s_x == e_x) {
                //这里证明是纵向运动
                if (s_y < e_y) {
                    //这里是向下运动
                    int distance = s_y - e_y;//获取距离
                    int v = distance / time;//获取速度
//                    int time_1 = time / (distance / v);//获取匀速
                    for (int i = s_y; i <= e_y; i += v) {
                        try {
//                            Thread.sleep(time_1);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startGps.setY(i);
                        startGps.setY_name(' ');
                        startGps.setX_name(' ');
                        chessPieces.setGps(startGps);
                        redPiecesList.set(0, chessPieces);
                        handler.sendEmptyMessage(1);
                    }
                } else {
                    //这里是向上运动
                }
            } else if (s_y == e_y) {
                //这里证明是横向运动
            } else {
                //这里证明是斜线运动
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            gameView.invalidate();
        }
    };
}
