package com.hanium.travel.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.hanium.travel.R;
import com.hanium.travel.project.PreferenceManager;
import com.kakao.sdk.user.UserApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AddUserActivity extends AppCompatActivity {

    private static final String TAG = "사용자";

    private TextView textView;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        /* 아래는 테스트용 코드 */
        //PreferenceManager.clear(AddUserActivity.this);

        if(PreferenceManager.getBoolean(AddUserActivity.this, "isLogin")) {
            // TODO : 설정 화면에서 자동로그인 여부 묻기
            Toast.makeText(AddUserActivity.this, "자동로그인 됩니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        textView = findViewById(R.id.logout);
        imageButton = findViewById(R.id.login);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.setBoolean(AddUserActivity.this, "isLogin", true);
                Intent intent = new Intent(AddUserActivity.this, CollectMyDataActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /* 카카오톡 로그인 연동 코드
        imageButton.setOnClickListener(view -> UserApiClient.getInstance().loginWithKakaoTalk(AddUserActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
                Snackbar.make(view, "로그인에 실패했습니다.", Snackbar.LENGTH_LONG).show();
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                Intent intent = new Intent(AddUserActivity.this, CollectMyDataActivity.class);
                startActivity(intent);
                finish();
            }
            return null;
        }));
         */

        textView.setOnClickListener(view -> UserApiClient.getInstance().logout(error -> {
            if (error != null) {
                Log.e(TAG, "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                Snackbar.make(view, "로그아웃에 실패했습니다.", Snackbar.LENGTH_LONG).show();
            }else{
                Log.e(TAG, "로그아웃 성공, SDK에서 토큰 삭제됨");
                Snackbar.make(view, "로그아웃에 성공했습니다.", Snackbar.LENGTH_LONG).show();
            }
            return null;
        }));
    }

    public String getKeyHash(){
        try{
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            if(packageInfo == null) return null;
            for(Signature signature: packageInfo.signatures){
                try{
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                }catch (NoSuchAlgorithmException e){
                    Log.w("getKeyHash", "Unable to get MessageDigest. signature="+signature, e);
                }
            }
        }catch(PackageManager.NameNotFoundException e){
            Log.w("getPackageInfo", "Unable to getPackageInfo");
        }
        return null;
    }
}
