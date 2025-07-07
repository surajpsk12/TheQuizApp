package com.surajvanshsv.thequizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.surajvanshsv.thequizapp.databinding.ActivityMainBinding;
import com.surajvanshsv.thequizapp.model.Question;
import com.surajvanshsv.thequizapp.model.QuestionList;
import com.surajvanshsv.thequizapp.viewmodel.QuizViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    QuizViewModel quizViewModel;
    List<Question> questionList;

    static int result = 0;
    static int totalQuestions = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Reset score
        result = 0;
        totalQuestions = 0;

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        DisplayFirstQuestion();

        binding.btnNext.setOnClickListener(view -> DisplayNextQuestions());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void DisplayFirstQuestion() {
        quizViewModel.getQuestionListLiveData().observe(this, new Observer<QuestionList>() {
            @Override
            public void onChanged(QuestionList questions) {
                if (questions != null && questions.size() > 0) {
                    questionList = questions;
                    totalQuestions = questionList.size();

                    showQuestion(0);

                } else {
                    Toast.makeText(MainActivity.this, "No questions available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showQuestion(int index) {
        binding.txtQuestion.setText("Question " + (index + 1) + ": " + questionList.get(index).getQuestion());
        binding.radio1.setText(questionList.get(index).getOption1());
        binding.radio2.setText(questionList.get(index).getOption2());
        binding.radio3.setText(questionList.get(index).getOption3());
        binding.radio4.setText(questionList.get(index).getOption4());
        binding.radioGroup.clearCheck();
    }

    private void DisplayNextQuestions() {
        int selectedOption = binding.radioGroup.getCheckedRadioButtonId();
        if (selectedOption != -1) {
            RadioButton selectedRadio = findViewById(selectedOption);

            if (questionList != null && i < questionList.size()) {
                if (selectedRadio.getText().toString().equals(questionList.get(i).getCorrectOption())) {
                    result++;
                    binding.txtResult.setText("Correct Answers: " + result);
                }
            }

            i++;

            if (i < questionList.size()) {
                showQuestion(i);
                if (i == questionList.size() - 1) {
                    binding.btnNext.setText("Finish");
                }
            } else {
                goToResults();
            }

        } else {
            Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToResults() {
        // handle final answer if Finish is pressed on the last question
        int selectedOption = binding.radioGroup.getCheckedRadioButtonId();
        if (selectedOption != -1 && i == questionList.size()) {
            RadioButton selectedRadio = findViewById(selectedOption);
            if (selectedRadio.getText().toString().equals(questionList.get(i - 1).getCorrectOption())) {
                // to prevent double-count, only add if not already counted
                if (!binding.btnNext.getText().toString().equals("Finish")) {
                    result++;
                }
            }
        }
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        startActivity(intent);
        finish();
    }
}
