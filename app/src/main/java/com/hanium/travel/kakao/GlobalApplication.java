package com.hanium.travel.kakao;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {

    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        KakaoSdk.init(instance, "a90194c426e58d7d1d0b86c1a662743a");
    }
}
