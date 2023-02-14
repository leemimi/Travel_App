package com.hanium.travel.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hanium.travel.R;
import com.hanium.travel.fragment.mydata.MyData0Fragment;
import com.hanium.travel.project.SingleTon;

public class CollectMyDataActivity extends AppCompatActivity {

    public Button next_btn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectmydata);

        next_btn = findViewById(R.id.next_btn);

        ConstraintLayout layout = findViewById(R.id.parent_view_mydata);
        layout.setOnTouchListener((view, motionEvent) -> {
            SingleTon.hideKeyboard(CollectMyDataActivity.this);
            return false;
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MyData0Fragment myData0Fragment = new MyData0Fragment();
        fragmentTransaction.add(R.id.mydata_frame, myData0Fragment).commit();
    }

    @Override
    public void onBackPressed() {
        SingleTon.alertDialogTwoButton(CollectMyDataActivity.this, "얼마 안남았어요.", "금방 끝나요! 조금만 기다려 주시면 안될까요?",
                "나갈래요", "마저 할게요")
                .setOnPositiveClicked((view, dialog) -> {
                    dialog.dismiss();
                    finish();
                })
                .setOnNegativeClicked((view, dialog) -> dialog.dismiss())
                .setDecorView(getWindow().getDecorView())
                .build().show();
    }

    public void setVisibility() {
        next_btn.setVisibility(View.VISIBLE);
    }
}