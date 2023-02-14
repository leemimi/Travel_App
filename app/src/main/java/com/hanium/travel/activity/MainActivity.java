package com.hanium.travel.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.hanium.travel.BuildConfig;
import com.hanium.travel.R;
import com.hanium.travel.fragment.main.SettingFragment;
import com.hanium.travel.fragment.mydata.MyData2Fragment;
import com.hanium.travel.project.PreferenceManager;
import com.hanium.travel.project.SingleTon;

public class MainActivity extends AppCompatActivity {

    private ImageView recommend_image;
    private ImageView plan_image;
    private MaterialCardView recommend_card;
    private MaterialCardView plan_card;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private boolean isDrawerOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 제거
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼

        recommend_image = findViewById(R.id.recommend_image);
        plan_image = findViewById(R.id.plan_image);

        recommend_card = findViewById(R.id.recommend_card);
        plan_card = findViewById(R.id.plan_card);

        Glide.with(this).load(R.drawable.sample).placeholder(R.drawable.noimage).into(recommend_image);
        Glide.with(this).load(R.drawable.sample).placeholder(R.drawable.noimage).into(plan_image);

        recommend_card.setOnClickListener(onClickListener);
        plan_card.setOnClickListener(onClickListener);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setNavigationUserInfo();

        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            drawer.closeDrawers();

            int id = item.getItemId();

            if(id == R.id.nav_modify_mydata) // navigation drawer : 여행 취향 수정하기
                SingleTon.alertDialogTwoButton(MainActivity.this, "여행 취향 수정하기", "여행 취향을 다시 설정하시겠어요?",
                        "수정할게요", "괜찮아요")
                        .setOnPositiveClicked((view, dialog) -> {
                            Intent intent = new Intent(MainActivity.this, CollectMyDataActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .setOnNegativeClicked((view, dialog) -> dialog.dismiss())
                        .setDecorView(getWindow().getDecorView())
                        .build().show();
            else if(id == R.id.nav_QR) { // navigation drawer : QR 체크인
                Toast.makeText(MainActivity.this, "QR 체크인", Toast.LENGTH_SHORT).show();
            }
            else if(id == R.id.nav_destination_visited) { // navigation drawer : 방문했던 여행지
                Toast.makeText(MainActivity.this, "방문했던 여행지", Toast.LENGTH_SHORT).show();
            }
            else if(id == R.id.nav_bookmark) { // navigation drawer : 찜 목록

            }
            else if(id == R.id.nav_setting) { // navigation drawer : 환경설정
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.nav_error_report) { // navigation drawer : 앱 내 에러 전송

                String appVersion = String.valueOf(Build.VERSION.SDK_INT);
                String androidVersion = BuildConfig.VERSION_NAME;
                String androidModel = Build.MODEL;

                String emailText = "Android App Version : " + appVersion + "\nAndroid OS Version : " + androidVersion + "\nAndroid Device : " + androidModel + "\n에러 내용(여기 작성해 주세요!) : ";

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"spdlqjfire@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "나드리 Application Error Report");
                email.putExtra(Intent.EXTRA_TEXT, emailText);
                startActivity(email);
            }
            else if(id == R.id.nav_logout) { // navigation drawer : 로그아웃
                SingleTon.alertDialogTwoButton(MainActivity.this, "로그아웃", "로그아웃시 로그인 화면으로 돌아가요. 취향 정보는 저장되니 걱정 마세요!",
                        "로그아웃", "안할게요")
                        .setOnPositiveClicked((view, dialog) -> {
                            PreferenceManager.setBoolean(MainActivity.this, "isLogin", false);
                            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .setOnNegativeClicked((view, dialog) -> dialog.dismiss())
                        .setDecorView(getWindow().getDecorView())
                        .build().show();
            }

            return true;
        });

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpen = false;
            }
        };

        drawer.addDrawerListener(toggle); // toolbar를 사용하므로 작성해 줘야함
        toggle.syncState(); // 토글 버튼 띄우기
    }

    @Override
    public void onBackPressed() {

        if(isDrawerOpen)
            drawer.closeDrawer(Gravity.LEFT);
        else
            SingleTon.alertDialogTwoButton(MainActivity.this, "나가기", "정말 앱을 종료하시겠어요?",
                    "종료할게요", "좀 더 볼게요")
            .setOnPositiveClicked((view, dialog) -> finish())
            .setOnNegativeClicked((view, dialog) -> dialog.dismiss())
            .setDecorView(getWindow().getDecorView())
            .build().show();
    }

    View.OnClickListener onClickListener = view -> {

        Intent intent;

        switch (view.getId()) {
            case R.id.recommend_card:
                intent = new Intent(MainActivity.this, RecommendTravelActivity.class);
                startActivity(intent);
                break;
            case R.id.plan_card:
                intent = new Intent(MainActivity.this, SelectLocActivity.class);
                startActivity(intent);
                break;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        toggle.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationUserInfo() {

        View navView = navigationView.getHeaderView(0);

        TextView userNickName = navView.findViewById(R.id.nickname_nav_show);
        TextView userEmail = navView.findViewById(R.id.email_nav_show);

        userNickName.setText(PreferenceManager.getString(MainActivity.this, "mydata6-0"));
        userEmail.setText(PreferenceManager.getString(MainActivity.this, "mydata6-2"));
    }
}