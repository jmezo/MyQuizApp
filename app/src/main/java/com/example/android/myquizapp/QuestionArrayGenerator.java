package com.example.android.myquizapp;

import android.content.Context;
import android.content.ContextWrapper;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class reads the questions from the strings.xml
 * and can create an ArrayList from those questions,
 * or from a given number of the questions randomly selected.
 */

public class QuestionArrayGenerator extends ContextWrapper{
    private ArrayList<QuizQuestion> arrayOfQuizQuestions;
    private ArrayList<QuizQuestion> arrayOfFlagQuestions;
    private static final int TOTAL_NUM_OF_QUESTIONS = 30;
    private static final int TOTAL_NUM_OF_FLAGS = 50;
    private static final String TYPE_QUESTION = "question";
    private static final String TYPE_FLAG = "flag";


    // creates an ArrayList of QuizQuestions from
    // the arrays of the strings.xml
    public QuestionArrayGenerator(Context context){
        super(context);
        arrayOfQuizQuestions = createArrayOfQuestions(TOTAL_NUM_OF_QUESTIONS, TYPE_QUESTION);
        arrayOfFlagQuestions = createArrayOfQuestions(TOTAL_NUM_OF_FLAGS, TYPE_FLAG);

    }

    public ArrayList<QuizQuestion> getArrayOfQuizQuestions(){
        return arrayOfQuizQuestions;
    }
    public ArrayList<QuizQuestion> getArrayOfFlagQuestions(){
        return arrayOfFlagQuestions;
    }

    // randomly selects a given number of QuizQuestions for advanced quiz
    // from the ArrayList arrayOfQuizQuestions and returns it
    // in a new ArrayList
    public ArrayList<QuizQuestion> getRandomQuestionList(int size){
        if(size > TOTAL_NUM_OF_QUESTIONS){
            size = TOTAL_NUM_OF_QUESTIONS;
        }

        ArrayList<QuizQuestion> copyList = arrayOfQuizQuestions;
        ArrayList<QuizQuestion> randomList = new ArrayList<>();
        Random random = new Random();
        for(int i = TOTAL_NUM_OF_QUESTIONS; i > TOTAL_NUM_OF_QUESTIONS - size; i--){
            int rand = random.nextInt(i);
            QuizQuestion question = copyList.get(rand);
            randomList.add(question);
            copyList.remove(rand);
        }
        return randomList;
    }

    // randomly selects a given number of QuizQuestions for basic quiz
    // from the ArrayList arrayOfQuizQuestions and returns it
    // in a new ArrayList
    public ArrayList<QuizQuestion> getRandomFlagList(int size){
        if(size > TOTAL_NUM_OF_FLAGS){
            size = TOTAL_NUM_OF_FLAGS;
        }

        ArrayList<QuizQuestion> copyList = arrayOfFlagQuestions;
        ArrayList<QuizQuestion> randomList = new ArrayList<>();
        Random random = new Random();
        for(int i = TOTAL_NUM_OF_FLAGS; i > TOTAL_NUM_OF_FLAGS - size; i--){
            int rand = random.nextInt(i);
            QuizQuestion question = copyList.get(rand);
            randomList.add(question);
            copyList.remove(rand);
        }
        return randomList;
    }

    // returns basic ArrayList for testing
    public ArrayList<QuizQuestion> getTestList(int size){
        String[] arrayForQuestion = {"Question","answer1(correct answer)",
                                    "answer2","answer3","answer4","1"};
        ArrayList<QuizQuestion> retList = new ArrayList<>();

        for(int i = 0; i< size; i++){
            QuizQuestion question = new QuizQuestion(arrayForQuestion);
            retList.add(question);
        }
        return retList;
    }

    //creates the arrayList of questions or flags by reading the data from the string resources
    private ArrayList<QuizQuestion> createArrayOfQuestions(int size, String type){
        ArrayList<QuizQuestion> arrayList= new ArrayList<>();
        for(int i = 1; i <= size; i++){
            int id = getResources().getIdentifier(type+i,"array",getPackageName());
            String[] array = getResources().getStringArray(id);
            QuizQuestion question = new QuizQuestion(array);
            arrayList.add(question);
        }
        return arrayList;
    }
}
