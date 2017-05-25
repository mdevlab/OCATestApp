package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.mdevlab.ocatraining.R;

/**
 * Created by husaynhakeem on 5/24/17.
 */

public abstract class ActivityBase extends AppCompatActivity {


    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    protected void setUpToolbar(String toolbarTitle) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(toolbarTitle);
        mToolbar.setTitleTextColor(ContextCompat.getColor(ActivityBase.this, android.R.color.white));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    protected void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
