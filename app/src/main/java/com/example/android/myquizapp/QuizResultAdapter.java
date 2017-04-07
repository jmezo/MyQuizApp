package com.example.android.myquizapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * instead of letting the user choose an answer,
 * shows right and wrong answers previously
 * selected in the QuizQuestionAdapter
 * by changing the colors of the radioButtons
 */

public class QuizResultAdapter extends ArrayAdapter {
    // View lookup cache
    private static class ViewHolder {
        TextView question;
        RadioGroup choiceGroup1;
        RadioGroup choiceGroup2;
        RadioButton choice1;
        RadioButton choice2;
        RadioButton choice3;
        RadioButton choice4;
    }

    public QuizResultAdapter(Context context, ArrayList<QuizQuestion> quizQuestions) {
        super(context, R.layout.quiz_question, quizQuestions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final QuizQuestion quizQuestion = (QuizQuestion) getItem(position);
        int chosen = quizQuestion.getChosen();

        final ViewHolder viewHolder; // view lookup cache stored in tag

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.quiz_question, parent, false);

            viewHolder.question = (TextView) convertView.findViewById(R.id.question_textView);
            viewHolder.choiceGroup1 = (RadioGroup) convertView.findViewById(R.id.choices_radioGroup1);
            viewHolder.choiceGroup2 = (RadioGroup) convertView.findViewById(R.id.choices_radioGroup2);
            viewHolder.choice1 = (RadioButton) convertView.findViewById(R.id.choice1_radioButton);
            viewHolder.choice2 = (RadioButton) convertView.findViewById(R.id.choice2_radioButton);
            viewHolder.choice3 = (RadioButton) convertView.findViewById(R.id.choice3_radioButton);
            viewHolder.choice4 = (RadioButton) convertView.findViewById(R.id.choice4_radioButton);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();

        }
        // Populate the data into the template view using the data object
        viewHolder.question.setText(quizQuestion.getQuestion());
        viewHolder.choice1.setText(quizQuestion.getChoice1());
        viewHolder.choice2.setText(quizQuestion.getChoice2());
        viewHolder.choice3.setText(quizQuestion.getChoice3());
        viewHolder.choice4.setText(quizQuestion.getChoice4());

        // create ArrayList of RadioButtons for setButtonColors method
        ArrayList<RadioButton> answers = new ArrayList<>();
        answers.add(viewHolder.choice1);
        answers.add(viewHolder.choice2);
        answers.add(viewHolder.choice3);
        answers.add(viewHolder.choice4);
        setButtonColors(answers, quizQuestion);

        switch (chosen) {
            case 1:
                viewHolder.choiceGroup1.check(R.id.choice1_radioButton);
                break;
            case 2:
                viewHolder.choiceGroup1.check(R.id.choice2_radioButton);
                break;
            case 3:
                viewHolder.choiceGroup2.check(R.id.choice3_radioButton);
                break;
            case 4:
                viewHolder.choiceGroup2.check(R.id.choice4_radioButton);
                break;
            default:
                viewHolder.choiceGroup1.clearCheck();
                viewHolder.choiceGroup2.clearCheck();
                break;
        }

        // Return the completed view to render on screen
        return convertView;
    }

    //sets correct answer's color green, selected wrong's to red
    private void setButtonColors(ArrayList<RadioButton> listOfButtons, QuizQuestion quizQuestion) {

        for (RadioButton button : listOfButtons) {
            button.setClickable(false);
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
        int answer = quizQuestion.getAnswer();
        int chosen = quizQuestion.getChosen();

        RadioButton correct = listOfButtons.get(answer - 1);
        correct.setTextColor(ContextCompat.getColor(getContext(), R.color.correctAnswer));

        if (chosen != answer && chosen != 0) {
            RadioButton selected = listOfButtons.get(chosen - 1);
            selected.setTextColor(ContextCompat.getColor(getContext(), R.color.wrongAnswer));
        }
    }

}
