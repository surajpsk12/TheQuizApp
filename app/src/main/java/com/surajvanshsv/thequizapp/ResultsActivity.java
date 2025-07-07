package com.surajvanshsv.thequizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.surajvanshsv.thequizapp.databinding.ActivityResultsBinding;

public class ResultsActivity extends AppCompatActivity {
    ActivityResultsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_results);

        // Show score
        binding.txtAnswer.setText(
                "Your score is: " + MainActivity.result
                        + " / " + MainActivity.totalQuestions
        );

        // Reset static counters so quiz restarts fresh
        MainActivity.result = 0;
        MainActivity.totalQuestions = 0;

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // close results so user cannot return with back button
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
