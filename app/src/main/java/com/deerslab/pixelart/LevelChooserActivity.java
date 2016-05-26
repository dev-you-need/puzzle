package com.deerslab.pixelart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

public class LevelChooserActivity extends Activity implements View.OnClickListener {

    private ImageButton level1, level2, level3,level4, level5, level6, level7, level8, level9,level10, level11, level12, level13, level14, level15, level16, level17, level18;
    private int currentLevel;
    private int[] pictures = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5, R.drawable.icon6, R.drawable.icon7, R.drawable.icon8, R.drawable.icon9, R.drawable.icon10, R.drawable.icon11, R.drawable.icon12, R.drawable.icon13, R.drawable.icon14, R.drawable.icon15, R.drawable.icon16, R.drawable.icon17, R.drawable.icon18};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        currentLevel = settings.getInt("maxLevel", 1);

        setContentView(R.layout.activity_level_chooser);

        level1 = (ImageButton) findViewById(R.id.idBtnLevel1);
        level2 = (ImageButton) findViewById(R.id.idBtnLevel2);
        level3 = (ImageButton) findViewById(R.id.idBtnLevel3);
        level4 = (ImageButton) findViewById(R.id.idBtnLevel4);
        level5 = (ImageButton) findViewById(R.id.idBtnLevel5);
        level6 = (ImageButton) findViewById(R.id.idBtnLevel6);
        level7 = (ImageButton) findViewById(R.id.idBtnLevel7);
        level8 = (ImageButton) findViewById(R.id.idBtnLevel8);
        level9 = (ImageButton) findViewById(R.id.idBtnLevel9);
        level10 = (ImageButton) findViewById(R.id.idBtnLevel10);
        level11 = (ImageButton) findViewById(R.id.idBtnLevel11);
        level12 = (ImageButton) findViewById(R.id.idBtnLevel12);
        level13 = (ImageButton) findViewById(R.id.idBtnLevel13);
        level14 = (ImageButton) findViewById(R.id.idBtnLevel14);
        level15 = (ImageButton) findViewById(R.id.idBtnLevel15);
        level16 = (ImageButton) findViewById(R.id.idBtnLevel16);
        level17 = (ImageButton) findViewById(R.id.idBtnLevel17);
        level18 = (ImageButton) findViewById(R.id.idBtnLevel18);

        ImageButton[] buttons = {level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, level11, level12, level13, level14, level15, level16, level17, level18};


        for (int i=0; i<buttons.length; i++){
            if (i<currentLevel-1){
                buttons[i].setImageResource(pictures[i]);
                buttons[i].setOnClickListener(this);
            } else if (i == currentLevel-1){
                buttons[i].setImageResource(R.drawable.question);
                buttons[i].setOnClickListener(this);
            } else {
                buttons[i].setImageResource(R.drawable.lock);
            }
            buttons[i].setAdjustViewBounds(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idBtnLevel1:
                Game.currentLevel = 1;
                break;
            case R.id.idBtnLevel2:
                Game.currentLevel = 2;
                break;
            case R.id.idBtnLevel3:
                Game.currentLevel = 3;
                break;
            case R.id.idBtnLevel4:
                Game.currentLevel = 4;
                break;
            case R.id.idBtnLevel5:
                Game.currentLevel = 5;
                break;
            case R.id.idBtnLevel6:
                Game.currentLevel = 6;
                break;
            case R.id.idBtnLevel7:
                Game.currentLevel = 7;
                break;
            case R.id.idBtnLevel8:
                Game.currentLevel = 8;
                break;
            case R.id.idBtnLevel9:
                Game.currentLevel = 9;
                break;
            case R.id.idBtnLevel10:
                Game.currentLevel = 10;
                break;
            case R.id.idBtnLevel11:
                Game.currentLevel = 11;
                break;
            case R.id.idBtnLevel12:
                Game.currentLevel = 12;
                break;
            case R.id.idBtnLevel13:
                Game.currentLevel = 13;
                break;
            case R.id.idBtnLevel14:
                Game.currentLevel = 14;
                break;
            case R.id.idBtnLevel15:
                Game.currentLevel = 15;
                break;
            case R.id.idBtnLevel16:
                Game.currentLevel = 16;
                break;
            case R.id.idBtnLevel17:
                Game.currentLevel = 17;
                break;
            case R.id.idBtnLevel18:
                Game.currentLevel = 18;
                break;
            default:
                Game.currentLevel = 1;
                break;
        }

        startActivity(new Intent(LevelChooserActivity.this, GameActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
