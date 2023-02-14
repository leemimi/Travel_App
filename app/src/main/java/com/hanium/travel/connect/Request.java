package com.hanium.travel.connect;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Request {

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ConnectServer.IP_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
