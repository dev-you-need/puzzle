package com.deerslab.pixelart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

/**
 * Created by keeper on 16.05.2016.
 */
public class GameView extends View {

    private Context context;
    private Game game;
    private final Paint paint = new Paint();
    private Typeface font;

    private int width;
    private int height;
    private int borderSize;
    private int gameField;
    private int picPreviewWidth;
    private int picPreviewHeight;
    private int scoreFieldHeight;
    protected int gameFieldX;
    protected int gameFieldY;
    private int scoreFieldX;
    private int scoreFieldY;
    private int picFieldY;
    protected int picPartSizeX;
    protected int picPartSizeY;
    private int picPartPreviewSizeX;
    private int picPartPreviewSizeY;
    private int winX;
    private int winY;
    private int winWidth;
    private int winHeight;

    private Bitmap mainPic;
    private Bitmap[][] picParts;
    private Bitmap[][] picPartsPreview;
    private Bitmap choosePic;
    private Bitmap picPreview;

    //for win animation process;
    private boolean firstTimeAnim = true;
    private final long ANIMATIONTIME = 500000000; //0.5 sec
    private long animationStartTime;


    public GameView(Context context) {
        super(context);
        setOnTouchListener(new GameTouchListener(context, this));
        this.context = context;

        Resources resources = context.getResources();
        font = Typeface.createFromAsset(resources.getAssets(), "font.ttf");
        paint.setTypeface(font);
        paint.setAntiAlias(true);

        game = Game.getInstance(context, this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgrounds(canvas);

        switch (game.gameState) {
            case MainGame:
                drawPicPreview(canvas);
                drawGameField(canvas);
                drawScoreField(canvas);
                break;
            case Win:
                winAnimation(canvas);
                break;
        }

        Log.d("draw", "ok");

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getLayout(w, h);
    }

    private void getLayout(int width, int height){

        Resources resources = context.getResources();
        mainPic = loadMainPic(resources);

        this.width = width;
        this.height = height;

        int baseScreenSize = Math.min(width, height);
        borderSize = baseScreenSize/20;
        gameField = baseScreenSize - borderSize;
        picPreviewWidth = width - gameField - borderSize*2;
        picPreviewHeight = picPreviewWidth /2;
        scoreFieldHeight = height - picPreviewHeight - borderSize*2;

        scoreFieldX = borderSize/2;
        scoreFieldY = borderSize/2;
        picFieldY = height - picPreviewHeight - borderSize/2;

        gameFieldY = borderSize/2;
        gameFieldX = width - gameField - borderSize/2;

        picPartSizeX = gameField/game.picPartsX;
        picPartSizeY = gameField/(2*game.picPartsY);

        picPartPreviewSizeX = picPreviewWidth /game.picPartsX;
        picPartPreviewSizeY = picPreviewHeight /game.picPartsY;

        if (width - borderSize > 2*(height - borderSize)){
            winY = borderSize/2;
            winHeight = height - borderSize;
            winWidth = winHeight*2;
            winX = (width - winWidth)/2;
        } else {
            winX = borderSize/2;
            winWidth = width - borderSize;
            winHeight = winWidth/2;
            winY = (height - winHeight)/2;
        }

        picParts = new Bitmap[game.picPartsX][game.picPartsY];
        picPartsPreview = new Bitmap[game.picPartsX][game.picPartsY];

        Bitmap pic = Bitmap.createScaledBitmap(mainPic, gameField, gameField/2, false);
        Log.d("pic.width=", pic.getWidth()+"");
        Log.d("pic.height=", pic.getHeight()+"");

        for (int x=0; x<game.picPartsX; x++) {
            for (int y = 0; y < game.picPartsY; y++) {
                picParts[x][y] = Bitmap.createBitmap(pic, x*(pic.getWidth()/game.picPartsX), y*(pic.getHeight()/game.picPartsY), picPartSizeX, picPartSizeY);
            }
        }
        pic.recycle();

        picPreview = Bitmap.createScaledBitmap(mainPic, picPreviewWidth, picPreviewHeight, false);
        for (int x=0; x<game.picPartsX; x++) {
            for (int y = 0; y < game.picPartsY; y++) {
               picPartsPreview[x][y] = Bitmap.createBitmap(picPreview, x*(picPreview.getWidth()/game.picPartsX), y*(picPreview.getHeight()/game.picPartsY), picPartPreviewSizeX, picPartPreviewSizeY);
            }
        }

        choosePic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.choose), picPartSizeX, picPartSizeY, false);

