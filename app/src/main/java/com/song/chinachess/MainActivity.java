package com.song.chinachess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.song.chinachess.view.GameView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameView gameView = findViewById(R.id.gameView);
//        gameView.setBoardLLenght(700);//控制棋盘大小
    }
}
