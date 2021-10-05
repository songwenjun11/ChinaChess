package com.song.chinachess.pieces;

import android.content.Context;

import com.song.chinachess.Gps;

/**
 * SongWenjun
 * Created by dell
 * on 2020/2/12
 * The package is com.song.chinachess.pieces
 * This Class is ...
 */
public class ChessMarale extends ChessPieces {
    public ChessMarale(Context context, boolean isTeammate) {
        super(context, isTeammate);
    }

    @Override
    public char getName() {
        return '士';
    }

    @Override
    public boolean isExecute(Gps endGps) {
        return true;
    }
}
