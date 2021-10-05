package com.song.chinachess.pieces;

import android.content.Context;

import com.song.chinachess.Gps;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/11
 * The package is com.song.chinachess.pieces
 * This Class is ...
 */
public class ChessConnon extends ChessCar {
    public ChessConnon(Context context, boolean isTeammate) {
        super(context, isTeammate);
    }

    @Override
    public char getName() {
        return '炮';
    }

    @Override
    public boolean isExecute(Gps endGps) {
        return super.isExecute(endGps);
    }
}
