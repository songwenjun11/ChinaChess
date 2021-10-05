package com.song.chinachess.pieces;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.song.chinachess.Gps;
import com.song.chinachess.bean.Line;
import com.song.chinachess.config.GameConfig;

import java.util.ArrayList;
import java.util.List;

import static com.song.chinachess.utils.Interpreter.gpsList;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.bean
 * This Class is ...  单例棋盘类  因为只有一个棋盘所以必须是单例类
 */
public class CheckerBoard {
    private volatile static CheckerBoard checkerBoard;
    public static int lieju;
    public static int hangju;

    private CheckerBoard() {
    }

    public static CheckerBoard getInstance() {
        if (checkerBoard == null) {
            synchronized (CheckerBoard.class) {
                if (checkerBoard == null) {
                    checkerBoard = new CheckerBoard();
                }
            }
        }
        return checkerBoard;
    }

    Bitmap checkerBitmap;
    List<Line> lineList = new ArrayList<>();//棋盘上所有的线条
    List<Line> wList = new ArrayList<>();//斜线坐标
    private final int LINE_NUM = 9;//列数
    private final int ROW_NUM = 10;//行数

    /**
     *
     * @param side 边长
     * @return
     */
    public Bitmap getCheckerBitmap(int side) {
        return designChecker(side, side);
    }

    public List<Gps> getGpsList() {
        return gpsList;
    }

    public List<Line> getLineList() {
        return lineList;
    }

    public void setCheckerBitmap(Bitmap checkerBitmap) {
        this.checkerBitmap = checkerBitmap;
    }

    /**
     * 设计棋盘
     *
     * @param width  棋盘宽度
     * @param height 棋盘高度
     * @return 返回棋盘的位图形式
     */
    private Bitmap designChecker(int width, int height) {
        ChessPieces.radius = width / 27;//缩放倍数
        //这里判断是否已经绘制过棋盘  绘制过的话直接使用  提升效率
        if (checkerBitmap != null)
            return checkerBitmap;
        //第一次绘制棋盘
        checkerBitmap = Bitmap.createBitmap(width + 50, height + 50, Bitmap.Config.ARGB_8888);
        //每列的间隔 9
        lieju = width / LINE_NUM;
        //每行的间隔 10
        hangju = height / ROW_NUM;
        //设计竖线线条
        for (int x = 50, flag = 0; x < height && flag < LINE_NUM; x += lieju, flag++) {
            Line line = new Line();
            line.setStartX(x);
            line.setStartY(50);
            line.setStopY(9 * hangju +50);
//            line.setStopY(height - hangju / 2);
            line.setStopX(x);
            lineList.add(line);
        }
        //设计横线线条
        for (int y = 50, flag = 0; y < width && flag < ROW_NUM; y += hangju, flag++) {
            Line line1 = new Line();
            line1.setStartX(50);
            line1.setStartY(y);
            line1.setStopY(y);
            line1.setStopX(8 * lieju + 50);
//            line1.setStopX(width - lieju / 2 - 10);
            lineList.add(line1);
        }
        //添加所有点到  对点定位实体中 方便之后的解释器进行解释
        for (int x = 0; x < LINE_NUM; x++) {
            for (int y = 0; y < ROW_NUM; y++) {
                Gps gps = new Gps();
                gps.setX(x * lieju + 50);
                gps.setY(y * hangju + 50);
                gps.setX_name(GameConfig.x[x]);
                gps.setY_name(GameConfig.y[y]);
                gpsList.add(gps);
            }
        }
        drawBisasLine();
        return drawBoard(width);
    }

