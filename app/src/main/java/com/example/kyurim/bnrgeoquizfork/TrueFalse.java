//package com.example.kyurim.bignerdranchgeoquiz;
package com.example.kyurim.bnrgeoquizfork;

public class TrueFalse {

   private int mQuestion;          // this is an int and not a string as Resource ID is an int.
   private boolean mTrueQuestion;

   // setter
   public TrueFalse(int question, boolean trueQuestion) {
      mQuestion = question;
      mTrueQuestion = trueQuestion;
   }

   public int getQuestion() {
      return mQuestion;
   }

   public void setQuestion(int question) {
      mQuestion = question;
   }

   public boolean isTrueQuestion() {
      return mTrueQuestion;
   }

   public void setTrueQuestion (boolean trueQuestion) {
      mTrueQuestion = trueQuestion;
   }
}
