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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        game = Game.getInstance(this, gameView);

        setContentView(gameView);
    }

    protected void startLevelChooserActivity(){
        startActivity(new Intent(GameActivity.this, LevelChooserActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, LevelChooserActivity.class));
        finish();
    }
}