    /**
     * 绘制斜线
     */
    public void drawBisasLine() {
        Gps gps = new Gps();
        gps.setX(3 * lieju + 50);
        gps.setY(50);
        Gps xia = getXia(gps, 2);
        Line line = new Line();
        line.setStartX(3 * lieju + 50);
        line.setStopX(xia.getX());
        line.setStartY(50);
        line.setStopY(xia.getY());
        lineList.add(line);

        Gps gps1 = new Gps();
        gps1.setX(3 * lieju + 50);
        gps1.setY(7 * hangju + 50);
        Gps xia1 = getXia(gps1, 2);
        Line line1 = new Line();
        line1.setStartX(3 * lieju + 50);
        line1.setStopX(xia1.getX());
        line1.setStartY(7 * hangju + 50);
        line1.setStopY(xia1.getY());
        lineList.add(line1);

        Gps gps2 = new Gps();
        gps2.setX(5 * lieju + 50);
        gps2.setY(0 + 50);
        Gps xia2 = getShang(gps2, 2);
        Line line2 = new Line();
        line2.setStartX(5 * lieju + 50);
        line2.setStopX(xia2.getX());
        line2.setStartY(0 + 50);
        line2.setStopY(xia2.getY());
        lineList.add(line2);

        Gps gps3 = new Gps();
        gps3.setX(5 * lieju + 50);
        gps3.setY(7 * hangju + 50);
        Gps xia3 = getShang(gps3, 2);
        Line line3 = new Line();
        line3.setStartX(5 * lieju + 50);
        line3.setStopX(xia3.getX());
        line3.setStartY(7 * hangju + 50);
        line3.setStopY(xia3.getY());
        lineList.add(line3);

        for (int i = 1; i <= 7; i++) {
            Line line4 = new Line();
            line4.setStartX(lieju * i + 50);
            line4.setStartY(4 * hangju + 52);
            line4.setStopX(lieju * i + 50);
            line4.setStopY(5 * hangju + 50);
            wList.add(line4);
        }
    }

    /**
     * 绘制棋盘
     *
     * @return 返回棋盘bitmap
     */
    private Bitmap drawBoard(int width) {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setARGB(255, 255, 36, 90);
        Canvas canvas = new Canvas(checkerBitmap);
        for (Line line : lineList) {
            canvas.drawLine(line.getStartX(), line.getStartY(), line.getStopX(), line.getStopY(), paint);
            canvas.save();
        }
        paint.setStrokeWidth(10);
        paint.setColor(0xffffffff);//TODO 棋盘背景色
        for (Line line : wList) {
            canvas.drawLine(line.getStartX(), line.getStartY(), line.getStopX(), line.getStopY(), paint);
            canvas.save();
        }
        paint.setARGB(255, 255, 36, 90);
        paint.setTextSize(width / 12);
        canvas.drawText("楚河      汉界", 2 * lieju - 5 + 50, 5 * hangju - 15 + 50, paint);

        for (Gps gps : gpsList) {
            Log.e("gpses", "相对定位查看：  " + gps.toString());
            int x = gps.getX();
            int y = gps.getY();
//            canvas.drawCircle(x,y,40,paint);
            if (panduanMeihua(x, y)) {
                if (!(x - 50 < 50)) {
                    canvas.drawLine(x - 10, y + 10, x - 10, y + 20, paint);
                    canvas.drawLine(x - 10, y - 10, x - 20, y - 10, paint);
                }
                if ((x - 50 == 8 * lieju))
                    continue;
                canvas.drawLine(x + 10, y - 10, x + 20, y - 10, paint);
                canvas.drawLine(x + 10, y + 10, x + 10, y + 20, paint);
            }
        }
        return checkerBitmap;
    }

    /**
     * 输入指定  兵的位置和炮的位置
     *
     * @param x
     * @param y
     * @return
     */
    private boolean panduanMeihua(int x, int y) {
        return (lieju == x - 50 && y - 50 == 2 * hangju) || (x - 50 == 7 * lieju && y - 50 == 2 * hangju) ||
                (lieju == x - 50 && y - 50 == 7 * hangju) || (x - 50 == 7 * lieju && y - 50 == 7 * hangju) ||
                (x - 50 == 0 && y - 50 == 3 * hangju) || (x - 50 == 2 * lieju && y - 50 == 3 * hangju) ||
                (x - 50 == 4 * lieju && y - 50 == 3 * hangju) || (x - 50 == 6 * lieju && y - 50 == 3 * hangju) ||
                (x - 50 == 8 * lieju && y - 50 == 3 * hangju) ||
                (x - 50 == 0 && y - 50 == 6 * hangju) || (x - 50 == 2 * lieju && y - 50 == 6 * hangju) ||
                (x - 50 == 4 * lieju && y - 50 == 6 * hangju) || (x - 50 == 6 * lieju && y - 50 == 6 * hangju) ||
                (x - 50 == 8 * lieju && y - 50 == 6 * hangju);
    }

    /**
     * 直线下降函数  根据x判断y的位置
     *
     * @return
     */
    private Gps getXia(Gps gps, int num) {
        int x = gps.getX();
        int y = gps.getY();
        gps.setX(x + num * lieju);
        gps.setY(y + num * hangju);
        return gps;
    }

    private Gps getShang(Gps gps, int num) {
        int x = gps.getX();
        int y = gps.getY();
        gps.setX(x - num * lieju);
        gps.setY(y + num * hangju);
        return gps;
    }
}
