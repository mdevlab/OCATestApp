package io.mdevlab.ocatraining.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.activity.TestActivity;
import io.mdevlab.ocatraining.util.Constants;
import io.mdevlab.ocatraining.util.UtilActions;

/**
 * Created by husaynhakeem on 5/23/17.
 */

public class DialogNewTest extends DialogFragment {


    private static int mTestMode;


    public static DialogNewTest newInstance(int testMode) {
        mTestMode = testMode;
        return new DialogNewTest();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_new_test, null));

        builder.setPositiveButton(R.string.take_another_test_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UtilActions.upgrade(getActivity());
            }
        });

        builder.setNeutralButton(R.string.take_another_test_neutral_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                retakeTest();
            }
        });

        builder.setNegativeButton(R.string.take_another_test_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogNewTest.this.getDialog().cancel();
            }
        });

        return builder.create();
    }


    private void retakeTest() {
        Intent test = new Intent(getActivity(), TestActivity.class);
        test.putExtra(Constants.TEST_MODE, mTestMode);
        startActivity(test);
    }
}
