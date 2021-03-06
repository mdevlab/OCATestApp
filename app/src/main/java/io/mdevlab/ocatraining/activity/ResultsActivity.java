package io.mdevlab.ocatraining.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.mdevlab.ocatraining.BuildConfig;
import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.ResultsAdapter;
import io.mdevlab.ocatraining.analytics.AnalyticsManager;
import io.mdevlab.ocatraining.dialog.DialogNewTest;
import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.util.UtilActions;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

import static io.mdevlab.ocatraining.model.Answer.ANSWER_NUMBER;
import static io.mdevlab.ocatraining.model.Answer.CURRENT_ANSWER;
import static io.mdevlab.ocatraining.model.Test.CHAPTER_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.FINAL_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.TEST_CHAPTER;
import static io.mdevlab.ocatraining.model.Test.TEST_MODE;

public class ResultsActivity extends ActivityBase {

    private static final String DIALOG_NEW_TEST_TAG = "new test dialog";
    private ResultsAdapter mAdapter;
    private Test mTest;
    private Boolean isPaid;
    private Button upgradeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setUpToolbar(getString(R.string.title_result));
        upgradeButton = (Button) findViewById(R.id.upgrade_btn);
        setUpDFP();
        setUpTest();
        setUpViews();
        isPaid = BuildConfig.IS_FREE_FLAVOR ? false : true;
        setUpPaidUi();
        // For testing
//        io.mdevlab.ocatraining.test.Test.populateDataBase(ResultsActivity.this);
//        mTest = TestTest.createTest();
    }

    public void setUpPaidUi() {
        if (isPaid) {
            upgradeButton.setVisibility(View.GONE);
        }
    }

    private void setUpViews() {
        if (mTest != null) {
            setUpTestScore();
            setUpTestDuration();
            setUpPieChart();
            setUpQuestionsAdapter();
            setUpQuestionsList();
        }
    }


    private void setUpTestScore() {
        TextView mScore = (TextView) findViewById(R.id.score);
        mScore.setText(getString(R.string.test_score, mTest.getScore()));
    }


    private void setUpTestDuration() {
        TextView mDuration = (TextView) findViewById(R.id.duration);
        mDuration.setText(getString(R.string.test_duration, mTest.getDurationToDisplay(ResultsActivity.this)));
    }


    private void setUpPieChart() {
        PieChartView chart = (PieChartView) findViewById(R.id.results_pie_chart);
        PieChartData data = fillInChartData();
        chart.setPieChartData(data);
    }


    private PieChartData fillInChartData() {
        PieChartData data = new PieChartData();

        List<SliceValue> results = new ArrayList<>();

        SliceValue correctAnswers = new SliceValue(mTest.getNumberOfCorrectAnswers(), ChartUtils.pickColor());
        correctAnswers.setLabel(getString(R.string.correct_answers_label));
        results.add(correctAnswers);

        SliceValue falseAnswers = new SliceValue(mTest.getNumberOfFalseAnswers(), ChartUtils.pickColor());
        falseAnswers.setLabel(getString(R.string.false_answers_label));
        results.add(falseAnswers);

        data.setValues(results);
        data.setHasLabels(true);
        data.setHasLabelsOutside(true);
        data.setHasCenterCircle(true);

        return data;
    }


    private void setUpQuestionsAdapter() {
        mAdapter = new ResultsAdapter(ResultsActivity.this,
                R.layout.item_result,
                mTest.getQuestionsAsArrayList());
    }


    private void setUpQuestionsList() {
        GridView mQuestions = (GridView) findViewById(R.id.questions_grid);
        mQuestions.setAdapter(mAdapter);
        mQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent response = new Intent(ResultsActivity.this, ResponseActivity.class);
                response.putExtra(CURRENT_ANSWER, mAdapter.getItem(position));
                response.putExtra(ANSWER_NUMBER, String.valueOf(position + 1));
                startActivity(response);
            }
        });
    }


    private void setUpTest() {
        Intent intent = getIntent();
        if (intent.hasExtra(TestActivity.CURRENT_TEST))
            mTest = intent.getParcelableExtra(TestActivity.CURRENT_TEST);
    }


    public void takeAnotherTest(View view) {
        int testMode = getCurrentTestMode();
        DialogFragment newTestDialog;
        if (testMode == CHAPTER_TEST_MODE) {
            newTestDialog = DialogNewTest.newInstance(testMode, getIntent().getExtras().getInt(TEST_CHAPTER));
        } else {
            newTestDialog = DialogNewTest.newInstance(testMode);
        }
        if (isPaid) {
            retakeTest();
        } else {
            newTestDialog.show(getSupportFragmentManager(), DIALOG_NEW_TEST_TAG);
        }

    }

    public void retakeTest() {
        Intent test = new Intent(this, TestActivity.class);
        test.putExtra(TEST_MODE, getCurrentTestMode());
        if (getCurrentTestMode() == Test.CHAPTER_TEST_MODE && getIntent().getExtras().containsKey(TEST_CHAPTER))
            test.putExtra(TEST_CHAPTER, getIntent().getExtras().getInt(TEST_CHAPTER));
        startActivity(test);
    }

    private int getCurrentTestMode() {
        if (getIntent() != null &&
                getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(TEST_MODE))
            return getIntent().getExtras().getInt(TEST_MODE);

        // Default value
        return FINAL_TEST_MODE;
    }


    public void upgrade(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.property_name_source), getString(R.string.attribute_test_result));
        AnalyticsManager.getInstance().logEvent(getResources().getString(R.string.event_view_upgrade), bundle);
        UtilActions.displayUpgradeDialog(ResultsActivity.this);
    }


    public void backToMainScreen(View view) {
        buildLeaveDialog();
    }


    @Override
    public void onBackPressed() {
        buildLeaveDialog();
    }


    private void buildLeaveDialog() {

        //get the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultsActivity.this);
        builder.setPositiveButton(R.string.quit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent mainScreen = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(mainScreen);
                finish();
            }
        });


        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        //set message and title
        builder.setMessage(getString(R.string.result_exit_message))
                .setTitle(getString(R.string.result_exit_title));

        //Build the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
