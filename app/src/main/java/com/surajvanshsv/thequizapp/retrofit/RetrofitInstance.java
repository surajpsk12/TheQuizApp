package com.surajvanshsv.thequizapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    String baseUrl = "http://10.0.2.2:8080/quiz/";

    public Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
