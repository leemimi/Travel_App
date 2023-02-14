package com.hanium.travel.validclass;

import android.app.Activity;

import com.google.android.material.card.MaterialCardView;
import com.hanium.travel.project.SingleTon;

public interface ValidationCard {

    default void setDialog(Activity activity, String title, String message) {
        SingleTon.alertDialogNoButton(activity, title, message)
                .setDecorView(activity.getWindow().getDecorView())
                .build()
                .show();
    };
    boolean isSelectCard(MaterialCardView cardView1, MaterialCardView cardView2, MaterialCardView cardView3);
    boolean isSelectCard(MaterialCardView cardView1, MaterialCardView cardView2, MaterialCardView cardView3, MaterialCardView cardView4);
}
