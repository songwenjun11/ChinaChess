package com.song.chinachess.pieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.ColorRes;

import com.song.chinachess.Gps;
import com.song.chinachess.R;
import com.song.chinachess.config.GameConfig;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.bean.base
 * This Class is ...  棋子类
 */
public abstract class ChessPieces implements PiecesInterface {
    protected Gps gps;
    public static int width, height;//棋子的宽高
    public static int radius = 40;//棋子半径
    protected Context context;//上下文对象
    protected boolean isTeammate;//是否是自己的棋子  黑色为false
    public Paint paint;//画笔
    public Bitmap bitmap;//位图棋子
    public Paint whitePaint = new Paint();//白色画笔

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps.copy();//拷贝一份  等同于重新创建对象  防止地址相等
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRadius() {
        return radius;
    }

    public Context getContext() {
        return context;
    }

    public boolean isTeammate() {
        return isTeammate;
    }

    public Paint getPaint() {
        return paint;
    }

    public ChessPieces(Context context, boolean isTeammate) {
        this.context = context;
        width = radius * 2;
        height = width;
        this.isTeammate = isTeammate;
        paint = new Paint();
        paint.setTextSize(radius + 10);
        whitePaint.setColor(context.getResources().getColor(R.color.white));
        if (isTeammate) {
            paint.setColor(context.getResources().getColor(R.color.red));
        } else {
            paint.setColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public Bitmap createBitmap() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(radius, radius, radius, paint);
        canvas.drawCircle(radius - 3, radius - 3, radius - 3, whitePaint);
        canvas.drawText(getName() + "", radius - 26, radius + 17, paint);//文字位于圆正中间
        return bitmap;
    }

    public abstract char getName();

    @Override
    public int getChessClass() {
        return GameConfig.getChessClass(getName());
    }

    @Override
    public String toString() {
        return "ChessPieces{" +
                "gps=" + gps.toString() +
                ",name=" + getName() +
                ", context=" + context +
                ", isTeammate=" + isTeammate +
                ", paint=" + paint +
                ", bitmap=" + bitmap +
                ", whitePaint=" + whitePaint +
                '}';
    }
}
