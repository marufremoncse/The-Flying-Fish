package com.example.theflyingfish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class FlyingFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth, canvasHeight;
    private Bitmap background;
    private Bitmap life[] = new Bitmap[2];
    private Paint scorePaint = new Paint();
    private boolean touch = false;
    private int yellowX, yellowY, yellowSpeed = 18;
    private Paint yellowPaint = new Paint();
    private int greenX, greenY, greenSpeed = 26;
    private Paint greenPaint = new Paint();
    private int redX, redY, redSpeed = 10;
    private Paint redPaint = new Paint();
    public int score, lifeOfFish = 3;
    Rect rect;
    private int dWidth, dHeight;

    public FlyingFishView(Context context) {
        super(context);



        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0,0,dWidth,dHeight);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.under_water);
        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_small);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.arrow_heart);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(true);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setTextSize(70);
        scorePaint.setColor(Color.parseColor("#e6f2ff"));
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);
        fishY = 550;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap(background,null,rect,null);

        int minFishY = fish[0].getHeight()-22;
        int maxFishY = canvasHeight - fish[0].getHeight();
        fishY = fishY + fishSpeed;

        if(minFishY>fishY){
            fishY = minFishY;
        }
        if(maxFishY<fishY){
            fishY = maxFishY;

        }
        fishSpeed = fishSpeed + 4;
        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;
        }
        else{
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        yellowX = yellowX - yellowSpeed;
        if(hitBallChecker(yellowX,yellowY)){
            score = score + 10;
            yellowX = -100;
        }
        if(yellowX<0){
            yellowX = canvasWidth + 21;
            yellowY = (int)Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        greenX = greenX - greenSpeed;
        if(hitBallChecker(greenX,greenY)){
            score = score + 15;
            greenX = -100;
        }
        if(greenX<0){
            greenX = canvasWidth + 21;
            greenY = (int)Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(greenX, greenY, 30, greenPaint);

        redX = redX - redSpeed;
        if(hitBallChecker(redX,redY)){
            redX = -100;
            --lifeOfFish;

            if(lifeOfFish==0){
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent gameEndIntent = new Intent(getContext(),GameEndActivity.class);
                gameEndIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameEndIntent.putExtra("score",score);
                getContext().startActivity(gameEndIntent);
            }
        }
        if(redX<0){
            redX = canvasWidth + 21;
            redY = (int)Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(redX, redY, 35, redPaint);

        canvas.drawText("score: "+ score,50,80,scorePaint);

        for(int i=0;i<3;i++){
            int x = 580 + i * 100;
            int y = 20;
            if(i<lifeOfFish){
                canvas.drawBitmap(life[0],x,y,null);
            } else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }
    }

    public boolean hitBallChecker(int x, int y){
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            fishSpeed = -28;
        }
        return true;
    }
}
