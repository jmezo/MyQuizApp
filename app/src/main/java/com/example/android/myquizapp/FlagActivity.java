package com.example.android.myquizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Activity of the basic quiz
 */

public class FlagActivity extends AppCompatActivity {
    private ArrayList<QuizQuestion> arrayOfFlagQuestions;
    private LinearLayout flagsLayout;
    private ImageView flagImage;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;
    private Button[] choices;
    private TextView currentQuestionText;
    private TextView scoreText;
    private QuestionArrayGenerator questionArrayGenerator;
    private int currentQuestion = 0;
    private int score = 0;
    private int numOfQuestions = 10;
    private boolean finishedQuiz = false;
    private static final int PAUSE_BETWEEN_QUESTIONS = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
        //set values of Views, for later use
        flagsLayout = (LinearLayout) findViewById(R.id.flags_layout);
        flagImage = (ImageView) findViewById(R.id.flag_image_view);
        currentQuestionText = (TextView) findViewById(R.id.current_question);
        scoreText = (TextView) findViewById(R.id.flag_score);
        choice1 = (Button) findViewById(R.id.flag_choice1);
        choice2 = (Button) findViewById(R.id.flag_choice2);
        choice3 = (Button) findViewById(R.id.flag_choice3);
        choice4 = (Button) findViewById(R.id.flag_choice4);
        choices =new Button[]{choice1,choice2,choice3, choice4};

        //set onClickListeners for the choice buttons
        setOnClickMethods(1);
        setOnClickMethods(2);
        setOnClickMethods(3);
        setOnClickMethods(4);

        //if else statements for saving and restoring variables on screen rotation
        if(savedInstanceState == null){
            questionArrayGenerator = new QuestionArrayGenerator(this);
            arrayOfFlagQuestions =questionArrayGenerator.getRandomFlagList(numOfQuestions);
            setViewsToQuestion(currentQuestion);
        }
        else{
            arrayOfFlagQuestions = savedInstanceState.getParcelableArrayList("arrayOfFlagQuestions");
            currentQuestion = savedInstanceState.getInt("currentQuestion");
            score = savedInstanceState.getInt("score");
            numOfQuestions = savedInstanceState.getInt("numOfQuestions");
            finishedQuiz = savedInstanceState.getBoolean("finishedQuiz");

            if(finishedQuiz){
                //if the quiz is finished by the user, sets the screen to the last questions answer
                String scoreString = "your score:"+score+"/"+numOfQuestions;
                scoreText.setText(scoreString);
                currentQuestion = numOfQuestions-1;
                setViewsToQuestion(currentQuestion);
                int choice = arrayOfFlagQuestions.get(currentQuestion).getChosen();
                choiceClicked(choice+1);
                setButtonsClickable(false);
            }
            else {
                setViewsToQuestion(currentQuestion);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("arrayOfFlagQuestions", arrayOfFlagQuestions);
        outState.putInt("currentQuestion",currentQuestion);
        outState.putInt("score",score);
        outState.putInt("numOfQuestions",numOfQuestions);
        outState.putBoolean("finishedQuiz",finishedQuiz);
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                resetQuestions();
                return true;
            case R.id.action_set_question_number_5:
                setNumOfQuestions(5);
                return true;
            case R.id.action_set_question_number_10:
                setNumOfQuestions(10);
                return true;
            case R.id.action_set_question_number_20:
                setNumOfQuestions(20);
                return true;
            case R.id.action_set_question_number_30:
                setNumOfQuestions(30);
                return true;
            case R.id.action_set_question_number_40:
                setNumOfQuestions(40);
                return true;
            case R.id.action_set_question_number_50:
                setNumOfQuestions(50);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //sets the views according to the variables of the current QuizQuestion object
    private void setViewsToQuestion(int currentQuestion){
        QuizQuestion question = arrayOfFlagQuestions.get(currentQuestion);

        int id = getResources().getIdentifier(question.getQuestion(),"drawable",getPackageName());
        flagImage.setImageResource(id);

        choice1.setText(question.getChoice1());
        choice1.setBackgroundResource(R.drawable.custom_button_basic);
        choice2.setText(question.getChoice2());
        choice2.setBackgroundResource(R.drawable.custom_button_basic);
        choice3.setText(question.getChoice3());
        choice3.setBackgroundResource(R.drawable.custom_button_basic);
        choice4.setText(question.getChoice4());
        choice4.setBackgroundResource(R.drawable.custom_button_basic);

        String currentQuestionString = ""+(currentQuestion+1)+"/"+numOfQuestions;
        currentQuestionText.setText(currentQuestionString);
    }

    //changes the colors of the clicked button, to red or green according to the correct answer
    private  void choiceClicked(int choiceNum){
        int answer = arrayOfFlagQuestions.get(currentQuestion).getAnswer();
        Button choiceButton = choices[choiceNum-1];
        arrayOfFlagQuestions.get(currentQuestion).setChosen(choiceNum-1);

        if(answer == choiceNum){
            choiceButton.setBackgroundResource(R.drawable.custom_button_correct);
            score++;

        }
        else{
            choiceButton.setBackgroundResource(R.drawable.custom_button_wrong);

            Button correctAns = choices[answer-1];
            correctAns.setBackgroundResource(R.drawable.custom_button_correct);

        }
        setButtonsClickable(false);
    }
    //sets an onClickListener to a choice button
    private void setOnClickMethods(final int choiceNum){
        Button choiceButton = choices[choiceNum-1];
        choiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceClicked(choiceNum);

                //goes to next question automatically
                flagsLayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        currentQuestion++;
                        if(currentQuestion < numOfQuestions){
                            setViewsToQuestion(currentQuestion);
                            setButtonsClickable(true);
                        }
                        else{
                            String scoreString = "your score:"+score+"/"+numOfQuestions;
                            scoreText.setText(scoreString);
                            finishedQuiz = true;
                        }
                    }
                }, PAUSE_BETWEEN_QUESTIONS);

            }
        });
    }
    //changes all the choice buttons to clickable/non clickable
    private void setButtonsClickable(boolean clickable){
        for(Button button : choices){
            button.setClickable(clickable);
        }
    }
    //resets the activity, generates a new list of questions
    private void resetQuestions(){
        finishedQuiz = false;
        questionArrayGenerator = new QuestionArrayGenerator(this);
        arrayOfFlagQuestions =questionArrayGenerator.getRandomFlagList(numOfQuestions);
        currentQuestion = 0;
        score = 0;
        scoreText.setText("");
        setViewsToQuestion(currentQuestion);
        setButtonsClickable(true);

    }
    //sets the number of total questions, and resets the activity
    private void setNumOfQuestions(int number){
        numOfQuestions = number;
        resetQuestions();
    }
}
