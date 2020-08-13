package com.os;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private TextView textViewScore ;
    private TextView textViewTotal ;
    private TextView textViewSentence ;
    private Button doneButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Online SortOut");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewScore = findViewById(R.id.textViewScore);
        textViewTotal = findViewById(R.id.textViewTotal);
        textViewSentence = findViewById(R.id.textViewSentense);
        doneButton = findViewById(R.id.done_btn);
        int total = getIntent().getIntExtra("total",0);
        int rights = getIntent().getIntExtra("rights",0);
        int wrongs = total - rights ;
        textViewScore.setText(String.valueOf("Right : "+rights));
        textViewScore.setTextColor(Color.GREEN);
        textViewTotal.setText(String.valueOf("Wrong : "+wrongs));
        textViewTotal.setTextColor(Color.RED);
        float percentage = (rights*100)/(float)total;
        if(percentage <=25){
            textViewSentence.setText("Work Hard");
        }
        else if(percentage>25 && percentage <=50){
            textViewSentence.setText("Study More");
        }
        else if(percentage>50 && percentage <=75){
            textViewSentence.setText("Good Work");
        }
        else if(percentage>75 && percentage<=90){
            textViewSentence.setText("Excellent");
        }
        else{
            textViewSentence.setText("Superior");
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
