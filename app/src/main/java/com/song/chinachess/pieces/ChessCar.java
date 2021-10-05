package com.song.chinachess.pieces;

import android.content.Context;

import com.song.chinachess.Gps;
import com.song.chinachess.utils.Interpreter;

import static com.song.chinachess.utils.Interpreter.blackPiecesList;
import static com.song.chinachess.utils.Interpreter.redPiecesList;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.bean
 * This Class is ...  车子类
 */
public class ChessCar extends ChessPieces {

    public ChessCar(Context context, boolean isTeammate) {
        super(context, isTeammate);
    }

    @Override
    public char getName() {
        return '车';
    }

    @Override
    public boolean isExecute(Gps endGps) {
        char x_name = endGps.getX_name();
        char y_name = endGps.getY_name();
        Gps gps = this.getGps();
        char this_y_name = gps.getY_name();
        char this_x_name = gps.getX_name();
        if (x_name == this_x_name) {
            //纵向移动
            boolean heng = getHeng(endGps, false);
            if (!heng) {
                setGps(endGps);
                return true;
            } else {
                return false;
            }
        } else if (y_name == this_y_name) {
            //横向移动
            boolean heng = getHeng(endGps, true);
            if (!heng) {
                setGps(endGps);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断行驶路线过程中有没有其他的棋子
     *
     * @param endGps  目的地
     * @param isHeng  横向移动 传true
     * @return
     */
    protected boolean getHeng(Gps endGps, boolean isHeng) {
        char x_name1 = endGps.getX_name();
        char y_name1 = endGps.getY_name();
        Gps gps = getGps();
        char y_name = gps.getY_name();
        char x_name = gps.getX_name();
        for (ChessPieces chessPieces : redPiecesList) {
            if (isHeng) {
                if (x_name < x_name1) {
                    if (chessPieces.getGps().getX_name() > x_name && chessPieces.getGps().getX_name() < x_name1) {
                        return true;
                    }
                } else {
                    if (chessPieces.getGps().getX_name() < x_name && chessPieces.getGps().getX_name() > x_name1) {
                        return true;
                    }
                }
            } else {
                if (y_name < y_name1) {
                    if (chessPieces.getGps().getY_name() > y_name && chessPieces.getGps().getY_name() < y_name1) {
                        return true;
                    }
                } else {
                    if (chessPieces.getGps().getY_name() < y_name && chessPieces.getGps().getY_name() > y_name1) {
                        return true;
                    }
                }
            }
        }
        for (ChessPieces chessPieces : blackPiecesList) {
            if (isHeng) {
                if (x_name < x_name1) {
                    if (chessPieces.getGps().getX_name() > x_name && chessPieces.getGps().getX_name() < x_name1) {
                        return true;
                    }
                } else {
                    if (chessPieces.getGps().getX_name() < x_name && chessPieces.getGps().getX_name() > x_name1) {
                        return true;
                    }
                }
            } else {
                if (y_name < y_name1) {
                    if (chessPieces.getGps().getY_name() > y_name && chessPieces.getGps().getY_name() < y_name1) {
                        return true;
                    }
                } else {
                    if (chessPieces.getGps().getY_name() < y_name && chessPieces.getGps().getY_name() > y_name1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