        Log.d("getLayout", "ok");
    }

    private void drawPicPreview(Canvas canvas){


        for (int x=0; x<game.picPartsX; x++) {
            for (int y = 0; y < game.picPartsY; y++) {
                if (game.picPartsCatch[x][y]) {
                    canvas.drawBitmap(picPartsPreview[x][y], scoreFieldX + x * picPartPreviewSizeX, picFieldY + y * picPartPreviewSizeY, paint);
                }
            }
        }
    }

    private void drawGameField(Canvas canvas){
        int currentPart;
        for (int x=0; x<game.picPartsX; x++) {
            for (int y = 0; y < 2*game.picPartsY; y++) {
                //canvas.drawBitmap(picParts[x][y], gameFieldX + x*picPartSizeX, gameFieldY + y*picPartSizeY, paint);
//                Log.d("draw partX=", x+"");
//                Log.d("draw partY=", y+"");
                currentPart = game.picPartsField[x][y];
//                Log.d("currentPart", currentPart+"");
                if (!game.picPartsCatch[currentPart % game.picPartsX][currentPart / game.picPartsX]) {
                    canvas.drawBitmap(picParts[currentPart % game.picPartsX][currentPart / game.picPartsX], gameFieldX + x * picPartSizeX, gameFieldY + y * picPartSizeY, paint);
                }
            }
        }

        if (game.alreadyChoose){
            canvas.drawBitmap(choosePic, gameFieldX + game.partXYchoose[0] * picPartSizeX, gameFieldY + game.partXYchoose[1] * picPartSizeY, paint);
        }
    }

    private void drawScoreField(Canvas canvas){
        /*paint.setColor(Color.argb(0, 72, 87, 159));
        canvas.drawRect(scoreFieldX, scoreFieldY, scoreFieldX+picPreviewWidth, scoreFieldY+scoreFieldHeight, paint);
        */
        paint.setTextSize(scoreFieldHeight/5);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(getResources().getString(R.string.clicks) + ": " + game.clicks, scoreFieldX+borderSize, scoreFieldY+borderSize*2+scoreFieldHeight/10, paint);
    }

    private void drawBackgrounds(Canvas canvas){
        canvas.drawARGB(80, 102, 204, 255);
    }

    private void winAnimation(Canvas canvas){
        if (firstTimeAnim){
            animationStartTime = System.nanoTime();
            firstTimeAnim = false;
        }

        double currentTime = System.nanoTime();
        double shift = (currentTime - animationStartTime)/ANIMATIONTIME;
        if (shift < 1){
            Bitmap temp = Bitmap.createScaledBitmap(mainPic, (int)((width-borderSize*2-picPreviewWidth)*shift + picPreviewWidth), (int)((height-borderSize*2-picPreviewHeight)*shift + picPreviewHeight), false);
            temp = Bitmap.createScaledBitmap(mainPic, (int)((1-shift)*picPreviewWidth + shift*winWidth), (int)((1-shift)*picPreviewHeight + shift*winHeight), false);
            //canvas.drawBitmap(temp, borderSize, height-borderSize-temp.getHeight(), paint);
            canvas.drawBitmap(temp, (int)((1-shift)*scoreFieldX + shift*winX), (int)((1-shift)*picFieldY + shift*winY), paint);
            temp.recycle();
            invalidate();
        } else {
            Bitmap temp = Bitmap.createScaledBitmap(mainPic, width-borderSize*2, height-borderSize*2, false);
            temp = Bitmap.createScaledBitmap(mainPic, winWidth, winHeight, false);
            canvas.drawBitmap(temp, winX, winY, paint);
        }
    }

    private int centerText() {
        return (int) ((paint.descent() + paint.ascent()) / 2);
    }

    private Bitmap loadMainPic(Resources resources){
        switch (Game.currentLevel){
            case 1:
                return BitmapFactory.decodeResource(resources, R.drawable.pic1);
            case 2:
                return BitmapFactory.decodeResource(resources, R.drawable.pic2);
            case 3:
                return BitmapFactory.decodeResource(resources, R.drawable.pic3);
            case 4:
                return BitmapFactory.decodeResource(resources, R.drawable.pic4);
            case 5:
                return BitmapFactory.decodeResource(resources, R.drawable.pic5);
            case 6:
                return BitmapFactory.decodeResource(resources, R.drawable.pic6);
            case 7:
                return BitmapFactory.decodeResource(resources, R.drawable.pic7);
            case 8:
                return BitmapFactory.decodeResource(resources, R.drawable.pic8);
            case 9:
                return BitmapFactory.decodeResource(resources, R.drawable.pic9);
            case 10:
                return BitmapFactory.decodeResource(resources, R.drawable.pic10);
            case 11:
                return BitmapFactory.decodeResource(resources, R.drawable.pic11);
            case 12:
                return BitmapFactory.decodeResource(resources, R.drawable.pic12);
            case 13:
                return BitmapFactory.decodeResource(resources, R.drawable.pic13);
            case 14:
                return BitmapFactory.decodeResource(resources, R.drawable.pic14);
            case 15:
                return BitmapFactory.decodeResource(resources, R.drawable.pic15);
            case 16:
                return BitmapFactory.decodeResource(resources, R.drawable.pic16);
            case 17:
                return BitmapFactory.decodeResource(resources, R.drawable.pic17);
            case 18:
                return BitmapFactory.decodeResource(resources, R.drawable.pic18);
            case 19:
                return BitmapFactory.decodeResource(resources, R.drawable.pic19);
            case 20:
                return BitmapFactory.decodeResource(resources, R.drawable.pic20);
            case 21:
                return BitmapFactory.decodeResource(resources, R.drawable.pic21);
            case 22:
                return BitmapFactory.decodeResource(resources, R.drawable.pic22);
            case 23:
                return BitmapFactory.decodeResource(resources, R.drawable.pic23);
            case 24:
                return BitmapFactory.decodeResource(resources, R.drawable.pic24);
            case 25:
                return BitmapFactory.decodeResource(resources, R.drawable.pic25);
            case 26:
                return BitmapFactory.decodeResource(resources, R.drawable.pic26);
            case 27:
                return BitmapFactory.decodeResource(resources, R.drawable.pic27);
            case 28:
                return BitmapFactory.decodeResource(resources, R.drawable.pic28);
            case 29:
                return BitmapFactory.decodeResource(resources, R.drawable.pic29);
            case 30:
                return BitmapFactory.decodeResource(resources, R.drawable.pic30);
            case 31:
                return BitmapFactory.decodeResource(resources, R.drawable.pic31);
            case 32:
                return BitmapFactory.decodeResource(resources, R.drawable.pic32);
            case 33:
                return BitmapFactory.decodeResource(resources, R.drawable.pic33);
            case 34:
                return BitmapFactory.decodeResource(resources, R.drawable.pic34);
            case 35:
                return BitmapFactory.decodeResource(resources, R.drawable.pic35);
            case 36:
                return BitmapFactory.decodeResource(resources, R.drawable.pic36);
            case 37:
                return BitmapFactory.decodeResource(resources, R.drawable.pic37);
            case 38:
                return BitmapFactory.decodeResource(resources, R.drawable.pic38);
            case 39:
                return BitmapFactory.decodeResource(resources, R.drawable.pic39);
            case 40:
                return BitmapFactory.decodeResource(resources, R.drawable.pic40);
            default:
                return BitmapFactory.decodeResource(resources, R.drawable.pic1);
        }
    }
}
