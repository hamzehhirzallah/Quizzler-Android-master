package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressbar;

    int mIndex = 0;
    int mScore = 0;

    // TODO: Uncomment to create question bank
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, true),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, true),
            new Question(R.string.question_6, false),
            new Question(R.string.question_7, true),
            new Question(R.string.question_8, false),
            new Question(R.string.question_9, true),
            new Question(R.string.question_10, true),
            new Question(R.string.question_11, false),
            new Question(R.string.question_12, false),
            new Question(R.string.question_13,true)
    };

    // TODO: Declare constants here
    int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the UI elements
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);

        // init the first Question
        mQuestionTextView.setText(mQuestionBank[mIndex].getQuestionId());

        // resume the saveInstance and get the values
        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");

            updateUI();
        }


        // on Click Listeners
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkAnswer(true);
            updateQuestion();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkAnswer(false);
            updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        mIndex = (mIndex+1) % mQuestionBank.length;

        if(mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setCancelable(false);
            alert.setTitle("Game Over");
            alert.setMessage("You scored " + mScore + " Scores !");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }

        updateUI();
    }

    private void updateUI(){
        mQuestionTextView.setText(mQuestionBank[mIndex].getQuestionId());
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
        mProgressbar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
    }

    private void checkAnswer(boolean userAnswer ){
        if( mQuestionBank[mIndex].isAnswer() == userAnswer){
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            if(mScore < mQuestionBank.length){
                mScore++;
            }
        }else{
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey",mScore);
        outState.putInt("IndexKey",mIndex);

    }

}
