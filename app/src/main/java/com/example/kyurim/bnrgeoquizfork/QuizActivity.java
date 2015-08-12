package com.example.kyurim.bnrgeoquizfork;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends ActionBarActivity {

   private Button mTrueButton, mFalseButton, mCheatButton;
   private ImageButton mPreviousButton, mNextButton;
   private TextView mQuestionTextView;
   private static final String TAG = "--- Q ---: QuizActivity";
   private static final String KEY_INDEX = "index";
   private String questionStr;
   private boolean mIsCheater;

   private TrueFalse[] mQuestionBank = new TrueFalse[]{
      new TrueFalse(R.string.question_oceans, true),
      new TrueFalse(R.string.question_mideast, false),
      new TrueFalse(R.string.question_africa, false),
      new TrueFalse(R.string.question_americas, true),
      new TrueFalse(R.string.question_asia, true)
   };

   private int mCurrentIndex = 0;
   private void updateQuestion() {
      Log.d(TAG, "Updating question text for question #" + mCurrentIndex, new Exception());   // used for Listing 4.3: Debugging
      int question = mQuestionBank[mCurrentIndex].getQuestion();
      questionStr = getResources().getString(question);
      mQuestionTextView.setText(question);
   }

   // Getting data back from CheatActivity
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data){
      if (data == null) {
         return;
      }
      mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
   }

   private void checkAnswer(boolean userPressedTrue) {
      boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

      int messageResId = 0;

      if (mIsCheater) {                            // added for checking if the user cheated.  Getting data back from CheatActivity.
         messageResId = R.string.judgment_toast;   // show cheating toast message.
      } else {
         if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.toast_true;
         } else {
            messageResId = R.string.toast_false;
         }
      }
      Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
   }

   @TargetApi(11)       // suppresses Lint compatability errors (BNR.p.119)
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      Log.d(TAG, "---Q: onCreate(Bundle) called");
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_quiz);

      // Ch.6:  adding code from later API
      // check to see if the SDK can handle it.
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//         ActionBar actionBar = getActionBar();
//         actionBar.setSubtitle("Bodies of Water");
//      }

      // Listing 3.8 Different ways of logging in Android
      Log.d(TAG, "Current question index: " + mCurrentIndex);

      TrueFalse question;
      try {
         question = mQuestionBank[mCurrentIndex];
      } catch (ArrayIndexOutOfBoundsException ex) {
         // Log a message at "error" log level, along with an exception stack trace
         Log.e(TAG, "Index was out of bounds", ex);
      }

      mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
      mQuestionTextView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // action
            System.out.println("------- Textview Pressed - going to NEXT question -------");
            // set next string
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionTextView.length();
            updateQuestion();
         }
      });

      mTrueButton = (Button) findViewById(R.id.true_button);
      mTrueButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
            // action
            System.out.println("------- TRUE -------");
            checkAnswer(true);          // this function returns a toast message

            // go to the next question if the answer is correct
            System.out.println("answer =");
            System.out.println(answerIsTrue);
            if (answerIsTrue) {
               // check to see if it's the last question
               if (mCurrentIndex == 4) {
                  System.out.println("mCurrentIndex==4");
                  // Toast
                  Toast.makeText(QuizActivity.this,
                     R.string.toast_last_question,           // reference from strings.xml
                     Toast.LENGTH_SHORT).show();             // time duration
               } else {
                  // set next string
                  mCurrentIndex = (mCurrentIndex + 1) % mQuestionTextView.length();
                  mIsCheater = false;                       // resetting the Cheater value
                  updateQuestion();
               }
            }
         }
      });

      mFalseButton = (Button) findViewById(R.id.false_button);
      mFalseButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
            // action
            System.out.println("------- FALSE -------");
            checkAnswer(false);         // this function returns a toast message

            // go to the next question if the answer is correct
            System.out.println("answer =");
            System.out.println(answerIsTrue);


            if (!answerIsTrue) {
               // check to see if it's the last question
               if (mCurrentIndex == 4) {
                  System.out.println("mCurrentIndex==4");
                  // Toast
                  Toast.makeText(QuizActivity.this,
                     R.string.toast_last_question,           // reference from strings.xml
                     Toast.LENGTH_SHORT).show();             // time duration
               } else {
                  // set next string
                  mCurrentIndex = (mCurrentIndex + 1) % mQuestionTextView.length();
                  mIsCheater = false;                       // resetting the Cheater value
                  updateQuestion();
               }
            }
         }
      });

      mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
      mPreviousButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // action
            System.out.println("------- PREVIOUS -------");
            System.out.println("-------mCurrIndex=");
            System.out.println(mCurrentIndex);

            if (mCurrentIndex == 0) {
               System.out.println("mCurrentIndex==0");
               // Toast
               Toast.makeText(QuizActivity.this,
                  R.string.toast_first_question,          // reference from strings.xml
                  Toast.LENGTH_SHORT).show();             // time duration
            } else {
               // go to the previous string
               mCurrentIndex = (mCurrentIndex - 1) % mQuestionTextView.length();
               updateQuestion();
            }
         }
      });


      mNextButton = (ImageButton) findViewById(R.id.next_button);
      mNextButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // action
            System.out.println("------- NEXT -------");
            System.out.println("-------mCurrIndex=");
            System.out.println(mCurrentIndex);
            // check to see if it's the last question

            if (mCurrentIndex == 4) {
               System.out.println("mCurrentIndex==4");
               // Toast
               Toast.makeText(QuizActivity.this,
                  R.string.toast_last_question,           // reference from strings.xml
                  Toast.LENGTH_SHORT).show();             // time duration
            } else {
               // set next string
               mCurrentIndex = (mCurrentIndex + 1) % mQuestionTextView.length();        // Listing 4.3: Debugging
               mIsCheater = false;                       // resetting the Cheater value
               updateQuestion();
            }
         }
      });

      // added for maintaining the state from portrait-to-landscape mode
      if (savedInstanceState != null) {
         mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
      }


      // button to go to CheatActivity
      mCheatButton = (Button)findViewById(R.id.cheat_button);
      mCheatButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // make it do something when pressed.
            Intent i = new Intent(QuizActivity.this, CheatActivity.class);       // make it start the CheatActivity.class

            // passing data (T/F answer) to CheatActivity - EXTRA
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();   // get the T/F answer of this question.
            i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);           // pass the answer to the CheatActivity.  You can also have multiple extras if needed.

            // passing data (Question Index) to CheatActivity - EXTRA
            System.out.println("------QUESTION: " + questionStr);
            System.out.println("-----------question index is:  " + mCurrentIndex);
            i.putExtra(CheatActivity.EXTRA_QUESTION, questionStr);

//            startActivity(i);              // use this if you don't need results back.
            startActivityForResult(i, 0);    // use this if you want result back from Child Activity.

         }
      });

      updateQuestion();
   }

   @Override
   public void onSaveInstanceState(Bundle savedInstanceState) {
      Log.i(TAG, "onSaveInstanceState() called");
      super.onSaveInstanceState(savedInstanceState);
      savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
   }

   @Override
   public void onStart() {
      Log.d(TAG, "onStart() called");
      super.onStart();
   }

   @Override
   public void onPause() {
      Log.d(TAG, "onPause() called");
      super.onPause();
   }

   @Override
   public void onResume() {
      Log.d(TAG, "onResume() called");
      super.onResume();
   }

   @Override
   public void onStop() {
      Log.d(TAG, "onStop() called");
      super.onStop();
   }

   @Override
   public void onDestroy() {
      Log.d(TAG, "onDestroy() called");
      super.onDestroy();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_quiz, menu);
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