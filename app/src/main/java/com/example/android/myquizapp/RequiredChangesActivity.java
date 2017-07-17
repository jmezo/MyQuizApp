package com.example.android.myquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.data;

public class RequiredChangesActivity extends AppCompatActivity {
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_changes);

        checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
        editText = (EditText) findViewById(R.id.edit_text);
    }
    public void submit(View view){
        int points = 0;

        if(checkBox1.isChecked() && checkBox2.isChecked() && !checkBox3.isChecked()){
            points += 1;
        }

        if(editText.getText().toString().equals("edittext")){
            points +=1;
        }

        Toast.makeText(this, "your result: "+points,
                Toast.LENGTH_LONG).show();
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        editText.setText("");
    }
}
