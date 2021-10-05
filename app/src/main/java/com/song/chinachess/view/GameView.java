package com.song.chinachess.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.song.chinachess.Gps;
import com.song.chinachess.R;
import com.song.chinachess.config.BoardLayout;
import com.song.chinachess.event.GameChessListener;
import com.song.chinachess.pieces.CheckerBoard;
import com.song.chinachess.pieces.ChessPieces;
import com.song.chinachess.utils.Interpreter;

import java.util.Date;

import static com.song.chinachess.utils.Interpreter.blackPiecesList;
import static com.song.chinachess.utils.Interpreter.gpsList;
import static com.song.chinachess.utils.Interpreter.redPiecesList;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.view
 * This Class is ...  象棋游戏的自定义View
 */
public class GameView extends View {

    Interpreter interpreter;//棋盘解释器
    private Paint rPaint;//红方画笔
    private Paint bPaint;//黑方画笔
    private boolean running;//游戏是否开始
    private Gps isSelectGps;//选中的坐标
    private Bitmap checkerBitmap;//棋盘位图
    private ChessPieces redChess;//当前被选中红色的棋子
    private ChessPieces blackChess;//当前被选中黑色的棋子
    private BoardLayout boardLayout = BoardLayout.RED;//当前该那方走棋  初始值为  红棋先走

    private GameChessListener gameChessListener;//事件监听

    public void setGameChessListener(GameChessListener gameChessListener) {
        this.gameChessListener = gameChessListener;
    }

