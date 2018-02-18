package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;

public class WordLearningActivity extends NoStatusBarActivity {

    private ImageButton btn_learn_exit;
    private ImageButton btn_learn_pre;
    private ImageButton btn_learn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_learning);

        btn_learn_exit = (ImageButton)findViewById(R.id.btn_learn_exit);
        btn_learn_pre = (ImageButton)findViewById(R.id.btn_learn_pre);
        btn_learn_next = (ImageButton)findViewById(R.id.btn_learn_next);

        btn_learn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordLearningActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_learn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WordLearningActivity.this, "Previous", Toast.LENGTH_SHORT).show();
            }
        });

        btn_learn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WordLearningActivity.this, "Next", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
