package com.deerslab.pixelart;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by keeper on 21.05.2016.
 */
public class GameTouchListener implements View.OnTouchListener  {

    private final GameView gView;
    private final Context context;
    private int x;
    private int y;

    public GameTouchListener(Context context, GameView gView) {
        super();
        this.context = context;
        this.gView = gView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = (int)event.getX();
                y = (int)event.getY();

                Game.getInstance(context, gView).click(x, y);
                gView.invalidate();
        }

        return false;
    }


}
