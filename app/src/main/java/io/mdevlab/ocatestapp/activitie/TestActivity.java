package io.mdevlab.ocatestapp.activitie;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.mdevlab.ocatestapp.QuestionFragment;
import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.test.ChapterTest;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        TestQuestion question = ChapterTest.createRandomTestQuestion(0,TestActivity.this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.question_container, QuestionFragment.newInstance(question,false));

        ft.commit();

    }


}