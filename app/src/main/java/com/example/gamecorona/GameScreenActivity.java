package com.example.gamecorona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreenActivity extends AppCompatActivity {

    private ConstraintLayout cl;
    private TextView textViewResultGame;
    private TextView textViewStartGame;
    private ImageView carsymbol;
    private ImageView pillsymbol;
    private ImageView bagsymbol;
    private ImageView coronasymbol;

    // carsymbol: This media comes from the Centers for Disease Control and Prevention's Public Health Image Library (PHIL),
    // with identification number #23312
    //other images: via;https://pixabay.com/


    //Positions
    private int mainX;
    private int mainY;
    private int pillsymbolX;
    private int pillsymbolY;
    private int bagsymbolX;
    private int bagsymbolY;
    private int coronasymbolX;
    private int coronasymbolY;


    //Size Details
    private int screenWidth;
    private int screenHeight;
    private int mainWidth;
    private int mainHeight;

    //Speeds
    private int carSpeed;
    private int pillSpeed;
    private int bagSpeed;
    private int coronaSpeed;


    //Controllers
    private boolean touchControl=false;
    private boolean startControl=false;

    private int score = 0;

    private Timer timer = new Timer();
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        cl= findViewById(R.id.cl);
        textViewResultGame=findViewById(R.id.textViewResult);
        textViewStartGame=findViewById(R.id.textViewStartGame);
        carsymbol=findViewById(R.id.carsymbol);
        pillsymbol=findViewById(R.id.pillsymbol);
        bagsymbol=findViewById(R.id.bagsymbol);
        coronasymbol=findViewById(R.id.coronasymbol);

        //Objects out to screen
        coronasymbol.setX(-180);
        coronasymbol.setY(-180);
        pillsymbol.setX(-180);
        pillsymbol.setY(-180);
        bagsymbol.setX(-180);
        bagsymbol.setY(-180);



        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (startControl) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        Log.e("MotionEvent", "Touched Screen");
                        touchControl = true;

                    }

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        Log.e("MotionEvent", " Stopped Touching");
                        touchControl = false;

                    }

                } else {

                    startControl = true;

                    textViewStartGame.setVisibility(View.INVISIBLE);

                    mainX = (int) carsymbol.getX();
                    mainY = (int) carsymbol.getY();

                    mainWidth = carsymbol.getWidth();
                    mainHeight = carsymbol.getHeight();
                    screenWidth = cl.getWidth();
                    screenHeight = cl.getHeight();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    carAction();
                                    objectactions();
                                    combatControl();

                                }
                            });
                        }
                    }, 0, 20);

                }



            return true;

            }
        });
    }

    public void carAction() {

        carSpeed = Math.round(screenHeight/140);

        if (touchControl){
            mainY-=carSpeed;
        }else{
            mainY+=carSpeed;
        }
        if (mainY <= 0){
            mainY = 0 ;
        }
        if (mainY >= screenHeight - mainHeight){
            mainY = screenHeight - mainHeight;

        }

        carsymbol.setY(mainY);
    }

    public void objectactions(){

        pillSpeed = Math.round(screenWidth/100);
        coronaSpeed = Math.round(screenWidth/40);
        bagSpeed = Math.round(screenWidth/309);

        coronasymbolX-=coronaSpeed;

        if (coronasymbolX < 0) {
            coronasymbolX = screenWidth + 20;
            coronasymbolY = (int) Math.floor(Math.random() * screenHeight);
        }

        coronasymbol.setX(coronasymbolX);
        coronasymbol.setY(coronasymbolY);


        pillsymbolX-=pillSpeed;

        if (pillsymbolX < 0) {
            pillsymbolX = screenWidth + 20;
            pillsymbolY = (int) Math.floor(Math.random() * screenHeight);
        }

        pillsymbol.setX(pillsymbolX);
        pillsymbol.setY(pillsymbolY);

        bagsymbolX-=bagSpeed;

        if (bagsymbolX < 0) {
            bagsymbolX = screenWidth + 20;
            bagsymbolY = (int) Math.floor(Math.random() * screenHeight);
        }

        bagsymbol.setX(bagsymbolX);
        bagsymbol.setY(bagsymbolY);



    }

    public void combatControl(){

       int pillCenterX = pillsymbolX + pillsymbol.getWidth()/2;
       int pillCenterY = pillsymbolY + pillsymbol.getWidth()/2;

       if (0 <= pillCenterX && pillCenterX <= mainWidth
               && mainY <= pillCenterY && pillCenterY <= mainY+mainHeight){

           score+=10;
           pillsymbolX =-10;
       }

        int bagCenterX = bagsymbolX + bagsymbol.getWidth()/2;
        int bagCenterY = bagsymbolY + bagsymbol.getWidth()/2;

        if (0 <= bagCenterX && bagCenterX <= mainWidth
                && mainY <= bagCenterY && bagCenterY <= mainY+mainHeight){

            score+=30;
            bagsymbolX =-10;
        }


        int coronaCenterX = coronasymbolX + coronasymbol.getWidth()/2;
        int coronaCenterY = coronasymbolY + coronasymbol.getWidth()/2;

        if (0 <= coronaCenterX && coronaCenterX <= mainWidth
                && mainY <= coronaCenterY && coronaCenterY <= mainY+mainHeight){

            coronasymbolX =-10;

            //Timer stopping
            timer.cancel();
            timer=null;

            Intent intent = new Intent(GameScreenActivity.this, ResultScreenActivity.class);
            intent.putExtra("Score",score);
            startActivity(intent);

        }

        textViewResultGame.setText(String.valueOf(score));


    }
}
