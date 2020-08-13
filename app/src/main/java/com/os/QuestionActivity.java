package com.os;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


    private TextView noIndicator , question ;
    private LinearLayout optionsContainer ;
    private Button shareBtn , nextBtn , prevBtn;
    private  int count ;
    private List<QuestionModel> questionModelList ;
    private int position ;
    private  int score ;
    private String category ;
    private String subCategory ;
    private int setNo ;
    private Dialog loadingDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noIndicator = findViewById(R.id.no_indicator);
        question=findViewById(R.id.question);
        optionsContainer = findViewById(R.id.options_container);
        nextBtn=findViewById(R.id.next_btn);
        prevBtn = findViewById(R.id.prev_btn);
        shareBtn=findViewById(R.id.share_btn);

        category = getIntent().getStringExtra("category");
        subCategory = getIntent().getStringExtra("subCategory");
        setNo = getIntent().getIntExtra("setNo",1);



        questionModelList = new ArrayList<>();

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingDialog.setCancelable(false);

        loadingDialog.show();
        myRef.child("SETS").child(category).child(subCategory).child("questions").orderByChild("setNo")
                .equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    questionModelList.add(dataSnapshot.getValue(QuestionModel.class));

                }
                if(questionModelList.size()>0){

                    for(int i=0;i<4;i++){
                        optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAnswer(((Button)v));
                            }
                        });
                    }

                    playAnim(question, 0 ,questionModelList.get(position).getQuestion());

                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextBtn.setEnabled(false);
                            nextBtn.setAlpha(0.7f);
                            enableOption(true);
                            position++;
                            if(position==questionModelList.size()){
                                Intent intent = new Intent(QuestionActivity.this , ScoreActivity.class);
                                intent.putExtra("rights" ,score);
                                intent.putExtra("total",questionModelList.size());
                                startActivity(intent);
                                finish();
                                return;
                            }
                            count = 0;
                            playAnim(question,0,questionModelList.get(position).getQuestion());
                        }
                    });

                    shareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String body ="OS QUIZ QUESTION \n Q.  "+ questionModelList.get(position).getQuestion()+"\n(1). "+
                                    questionModelList.get(position).getOptionA()+"\n(2). "+
                                    questionModelList.get(position).getOptionB()+"\n(3). "+
                                    questionModelList.get(position).getOptionC()+"\n(4). "
                                    +questionModelList.get(position).getOptionD()+"\nClick the link to get the App\nhttps://drive.google.com/drive/folders/1QMpOFqU99FwwV1B1cuslkZb4jR0jkS_7?usp=sharing";
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT,"OS QUIZ");
                            intent.putExtra(Intent.EXTRA_TEXT,body);
                            startActivity(Intent.createChooser(intent,"Share via"));
                        }
                    });
                }
                else{
                    finish();
                    Toast.makeText(QuestionActivity.this,"No Question Found",Toast.LENGTH_LONG).show();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();
                finish();
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    private void playAnim(final View view , final int value ,final String data){
        view.animate().alpha(value).scaleX(value)
                .scaleY(value).setDuration(300).setStartDelay(50)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if(value==0 && count < 4){
                            String option = null ;
                            if(count==0){
                                option = questionModelList.get(position).getOptionA();
                            }
                            else if(count==1){
                                option = questionModelList.get(position).getOptionB();

                            }
                            else if(count==2){
                                option = questionModelList.get(position).getOptionC();

                            }
                            else if(count==3){
                                option = questionModelList.get(position).getOptionD();

                            }
                            //position++;
                            playAnim(optionsContainer.getChildAt(count),0,option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if(value==0){
                            try{
                                noIndicator.setText(position+1+"/"+questionModelList.size());
                                ((TextView)view).setText(data);
                            }
                            catch (ClassCastException e){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            playAnim(view,1,data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }
    private void checkAnswer(Button selectedOption) {
        if (position < questionModelList.size()) {
            enableOption(false);
            nextBtn.setEnabled(true);
            nextBtn.setAlpha(1);
            if (selectedOption.getText().toString().equals(questionModelList.get(position).getCorrectAns())) {
                selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                score++;
            } else {
                selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                Button correctOption = optionsContainer.findViewWithTag(questionModelList.get(position).getCorrectAns());
                correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

            }
        }
    }
    private void enableOption(boolean enable){
        for(int i=0;i<4;i++){
            optionsContainer.getChildAt(i).setEnabled(enable);
            if(enable){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#58C1EB")));

            }
        }

    }
}

