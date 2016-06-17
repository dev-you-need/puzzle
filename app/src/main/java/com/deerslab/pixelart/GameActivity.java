package com.deerslab.pixelart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by keeper on 16.05.2016.
 */
public class GameActivity extends Activity {

    private GameView gameView;
    private Game game;

    protected static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;

        gameView = new GameView(this);
        game = Game.getInstance(this, gameView);

        game.create();

        setContentView(gameView);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, LevelChooserActivity.class));
        finish();
    }


}


