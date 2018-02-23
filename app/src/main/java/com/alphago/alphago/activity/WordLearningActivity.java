package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;

import static com.alphago.alphago.R.id.btn_learn_exit;

public class WordLearningActivity extends NoStatusBarActivity {

    private ImageButton btnLearnExit;
    private ImageButton btnLearnPre;
    private ImageButton btnLearnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_learning);

        btnLearnExit = (ImageButton)findViewById(btn_learn_exit);
        btnLearnPre = (ImageButton)findViewById(R.id.btn_learn_pre);
        btnLearnNext = (ImageButton)findViewById(R.id.btn_learn_next);

        btnLearnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getBaseContext(), CardBookActivity.class);
                finish();
            }
        });

        btnLearnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WordLearningActivity.this, "Previous", Toast.LENGTH_SHORT).show();
            }
        });

        btnLearnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WordLearningActivity.this, "Next", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
