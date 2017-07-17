package com.example.android.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * The activity that starts when user starts the app
 */

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void goToQuiz(View view){
        Intent i = new Intent(StartActivity.this, QuizActivity.class);
        startActivity(i);
    }

    public void goToFlag(View view){
        Intent i = new Intent(StartActivity.this, FlagActivity.class);
        startActivity(i);
    }

    public void goToRequired(View view){
        Intent i = new Intent(StartActivity.this, RequiredChangesActivity.class);
        startActivity(i);
    }
}
