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

    private GameView gameView;
    private Context context;
    private static Game instance;

    protected static GameState gameState;
    protected static int currentLevel;

    protected static int picPartsX = 4;
    protected static int picPartsY = 3;
    protected static boolean[][] picPartsCatch = new boolean[picPartsX][picPartsY];
    protected static int[][] picPartsField = new int[picPartsX][2*picPartsY];

    protected static boolean alreadyChoose;
    protected static int[] partXYchoose = new int[2];
    private int valueChoose;

    public Game(Context context, GameView gameView) {
        this.context = context;
        this.gameView = gameView;

        create();
    }

    public void create(){

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

    public static Game getInstance(Context context, GameView gameView) {
        if (instance == null) {
            instance = new Game(context, gameView);
        }
        return instance;
    }

    enum GameState{
        MainGame, Win
    }

}
