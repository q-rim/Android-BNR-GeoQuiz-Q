package com.example.kyurim.bnrgeoquizfork;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CheatActivity extends ActionBarActivity {

   public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";     // arbitrary string used for Key
   public static final String EXTRA_QUESTION = "com.bignerdranch.android.geoquiz.question";                 // arbitrary string used for Key
   public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";         // arbitrary string used for Key
   private boolean mAnswerIsTrue;
   private String mQuestionStr;

   private TextView mAnswerTextView, mQuestionTextView, mApiLevelTextView;
   private Button mShowAnswer;


   // putting a pause on the process for the Toast Message to complete before killing the Activity
   Thread thread = new Thread(){
      @Override
      public void run() {
         try {
            Thread.sleep(2000); // As I am using LENGTH_SHORT in Toast
            //Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
            CheatActivity.this.finish();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   };


   // sending data back to QuizActivity
   private void setAnswerShownResult(boolean isAnswerShown) {
      Intent data = new Intent();
      data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
      setResult(RESULT_OK, data);
   }


   // make a java process sleep for mili-seconds
   public static void sleep(int time){
      try {
         Thread.sleep(time);
      } catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
   }


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cheat);

      // Question from QuizActivity.java
      mQuestionStr = getIntent().getStringExtra(EXTRA_QUESTION);                    // String value passed from QuizActivity.  NOTE:  EXTRA is a key:value pair
      mQuestionTextView = (TextView)findViewById(R.id.questionTextView);            // connect to Layout
      mQuestionTextView.setText("Question " + mQuestionStr);                        // set TextView with Question

      // TextView of API level (BNR.p.123)
      mApiLevelTextView = (TextView)findViewById(R.id.api_levelTextView);
      mApiLevelTextView.setText("Android API level: "+ Build.VERSION.SDK_INT);
      mApiLevelTextView.setTextSize(22);


      // T/F Answer from QuizActivity.java
      mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
      mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

      // Answer will not be shown until the user presses the button
      setAnswerShownResult(false);           // Sending data back to QuizActivity

      mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
      mShowAnswer.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            // textView output
            if (mAnswerIsTrue) {
               mAnswerTextView.setText(R.string.true_button);     // setText("TRUE")
            } else {
               mAnswerTextView.setText(R.string.false_button);    // setText("FALSE")
            }


            // toast Message Output
            int resStrId;
            Resources res = getResources();
            String resStr;
            if (mAnswerIsTrue) {
               resStrId = R.string.true_button;
               System.out.println("------(integer of resource ID):   "+ Integer.toString(R.string.true_button));
               System.out.println("------(String value of resource): "+ res.getString(R.string.true_button));
            } else {
               resStrId = R.string.false_button;
               System.out.println("------(integer of resource ID):   "+ Integer.toString(R.string.false_button));
               System.out.println("------(String value or resource): "+ res.getString(R.string.false_button));
            }
            Toast.makeText(CheatActivity.this, "test"+resStrId, Toast.LENGTH_SHORT).show();

            setAnswerShownResult(true);      // Send data back to QuizActivity

            thread.start();      // delay destroying the current Activity until toast output is complete.

         }
      });
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_cheat, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
         return true;
      }

      return super.onOptionsItemSelected(item);
   }
}
