package com.hanium.travel.connect;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ConnectServer {

    public static String IP_ADDRESS = "http://115.85.180.60/server/";

    @FormUrlEncoded
    @POST("test.php")
    Call<String> sendMyData(
            @Field("movement") boolean[] movement,
            @Field("dest") boolean[] dest,
            @Field("goal") boolean[] goal,
            @Field("with") boolean[] with,
            @Field("money") String[] money,
            @Field("user_info") String[] info
    );

    @FormUrlEncoded
    @POST("P_cost.php")
    Call<Void> sendTestData(
            @Field("minimum") String min,
            @Field("maximum") String max
    );
}