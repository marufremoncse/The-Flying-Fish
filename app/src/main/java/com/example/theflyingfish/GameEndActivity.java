package com.example.theflyingfish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameEndActivity extends AppCompatActivity {

    private Button playAgain;
    private TextView displayScore;
    private String score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        playAgain = (Button)findViewById(R.id.playAgain);
        displayScore = (TextView)findViewById(R.id.textViewScore);
        score = getIntent().getExtras().get("score").toString();
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startOver = new Intent(GameEndActivity.this,MainActivity.class);
                startActivity(startOver);
                }
        });
        displayScore.setText("Your Score: "+score);
    }
}
