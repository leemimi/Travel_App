package com.hanium.travel.validclass;

import android.app.Activity;
import android.view.Gravity;

import com.google.android.material.textfield.TextInputEditText;
import com.hanium.travel.project.SingleTon;

public interface ValidationEdit {

    default void setDialog(Activity activity, String title, String message) {
        SingleTon.alertDialogNoButton(activity, title, message)
                .setDecorView(activity.getWindow().getDecorView())
                .build()
                .show();
    };

    boolean isEnterAllValue(TextInputEditText editText1, TextInputEditText editText2);
    boolean isEnterAllValue(TextInputEditText editText1, TextInputEditText editText2, TextInputEditText editText3);

    int isDataValid(TextInputEditText editText1, TextInputEditText editText2);
    int isDataValid(TextInputEditText editText1, TextInputEditText editText2, TextInputEditText editText3);
}
