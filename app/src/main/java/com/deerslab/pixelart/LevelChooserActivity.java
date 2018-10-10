package com.deerslab.pixelart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LevelChooserActivity extends Activity implements View.OnClickListener {

    private static final int NUM_PAGES = 2;
    private static final int LOOP = 1000;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private ImageView level1, level2, level3,level4, level5, level6, level7, level8, level9,level10, level11, level12, level13, level14, level15, level16, level17, level18, level19, level20;
    private ImageView level21, level22, level23, level24, level25, level26, level27, level28, level29,level30, level31, level32, level33, level34, level35, level36, level37, level38, level39, level40;
    private TextView tvChooseLevel;
    private Typeface font;
    private int currentLevel;
    private int[] pictures = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5, R.drawable.icon6, R.drawable.icon7, R.drawable.icon8, R.drawable.icon9, R.drawable.icon10,
            R.drawable.icon11, R.drawable.icon12, R.drawable.icon13, R.drawable.icon14, R.drawable.icon15, R.drawable.icon16, R.drawable.icon17, R.drawable.icon18, R.drawable.icon19, R.drawable.icon20,
            R.drawable.icon21, R.drawable.icon22, R.drawable.icon23, R.drawable.icon24, R.drawable.icon25, R.drawable.icon26, R.drawable.icon27, R.drawable.icon28, R.drawable.icon29, R.drawable.icon30,
            R.drawable.icon31, R.drawable.icon32, R.drawable.icon33, R.drawable.icon34, R.drawable.icon35, R.drawable.icon36, R.drawable.icon37, R.drawable.icon38, R.drawable.icon39, R.drawable.icon40};

    String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        font = Typeface.createFromAsset(getResources().getAssets(), "font.ttf");

        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();

        View page = inflater.inflate(R.layout.activity_level_chooser1, null);
        level1 = (ImageView) page.findViewById(R.id.idBtnLevel1);
        level2 = (ImageView) page.findViewById(R.id.idBtnLevel2);
        level3 = (ImageView) page.findViewById(R.id.idBtnLevel3);
        level4 = (ImageView) page.findViewById(R.id.idBtnLevel4);
        level5 = (ImageView) page.findViewById(R.id.idBtnLevel5);
        level6 = (ImageView) page.findViewById(R.id.idBtnLevel6);
        level7 = (ImageView) page.findViewById(R.id.idBtnLevel7);
        level8 = (ImageView) page.findViewById(R.id.idBtnLevel8);
        level9 = (ImageView) page.findViewById(R.id.idBtnLevel9);
        level10 = (ImageView) page.findViewById(R.id.idBtnLevel10);
        level11 = (ImageView) page.findViewById(R.id.idBtnLevel11);
        level12 = (ImageView) page.findViewById(R.id.idBtnLevel12);
        level13 = (ImageView) page.findViewById(R.id.idBtnLevel13);
        level14 = (ImageView) page.findViewById(R.id.idBtnLevel14);
        level15 = (ImageView) page.findViewById(R.id.idBtnLevel15);
        level16 = (ImageView) page.findViewById(R.id.idBtnLevel16);
        level17 = (ImageView) page.findViewById(R.id.idBtnLevel17);
        level18 = (ImageView) page.findViewById(R.id.idBtnLevel18);
        level19 = (ImageView) page.findViewById(R.id.idBtnLevel19);
        level20 = (ImageView) page.findViewById(R.id.idBtnLevel20);

        tvChooseLevel = (TextView) page.findViewById(R.id.tvChooseLevel);
        tvChooseLevel.setTypeface(font);
        pages.add(page);

        page = inflater.inflate(R.layout.activity_level_chooser2, null);
        level21 = (ImageView) page.findViewById(R.id.idBtnLevel21);
        level22 = (ImageView) page.findViewById(R.id.idBtnLevel22);
        level23 = (ImageView) page.findViewById(R.id.idBtnLevel23);
        level24 = (ImageView) page.findViewById(R.id.idBtnLevel24);
        level25 = (ImageView) page.findViewById(R.id.idBtnLevel25);
        level26 = (ImageView) page.findViewById(R.id.idBtnLevel26);
        level27 = (ImageView) page.findViewById(R.id.idBtnLevel27);
        level28 = (ImageView) page.findViewById(R.id.idBtnLevel28);
        level29 = (ImageView) page.findViewById(R.id.idBtnLevel29);
        level30 = (ImageView) page.findViewById(R.id.idBtnLevel30);
        level31 = (ImageView) page.findViewById(R.id.idBtnLevel31);
        level32 = (ImageView) page.findViewById(R.id.idBtnLevel32);
        level33 = (ImageView) page.findViewById(R.id.idBtnLevel33);
        level34 = (ImageView) page.findViewById(R.id.idBtnLevel34);
        level35 = (ImageView) page.findViewById(R.id.idBtnLevel35);
        level36 = (ImageView) page.findViewById(R.id.idBtnLevel36);
        level37 = (ImageView) page.findViewById(R.id.idBtnLevel37);
        level38 = (ImageView) page.findViewById(R.id.idBtnLevel38);
        level39 = (ImageView) page.findViewById(R.id.idBtnLevel39);
        level40 = (ImageView) page.findViewById(R.id.idBtnLevel40);

        tvChooseLevel = (TextView) page.findViewById(R.id.tvChooseLevel);
        tvChooseLevel.setTypeface(font);
        pages.add(page);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        currentLevel = settings.getInt("maxLevel", 1);

        setContentView(viewPager);



        ImageView[] buttons = {level1, level2, level3, level4, level5, level6, level7, level8, level9, level10,
                level11, level12, level13, level14, level15, level16, level17, level18, level19, level20,
                level21, level22, level23, level24, level25, level26, level27, level28, level29, level30,
                level31, level32, level33, level34, level35, level36, level37, level38, level39, level40};


        for (int i=0; i<buttons.length; i++){
            if (i<currentLevel-1){
                buttons[i].setImageResource(pictures[i]);
                //buttons[i].
                buttons[i].setOnClickListener(this);
            } else if (i == currentLevel-1){
                buttons[i].setImageResource(R.drawable.question);
                buttons[i].setOnClickListener(this);
            } else {
                buttons[i].setImageResource(R.drawable.lock);
            }
            buttons[i].setAdjustViewBounds(true);
        }

        if (GameActivity.activity != null){
            GameActivity.activity.finish();
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
            case R.id.idBtnLevel19:
                Game.currentLevel = 19;
                break;
            case R.id.idBtnLevel20:
                Game.currentLevel = 20;
                break;
            case R.id.idBtnLevel21:
                Game.currentLevel = 21;
                break;
            case R.id.idBtnLevel22:
                Game.currentLevel = 22;
                break;
            case R.id.idBtnLevel23:
                Game.currentLevel = 23;
                break;
            case R.id.idBtnLevel24:
                Game.currentLevel = 24;
                break;
            case R.id.idBtnLevel25:
                Game.currentLevel = 25;
                break;
            case R.id.idBtnLevel26:
                Game.currentLevel = 26;
                break;
            case R.id.idBtnLevel27:
                Game.currentLevel = 27;
                break;
            case R.id.idBtnLevel28:
                Game.currentLevel = 28;
                break;
            case R.id.idBtnLevel29:
                Game.currentLevel = 29;
                break;
            case R.id.idBtnLevel30:
                Game.currentLevel = 30;
                break;
            case R.id.idBtnLevel31:
                Game.currentLevel = 31;
                break;
            case R.id.idBtnLevel32:
                Game.currentLevel = 32;
                break;
            case R.id.idBtnLevel33:
                Game.currentLevel = 33;
                break;
            case R.id.idBtnLevel34:
                Game.currentLevel = 34;
                break;
            case R.id.idBtnLevel35:
                Game.currentLevel = 35;
                break;
            case R.id.idBtnLevel36:
                Game.currentLevel = 36;
                break;
            case R.id.idBtnLevel37:
                Game.currentLevel = 37;
                break;
            case R.id.idBtnLevel38:
                Game.currentLevel = 38;
                break;
            case R.id.idBtnLevel39:
                Game.currentLevel = 39;
                break;
            case R.id.idBtnLevel40:
                Game.currentLevel = 40;
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
