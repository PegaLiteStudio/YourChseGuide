package com.yourapps.your_chse_guide.functions.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterfaces {

    @Headers("Content-Type: application/json")
    @GET("/getAppStatus/{version}")
    Call<ResponseBody> getAppStatus(@Path("version") int version);

}
