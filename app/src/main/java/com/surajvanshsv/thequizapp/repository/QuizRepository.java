package com.surajvanshsv.thequizapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.surajvanshsv.thequizapp.model.QuestionList;
import com.surajvanshsv.thequizapp.retrofit.QuestionAPI;
import com.surajvanshsv.thequizapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRepository {

    private QuestionAPI questionAPI;

    public QuizRepository() {
        this.questionAPI = new RetrofitInstance()
                .getRetrofitInstance()
                .create(QuestionAPI.class);
    }

    public LiveData<QuestionList> getQuestionsFromAPI(){
        MutableLiveData<QuestionList> data = new MutableLiveData<>();

        Call<QuestionList> response = questionAPI.getQuestion();

        response.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                QuestionList list = response.body();
                data.setValue(list);
            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {

            }
        });

        return data;
    }



}
