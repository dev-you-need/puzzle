package com.deerslab.pixelart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by keeper on 18.05.2016.
 */
public class Game {

    private static GameView gameView;
    private static Context context;
    private static Game instance;

    protected GameState gameState;
    protected static int currentLevel;

    protected int picPartsX = 4;
    protected int picPartsY = 3;
    protected boolean[][] picPartsCatch = new boolean[picPartsX][picPartsY];
    protected int[][] picPartsField = new int[picPartsX][2*picPartsY];

    protected boolean alreadyChoose;
    protected int[] partXYchoose = new int[2];
    private int valueChoose;

    String TAG = this.getClass().getSimpleName();

    protected int clicks;

    public Game(Context context, GameView gameView) {
        this.context = context;
        this.gameView = gameView;

        create();
    }

    public void create(){

        gameDifficultSetter(currentLevel);

        picPartsCatch = new boolean[picPartsX][picPartsY];
        picPartsField = new int[picPartsX][2*picPartsY];
        partXYchoose = new int[2];
        clicks = 0;

        ArrayList<Integer>usedCells = new ArrayList<Integer>();
        Random random = new Random();
        int randomInt;
        int fieldSize = picPartsX*picPartsY;

        for (int i=0; i<fieldSize; i++){

            randomInt = random.nextInt(fieldSize*2);
            while (usedCells.contains(randomInt)){
                randomInt = (++randomInt)%(fieldSize*2);
            }
            usedCells.add(randomInt);
            picPartsField[randomInt%picPartsX][randomInt/picPartsX] = i;


            randomInt = random.nextInt(fieldSize*2);
            while (usedCells.contains(randomInt)){
                randomInt = (++randomInt)%(fieldSize*2);
            }
            usedCells.add(randomInt);
            picPartsField[randomInt%picPartsX][randomInt/picPartsX] = i;
        }

        gameState = GameState.MainGame;

        Log.d("usedCells size", usedCells.size()+"");
    }

    public void click(int coordX, int coordY){

        switch (gameState) {

            case MainGame:

                int partX;
                int partY;

                Log.d("cond 1", "" + (coordX > +gameView.gameFieldX + picPartsX * gameView.picPartSizeX));
                Log.d("cond 2", "" + (coordY > +gameView.gameFieldY + 2 * picPartsY * gameView.picPartSizeY));
                Log.d("cond 3", "" + (coordX < +gameView.gameFieldX));
                Log.d("cond 4", "" + (coordY < +gameView.gameFieldY));

                if ((coordX > (gameView.gameFieldX + picPartsX * gameView.picPartSizeX))
                        || (coordY > (gameView.gameFieldY + 2 * picPartsY * gameView.picPartSizeY))
                        || coordX < gameView.gameFieldX
                        || coordY < gameView.gameFieldY) {
                    return;
                } else {
                    partX = (coordX - gameView.gameFieldX) / gameView.picPartSizeX;
                    partY = (coordY - gameView.gameFieldY) / gameView.picPartSizeY;

                    Log.d("choose partX=", "" + partX);
                    Log.d("choose partY=", "" + partY);
                    Log.d("partXYchoose", partXYchoose[0] + ", " + partXYchoose[1]);

                    valueChoose = picPartsField[partX][partY];
                    if (!picPartsCatch[valueChoose % picPartsX][valueChoose / picPartsX]) {
                        Log.d("alreadyChoose", "" + alreadyChoose);
                        if (alreadyChoose) {
                            Log.d("partX==partXYchoose[0]", "" + (partX == partXYchoose[0]));
                            Log.d("partY==partXYchoose[1]", "" + (partY == partXYchoose[1]));
                            if ((partX == partXYchoose[0]) && (partY == partXYchoose[1])) {
                                alreadyChoose = false;
                            } else {
                                Log.d("sovpadaet?", "" + (picPartsField[partX][partY] == picPartsField[partXYchoose[0]][partXYchoose[1]]));
                                if (picPartsField[partX][partY] == picPartsField[partXYchoose[0]][partXYchoose[1]]) {
                                    valueChoose = picPartsField[partX][partY];
                                    picPartsCatch[valueChoose % picPartsX][valueChoose / picPartsX] = true;
                                    alreadyChoose = false;
                                }
                            }

                        } else {
                            partXYchoose[0] = partX;
                            partXYchoose[1] = partY;
                            alreadyChoose = true;
                        }
                    }

                    clicks++;
                }

                if (endGameCheck()) {
                    gameState = GameState.Win;
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();

                    if (settings.getInt("maxLevel", 1) == currentLevel){
                        editor.putInt("maxLevel", currentLevel+1);
                        editor.apply();
                    }
                }
                gameView.invalidate();

                break;

            case Win:
                GameActivity.activity.finish();
                context.startActivity(new Intent(context, LevelChooserActivity.class));

                break;
        }
    }

    private boolean endGameCheck(){
        for (int x=0; x<picPartsX; x++) {
            for (int y = 0; y < picPartsY; y++) {
                if (!picPartsCatch[x][y]){
                    return false;
                }
            }
        }

        return true;
    }

    private void gameDifficultSetter(int currentLevel){
        switch (currentLevel){
            case 1:
                picPartsX=3;
                picPartsY=3;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                picPartsX=4;
                picPartsY=3;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                picPartsX=4;
                picPartsY=4;
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                picPartsX=5;
                picPartsY=4;
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                picPartsX=5;
                picPartsY=5;
                break;
            default:
                picPartsX=6;
                picPartsY=5;
                break;
        }
    }

    public static Game getInstance(Context context1, GameView gameView1) {
        if (instance == null) {
            instance = new Game(context1, gameView1);
        } else {
            context = context1;
            gameView = gameView1;
        }
        return instance;
    }

    enum GameState{
        MainGame, Win
    }
}
