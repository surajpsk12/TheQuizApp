package com.surajvanshsv.thequizapp.retrofit;

import com.surajvanshsv.thequizapp.model.QuestionList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionAPI {


    @GET("myquizapi.php")
    Call<QuestionList> getQuestion();
}
