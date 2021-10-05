package com.song.chinachess;

import androidx.annotation.Nullable;

import com.song.chinachess.bean.Coordinate;
import com.song.chinachess.pieces.ChessPieces;
import com.song.chinachess.utils.Interpreter;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess
 * This Class is ... 对点定位
 */
public class Gps {
    int x;//绝对坐标x
    int y;//绝对坐标y
    char x_name = ' ';//相对坐标 x
    char y_name = ' ';//相对坐标 y
    Fixed fixed;

    public Fixed getFixed() {
        if (fixed == null) {
            fixed = new Fixed();
            fixed.ld.x = x - ChessPieces.radius;
            fixed.ld.y = y + ChessPieces.radius;

            fixed.lu.x = x - ChessPieces.radius;
            fixed.lu.y = y - ChessPieces.radius;

            fixed.rd.x = x + ChessPieces.radius;
            fixed.rd.y = y + ChessPieces.radius;

            fixed.ru.x = x + ChessPieces.radius;
            fixed.ru.y = y - ChessPieces.radius;
        }
        return fixed;
    }

    @Override
    public String toString() {
        return "Gps{" +
                "绝对坐标x=" + x +
                ", 绝对坐标y=" + y +
                ", 相对坐标x=" + x_name +
                ", 相对坐标y=" + y_name +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getX_name() {
        return x_name;
    }

    public void setX_name(char x_name) {
        this.x_name = x_name;
    }

    public char getY_name() {
        return y_name;
    }

    public void setY_name(char y_name) {
        this.y_name = y_name;
    }

    /**
     * 只有相对坐标时 调用此方法将填充上绝对坐标的值
     */
    public void tianChong() {
        Gps gps = Interpreter.getInstance().transitionX_n(this);
        x = gps.getX();
        y = gps.getY();
    }

    public boolean equalsX(@Nullable Gps gps) {
        int x = gps.getX();
        int y = gps.getY();
        if (x == this.x && y == this.y) {
            return true;
        }
        return false;
    }

    public boolean equalsX_n(@Nullable Gps gps) {
        char x_name = gps.getX_name();
        char y_name = gps.getY_name();
        if (x_name == this.x_name && y_name == this.y_name) {
            return true;
        }
        return false;
    }

    /**
     * 把自己拷贝出来一份  避免地址一样导致的改变
     *
     * @return
     */
    public Gps copy() {
        Gps gps = new Gps();
        gps.setX(x);
        gps.setY(y);
        gps.setY_name(y_name);
        gps.setX_name(x_name);
        return gps;
    }

    /**
     * 关于这个点的 象棋的四个角的坐标
     */
    public class Fixed {
        private Coordinate lu;//左上点
        private Coordinate ld;//左下点
        private Coordinate ru;//右上点
        private Coordinate rd;//右下点

        /**
         * 在构造器内初始化每个顶点对象
         */
        public Fixed() {
            lu = new Coordinate();
            ld = new Coordinate();
            rd = new Coordinate();
            ru = new Coordinate();
        }

        /**
         * 判断指定的坐标是否在区间坐标内
         *
         * @param x 指定坐标x
         * @param y 指定坐标y
         * @return true 为在指定区域
         */
        public boolean isSelect(int x, int y) {
            return (x > lu.x && x < ru.x && y > lu.y && y < rd.y);
        }

        public Coordinate getLu() {
            return lu;
        }

        public Coordinate getLd() {
            return ld;
        }

        public Coordinate getRu() {
            return ru;
        }

        public Coordinate getRd() {
            return rd;
        }
    }
}
