package com.example.gamecorona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultScreenActivity extends AppCompatActivity {

    private TextView textViewRes;
    private TextView textViewRes2;
    private Button buttonTry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        textViewRes= findViewById(R.id.textViewRes);
        textViewRes2=findViewById(R.id.textViewRes2);
        buttonTry=findViewById(R.id.buttonTry);

        int score = getIntent().getIntExtra("Score", 0);
        textViewRes.setText(String.valueOf(score));

        SharedPreferences sp= getSharedPreferences("Result",Context.MODE_PRIVATE);
        int highScore = sp.getInt("highScore",0);


        if (score > highScore){

            SharedPreferences.Editor editor =sp.edit();
            editor.putInt("highScore",score);
            editor.commit();

            textViewRes2.setText(String.valueOf(score));
        }else{
            textViewRes2.setText(String.valueOf(highScore));

        }


        buttonTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultScreenActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
