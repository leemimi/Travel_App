package com.hanium.travel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hanium.travel.R;
import com.hanium.travel.connect.ConnectServer;
import com.hanium.travel.connect.Request;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressingMyDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processingmydata);

        setCircularProgressBar();
        Intent intent = getIntent();

        boolean[] mydata1 = intent.getBooleanArrayExtra("mydata1");
        boolean[] mydata2 = intent.getBooleanArrayExtra("mydata2");
        boolean[] mydata3 = intent.getBooleanArrayExtra("mydata3");
        boolean[] mydata4 = intent.getBooleanArrayExtra("mydata4");
        String[] mydata5 = intent.getStringArrayExtra("mydata5");
        String[] mydata6 = intent.getStringArrayExtra("mydata6");

        /*
        // TEST CODE
        for(int i = 0; i < mydata1.length; i++)
            System.out.println("mydata : " + mydata1[i]);
         */


        ConnectServer connectServer = Request.getRetrofit().create(ConnectServer.class);

        Call<Void> call = connectServer.sendTestData("10000", "15000");
        System.out.println("통신 전입니다.");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("통신 시도 ...");
                System.out.println("결과값 : " + response.body());
                //System.out.println("결과값 : " + result);

                Intent intent1 = new Intent(ProgressingMyDataActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("통신 실패");
                System.out.println(t.getMessage());
                System.out.println(t.fillInStackTrace());
            }
        });

        /*
        Call<String> call = connectServer.sendMyData(mydata1, mydata2, mydata3, mydata4, mydata5, mydata6);
        System.out.println("통신 전입니다.");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("통신 시도 ...");
                String result = response.body();
                System.out.println("결과값 : " + result);

                Intent intent1 = new Intent(ProgressingMyDataActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("통신 실패");
                System.out.println(t.getMessage());

                /* 통신 테스트 및 성공시 아래 코드 삭제
                Intent intent1 = new Intent(ProgressingMyDataActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();

            }
        });
        */
    }

    private void setCircularProgressBar() {
        CircularProgressBar circularProgressBar = findViewById(R.id.circularProgressBar);
        circularProgressBar.setProgressWithAnimation(65f, 1000L); // =1s
    }
}
