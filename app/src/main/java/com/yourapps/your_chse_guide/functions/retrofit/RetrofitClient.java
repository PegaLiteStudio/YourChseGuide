package com.yourapps.your_chse_guide.functions.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static String BASE_URL = "https://yourchseguidemain.androcluster.com";

    public static String BASE_IMAGE_URL = BASE_URL + "/getImage/";
    public static String BASE_PDF_URL = BASE_URL + "/getPdf/";

    private static RetrofitClient retrofitClient;
    private final ApiInterfaces apiInterfaces;

    public RetrofitClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterfaces = retrofit.create(ApiInterfaces.class);
    }

    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public ApiInterfaces getApiInterfaces() {
        return apiInterfaces;
    }

}
