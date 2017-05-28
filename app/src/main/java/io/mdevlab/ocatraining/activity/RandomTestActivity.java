package io.mdevlab.ocatraining.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.modelManager.TestQuestionManager;

/**
 * Created by husaynhakeem on 5/28/17.
 */

public class RandomTestActivity extends SingleQuestionTestActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar(getString(R.string.title_activity_random_test));
    }


    @Override
    protected void setUpViews() {
        super.setUpViews();
        mNextAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowNext) {
                    showNextQuestion();
                    isShowNext = false;
                } else {
                    showQuestionAnswer();
                    isShowNext = true;
                }
            }
        });
    }


    @Override
    protected void setUpCurrentQuestion() {
        mQuestionNumber++;
        setQuestionUi();
        currentQuestion = TestQuestionManager.getRandomQuestion();
    }


    /**
     * This function get a new random question and display it
     */
    public void showNextQuestion() {

        //Increment the counter of Questions
        mQuestionNumber++;
        setQuestionUi();

        //TODO  make sure to get a random question from the DB each time
        currentQuestion = TestQuestionManager.getRandomQuestion();

        //TODO set the fragement Transition
        // TODO down => up new question
        // TODO left => right answer
        mCurrentFragment = QuestionFragment.newInstance(currentQuestion, false, true);
        mFragmentManager.beginTransaction()
                .replace(R.id.question_answer_container, mCurrentFragment)
                .commit();
    }


    /**
     * set the question UI
     */
    protected void setQuestionUi() {
        super.setQuestionUi();
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Question : " + mQuestionNumber);
    }


    @Override
    protected void setAnswerUi() {
        mNextAnswerButton.setText(R.string.next_question);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Answer For Question : " + mQuestionNumber);
    }


    public void showQuestionAnswer() {
        super.showQuestionAnswer();
        Boolean isCorrect = mCurrentFragment.verifyQuestionAnswer();
        updateScore(isCorrect);
    }


    /**
     * update the score if true it means the user's answer was correct other ways false
     * true increment Correct score
     * false increment inCorrect score
     *
     * @param isCorrect true/false
     */
    protected void updateScore(Boolean isCorrect) {
        if (isCorrect) {
            mCorrectAnswers++;
            mCorrectScore.setText(getString(R.string.correct, mCorrectAnswers));
        } else {
            mInCorrectAnswers++;
            mIncorrectScore.setText(getString(R.string.incorrect, mInCorrectAnswers));
        }
    }


    @Override
    public void onBackPressed() {
        buildStopDialog();
    }


    private void buildStopDialog() {

        //Get the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(RandomTestActivity.this);
        builder.setPositiveButton(R.string.quit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        //set message and title
        builder.setMessage(getString(R.string.confirm_exit_random_question_message))
                .setTitle(getString(R.string.confirm_exit_random_question_title));

        //Build the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
