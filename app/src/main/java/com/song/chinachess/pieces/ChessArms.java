package com.song.chinachess.pieces;

import android.content.Context;
import android.util.Log;

import com.song.chinachess.Gps;
import com.song.chinachess.config.GameConfig;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/12
 * The package is com.song.chinachess.pieces
 * This Class is ...
 */
public class ChessArms extends ChessCar {
    public ChessArms(Context context, boolean isTeammate) {
        super(context, isTeammate);
    }

    @Override
    public char getName() {
        return '兵';
    }

    @Override
    public boolean isExecute(Gps endGps) {
        Gps gps = getGps();
        char x_name = gps.getX_name();
        char y_name = gps.getY_name();
        char x_name1 = endGps.getX_name();
        char y_name1 = endGps.getY_name();

        if (isTeammate) {
            //这里指的是红棋
            if (y_name > '6') {
                //没有过界的  兵  不能横着走
                if (x_name != x_name1) {
                    return false;
                } else {
                    if (y_name == y_name1) {
                        return false;
                    } else {
                        //这里指的是  红棋  向上走   y轴逐渐减小
                        if (y_name1 - y_name < '0' && GameConfig.removeChar(y_name, 1) == y_name1) {
                            //这里是指定 兵 只能前进
                            setGps(endGps);
                            return true;
                        } else {
                            Log.e("caes", (char) (-1) + "");
                            return false;
                        }
                    }
                }
            } else {
                if (x_name == x_name1 && y_name != y_name1) {
                    if (y_name1 - y_name < 0) {
                        setGps(endGps);
                        return true;
                    } else {
                        return false;
                    }
                } else if (y_name == y_name1 && x_name != x_name1) {
                    if (x_name > x_name1 && GameConfig.removeChar(x_name, 1) == x_name1) {
                        setGps(endGps);
                        return true;
                    } else if (x_name1 > x_name && GameConfig.removeChar(x_name1, 1) == x_name) {
                        setGps(endGps);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            if (y_name < '5') {
                //没有过界的  兵  不能横着走
                if (x_name != x_name1) {
                    return false;
                } else {
                    if (y_name == y_name1) {
                        return false;
                    } else {
                        //这里指的是  红棋  向上走   y轴逐渐减小
                        if (y_name - y_name1 < '0' && GameConfig.removeChar(y_name1, 1) == y_name) {
                            //这里是指定 兵 只能前进
                            setGps(endGps);
                            return true;
                        } else {
                            Log.e("caes", (char) (-1) + "");
                            return false;
                        }
                    }
                }
            } else {
                if (x_name == x_name1 && y_name != y_name1) {
                    if (y_name - y_name1 < 0) {
                        setGps(endGps);
                        return true;
                    } else {
                        return false;
                    }
                } else if (y_name1 == y_name && x_name != x_name1) {
                    if (x_name1 > x_name && GameConfig.removeChar(x_name1, 1) == x_name) {
                        setGps(endGps);
                        return true;
                    } else if (x_name > x_name1 && GameConfig.removeChar(x_name, 1) == x_name1) {
                        setGps(endGps);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
    }
}