    /**
     * 初始化棋盘
     *
     * @param width
     */
    public void initBoard(int width) {
        checkerBitmap = CheckerBoard.getInstance().getCheckerBitmap(width);//建议值大于600
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (checkerBitmap == null) {
            int widthResult = 0;
            //view根据xml中layout_width和layout_height测量出对应的宽度和高度值，
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            switch (widthSpecMode) {
                case MeasureSpec.UNSPECIFIED:
                    widthResult = widthSpecSize;
                    break;
            }

            int heightResult = 0;
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
            switch (heightSpecMode) {
                case MeasureSpec.UNSPECIFIED:
                    heightResult = heightSpecSize;
                    break;
            }
            setMeasuredDimension(widthResult, heightResult);
            return;
        }
        setMeasuredDimension(checkerBitmap.getWidth(), checkerBitmap.getHeight());
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    /**
     * 初始化画笔
     */
    private void initPaint(Context context) {
        interpreter = Interpreter.getInstance(context, this);//初始化棋盘解释器
        rPaint = new Paint();
        bPaint = new Paint();
        rPaint.setColor(context.getResources().getColor(R.color.red));
        bPaint.setColor(context.getResources().getColor(R.color.black));
    }

    boolean s = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制棋盘
        Log.e("width", getWidth() + "");
        checkerBitmap = CheckerBoard.getInstance().getCheckerBitmap(getWidth());//建议值大于600
        canvas.drawBitmap(checkerBitmap, 0, 0, rPaint);

        if (!s) {
            interpreter.init();//初始化棋子
        }
        s = true;

        if (isSelectGps != null) {
            Gps.Fixed fixed = isSelectGps.getFixed();
            Rect rect = new Rect(fixed.getLd().x, fixed.getLu().y, fixed.getRu().x, fixed.getRd().y);
            canvas.drawRect(rect, rPaint);
        }
        for (ChessPieces chessPieces : redPiecesList) {
            Log.e("test", chessPieces.toString());
        }
        //绘制红方棋子
        for (ChessPieces pieces : redPiecesList) {
            char y_name = pieces.getGps().getY_name();
            Gps gps = interpreter.transitionX_n(pieces.getGps());
            if (y_name == ' ') {
                gps = pieces.getGps();
            }
            if (y_name == '8' && pieces.getGps().getX_name() == 'e') {
                Log.e("asdasd", "我执行了");
            }
            canvas.drawBitmap(pieces.createBitmap(), gps.getX() - pieces.getRadius(), gps.getY() - pieces.getRadius(), pieces.getPaint());
        }
        //绘制黑方棋子
        for (ChessPieces pieces : blackPiecesList) {
            char y_name = pieces.getGps().getY_name();
            Gps gps = interpreter.transitionX_n(pieces.getGps());
            if (y_name == ' ') {
                gps = pieces.getGps();
            }
            canvas.drawBitmap(pieces.createBitmap(), gps.getX() - pieces.getRadius(), gps.getY() - pieces.getRadius(), pieces.getPaint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (!running) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //点击棋子的事件
                //第一步：需要将x  y的绝对坐标值转化为相对坐标值
                Gps downGps = switchRelative((int) x, (int) y);
                if (downGps == null) {
                    return true;
                }
                Log.e("down", downGps.toString());
                //  TODO 补充  还需要判断当前有没有选中的棋子
                if (redChess != null || blackChess != null) {
                    //  TODO 补充     有：需要执行棋子的  走棋方法进行规则判断
                    ChessPieces have = isHave(downGps);
                    if (have == null) {
                        //这里为点击的位置自己的棋子
                        boolean execute;
                        if (boardLayout == BoardLayout.RED) {
                            execute = redChess.isExecute(downGps);
                            if (execute) {
                                if (gameChessListener != null) {
                                    gameChessListener.moveChess(redChess);
                                }
                                redChess = null;
                                isSelectGps = downGps.copy();
                                boardLayout = boardLayout == BoardLayout.RED ? BoardLayout.BLACK : BoardLayout.RED;
                                invalidate();
                            }
                        } else {
                            execute = blackChess.isExecute(downGps);
                            if (execute) {
                                if (gameChessListener != null) {
                                    gameChessListener.moveChess(blackChess);
                                }
                                blackChess = null;
                                isSelectGps = downGps.copy();
                                boardLayout = boardLayout == BoardLayout.RED ? BoardLayout.BLACK : BoardLayout.RED;
                                invalidate();
                            }
                        }
                    } else {
                        //获取点击位置棋子
                        boolean execute = false;
                        if (redChess != null) {
                            //点击了红棋
                            if (!have.isTeammate()) {
                                //该红棋走时点击了黑棋  判断是否 符合走棋规则
                                execute = redChess.isExecute(downGps);
                                if (execute) {
                                    ChessPieces eat = isEat(downGps);
                                    if (eat != null) {
                                        if (gameChessListener != null) {
                                            gameChessListener.eat(redChess, eat);
                                        }
                                        blackPiecesList.remove(eat);
                                        gameOver();
                                    }
                                }
                            }
                            if (gameChessListener != null) {
                                gameChessListener.selectChess(redChess);
                            }
                            if (!execute) {
                                redChess = have;
                            }
                            isSelectGps = downGps;
                            boardLayout = boardLayout == BoardLayout.RED ? BoardLayout.BLACK : BoardLayout.RED;
                            redChess = null;
                            blackChess = null;
                            invalidate();
                            return true;
                        }
                        if (blackChess != null) {
                            //点击了黑棋
                            if (have.isTeammate()) {
                                execute = blackChess.isExecute(downGps);
                                if (execute) {
                                    ChessPieces eat = isEat(downGps);
                                    if (eat != null) {
                                        if (gameChessListener != null) {
                                            gameChessListener.eat(blackChess, eat);
                                        }
                                        redPiecesList.remove(eat);
                                        gameOver();
                                    }
                                }
                            }
                            if (gameChessListener != null) {
                                gameChessListener.selectChess(blackChess);
                            }
                            if (!execute) {
                                blackChess = have;
                            }
                            isSelectGps = downGps;
                            boardLayout = boardLayout == BoardLayout.RED ? BoardLayout.BLACK : BoardLayout.RED;
                            redChess = null;
                            blackChess = null;
                            invalidate();
                            return true;
                        }
                        boardLayout = boardLayout == BoardLayout.RED ? BoardLayout.BLACK : BoardLayout.RED;
                    }
                } else {
                    //这里是没有选过棋子
                    ChessPieces have = isHave(downGps);
                    if (have != null) {
                        if (boardLayout == BoardLayout.RED) {
                            if (have.isTeammate()) {
                                isSelectGps = downGps;
                                redChess = have;
                                invalidate();
                            } else {
                                //提示  请选择自己的棋子
                                Toast.makeText(getContext(), "请选择自己的棋子", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (!have.isTeammate()) {
                                isSelectGps = downGps;
                                blackChess = have;
                                invalidate();
                            } else {
                                //提示   请选择自己的棋子
                                Toast.makeText(getContext(), "请选择自己的棋子", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (gameChessListener != null) {
                            gameChessListener.selectChess(have);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 游戏结束
     *
     * @param boardLayout 胜利方
     */
    public void gameOver(BoardLayout boardLayout) {
        running = true;
        isSelectGps = null;
        invalidate();
    }

    private void gameOver() {
        int redflag = 0;
        int blackflag = 0;
        for (ChessPieces chessPieces : redPiecesList) {
            if (chessPieces.getName() == '将') {
                redflag++;
            }
        }
        for (ChessPieces chessPieces : blackPiecesList) {
            if (chessPieces.getName() == '将') {
                blackflag++;
            }
        }
        if (redflag == 0) {
            gameOver(BoardLayout.BLACK);
            if (gameChessListener != null)
                gameChessListener.gameOver(BoardLayout.BLACK);
        } else if (blackflag == 0) {
            gameOver(BoardLayout.RED);
            if (gameChessListener != null)
                gameChessListener.gameOver(BoardLayout.RED);
        }
    }

    /**
     * 是否吃棋子
     *
     * @return
     */
    private ChessPieces isEat(Gps gps) {
        if (boardLayout == BoardLayout.RED) {
            for (ChessPieces chess : blackPiecesList) {
                if (gps.equalsX_n(chess.getGps())) {
                    return chess;
                }
            }
        } else {
            for (ChessPieces chess : redPiecesList) {
                if (gps.equalsX_n(chess.getGps())) {
                    return chess;
                }
            }
        }
        return null;
    }

    /**
     * 判断当前位置有没有  棋子
     *
     * @param gps 点击的位置
     * @return 返回null代表没有棋子
     */
    private ChessPieces isHave(Gps gps) {
        for (ChessPieces chessPieces : redPiecesList) {
            Gps gps1 = chessPieces.getGps();
            Log.e("down", chessPieces.toString());
            if (gps.equalsX_n(gps1)) {
                return chessPieces;
            }
        }
        for (ChessPieces chessPieces : blackPiecesList) {
            Gps gps1 = chessPieces.getGps();
            if (gps.equalsX_n(gps1)) {
                return chessPieces;
            }
        }
        return null;
    }

    /**
     * 将x  y转化为Gps
     *
     * @param x
     * @param y
     * @return 返回GPs对象，有空的可能
     */
    private Gps switchRelative(int x, int y) {
        for (Gps gps : gpsList) {
            Gps.Fixed fixed = gps.getFixed();
            if (fixed.isSelect(x, y)) {
                return gps;
            }
        }
        return null;
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        running = true;
    }
}
