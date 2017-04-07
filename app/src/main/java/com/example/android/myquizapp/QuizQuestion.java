package com.example.android.myquizapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Custom object that stores variables needed for a quiz question.
 * Implements Parcelable for storing it's data in Bundles
 *  which are used for an Activity's onSaveInstanceState
 */

public final class QuizQuestion implements Parcelable{
    // question of a QuizQuestion, or name of a flag drawable
    private String question;

    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private int answer;
    //which choice is chosen by the user
    private int chosen = 0;

    public QuizQuestion(String[] array){

        mixUpAnswers(array);
        question = array[0];
        choice1 = array[1];
        choice2 = array[2];
        choice3 = array[3];
        choice4 = array[4];
        answer = Integer.parseInt(array[5]);
    }

    public QuizQuestion(){
        question = "question";
        choice1 = "choice1";
        choice2 = "choice2";
        choice3 = "choice3";
        choice4 = "choice4";
        answer = 1;
    }


    public void setChosen(int i){
        chosen = i;
    }

    public String getQuestion(){
        return question;
    }
    public String getChoice1(){
        return choice1;
    }
    public String getChoice2(){
        return choice2;
    }
    public String getChoice3(){
        return choice3;
    }
    public String getChoice4(){
        return choice4;
    }
    public int getAnswer(){
        return answer;
    }
    public int getChosen(){
        return chosen;
    }


    private void mixUpAnswers(String [] array){
        // create a HashMap and set value of the correct answer to true
        int ans = Integer.parseInt(array[5]);
        HashMap<String, Boolean> mapOfChoices = new HashMap<>();
        for(int i = 1; i < 5; i++){
            boolean isAnswer = false;
            if(i == ans){
                isAnswer = true;
            }
            mapOfChoices.put(array[i], isAnswer);

        }

        // create an ArrayList of the choices,
        ArrayList<String> choices = new ArrayList<>();
        choices.add(array[1]);
        choices.add(array[2]);
        choices.add(array[3]);
        choices.add(array[4]);


        Random random = new Random();
        // mix up the Strings 1-4 in the array,
        // witch are Strings of the choices
        for(int i = 4; i>0; i--){
            int rand = random.nextInt(i) ;
            array[i] = choices.get(rand);

            // when the correct answer comes up, saves it's new location in the array
            if(mapOfChoices.get(choices.get(rand))){
                array[5] = String.valueOf(i);
            }

            choices.remove(rand);
        }
    }

    // these methods are for implementing Parcelable

    protected QuizQuestion(Parcel in) {
        question = in.readString();
        choice1 = in.readString();
        choice2 = in.readString();
        choice3 = in.readString();
        choice4 = in.readString();
        answer = in.readInt();
        chosen = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(choice1);
        dest.writeString(choice2);
        dest.writeString(choice3);
        dest.writeString(choice4);
        dest.writeInt(answer);
        dest.writeInt(chosen);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<QuizQuestion> CREATOR = new Parcelable.Creator<QuizQuestion>() {
        @Override
        public QuizQuestion createFromParcel(Parcel in) {
            return new QuizQuestion(in);
        }

        @Override
        public QuizQuestion[] newArray(int size) {
            return new QuizQuestion[size];
        }
    };
}
