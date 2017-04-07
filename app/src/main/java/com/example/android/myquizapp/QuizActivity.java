package com.example.android.myquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class QuizActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private TextView scoreTextTextView;
    private ListView listView;
    private ArrayList<QuizQuestion> arrayOfQuizQuestions;
    private boolean finishedQuiz = false;
    private static final int NUM_OF_QUESTIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // sets value of variables for later use
        listView = (ListView) findViewById(R.id.quiz_listView);
        scoreTextView = (TextView) findViewById(R.id.score_textView);
        scoreTextTextView = (TextView) findViewById(R.id.score_text_textView);
        scoreTextTextView.setVisibility(TextView.INVISIBLE);

        if (savedInstanceState == null) {
            // get ArrayList of randomly selected QuizQuestions
            QuestionArrayGenerator qag = new QuestionArrayGenerator(this);
            arrayOfQuizQuestions = qag.getRandomQuestionList(NUM_OF_QUESTIONS);

            // sets an instance of the QuizQuestionAdapter
            QuizQuestionAdapter adapter = new QuizQuestionAdapter(this, arrayOfQuizQuestions);
            listView.setAdapter(adapter);
        } else {
            arrayOfQuizQuestions = savedInstanceState.getParcelableArrayList("arrayOfQuizQuestions");
            finishedQuiz = savedInstanceState.getBoolean("finishedQuiz");
            if (finishedQuiz) {
                showResult(scoreTextView);
            } else {
                QuizQuestionAdapter adapter = new QuizQuestionAdapter(this, arrayOfQuizQuestions);
                listView.setAdapter(adapter);
            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("arrayOfQuizQuestions", arrayOfQuizQuestions);
        outState.putBoolean("finishedQuiz", finishedQuiz);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                reset(scoreTextView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showResult(View view) {
        finishedQuiz = true;
        int score = countScore();
        String scoreString = score + "/" + arrayOfQuizQuestions.size();

        scoreTextView.setText(scoreString);
        scoreTextTextView.setVisibility(TextView.VISIBLE);
        QuizResultAdapter adapter = new QuizResultAdapter(this, arrayOfQuizQuestions);
        listView.setAdapter(adapter);
    }

    //
    public void reset(View view) {
        finishedQuiz = false;
        scoreTextTextView.setVisibility(TextView.INVISIBLE);
        scoreTextView.setText("");

        QuestionArrayGenerator qag = new QuestionArrayGenerator(this);
        arrayOfQuizQuestions = qag.getRandomQuestionList(NUM_OF_QUESTIONS);

        for (QuizQuestion question : arrayOfQuizQuestions) {
            question.setChosen(0);
        }
        QuizQuestionAdapter adapter = new QuizQuestionAdapter(this, arrayOfQuizQuestions);

        listView.setAdapter(adapter);

    }

    // helper method for counting users score
    private int countScore() {
        int count = 0;
        for (int i = 0; i < arrayOfQuizQuestions.size(); i++) {

            QuizQuestion question = arrayOfQuizQuestions.get(i);

            if (question.getChosen() == question.getAnswer()) {
                count++;
            }
        }
        return count;
    }
}
