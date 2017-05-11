package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.test.TestQuestionTest;


public class RandomTestActivity extends AppCompatActivity {


    private Button mNextAnswerButton;

    private boolean isShowNext = false;
    private TestQuestion currentQuestion;
    private QuestionFragment mCurrentFragment;
    private FragmentManager fm;
    private TextView mCorrectScoreTxt;
    private TextView mIncorrectScoreTxt;
    private int mCorrectAnswers;
    private int mInorrectAnswers;
    private int mQuestionNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();
        mCorrectScoreTxt = (TextView) findViewById(R.id.correct_score);
        mIncorrectScoreTxt = (TextView) findViewById(R.id.incorrect_score);

        mNextAnswerButton = (Button) findViewById(R.id.button_next_answer);
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

        initActivity();

    }


    /**
     * initial the random activity Question with a random question
     * and initialize the fragment
     */
    private void initActivity() {
        mQuestionNumber++;
        //TODO get the random Question from db
        currentQuestion = TestQuestionTest.createRandomTestQuestion(0, RandomTestActivity.this);

        FragmentTransaction ft = fm.beginTransaction();
        mCurrentFragment = QuestionFragment.newInstance(currentQuestion, false, true);
        ft.add(R.id.question_answer_container, mCurrentFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * This function get a new random question
     * and display it
     */
    public void showNextQuestion() {

        setQuestionUi();

        //TODO get the random Question from db
        currentQuestion = TestQuestionTest.createRandomTestQuestion(0, RandomTestActivity.this);

        //TODO set the fragement Transition
        // TODO down => up new question
        // TODO left => right answer
        FragmentTransaction ft = fm.beginTransaction();
        mCurrentFragment = QuestionFragment.newInstance(currentQuestion, false, true);
        ft.replace(R.id.question_answer_container, mCurrentFragment);
        ft.addToBackStack(null);
        ft.commit();


    }

    /**
     * set the question UI
     */
    private void setQuestionUi() {
        mNextAnswerButton.setText(R.string.question_answer);
    }

    /**
     * This function is for rendering the answer of the current question
     * show the explanation and show the effective answer
     */
    public void showQuestionAnswer() {
        setAnswerUi();
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
    private void updateScore(Boolean isCorrect) {
        if (isCorrect) {
            mCorrectAnswers++;
            mCorrectScoreTxt.setText(String.valueOf(mCorrectAnswers));
        } else {
            mInorrectAnswers++;
            mIncorrectScoreTxt.setText(String.valueOf(mInorrectAnswers));
        }
    }

    private void setAnswerUi() {
        mNextAnswerButton.setText(R.string.next_question);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
