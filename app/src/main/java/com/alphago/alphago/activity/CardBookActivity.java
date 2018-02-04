package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;

public class CardBookActivity extends NoStatusBarActivity {
    private Button btnHome;
    private Button btnLearning;
    private ImageButton btnTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book);

        btnHome = (Button) findViewById(R.id.btn_cd_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "홈 버튼 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardBookActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLearning = (Button) findViewById(R.id.btn_learning);
        btnLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "학습하기 버튼 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardBookActivity.this, WordLearningActivity.class);
                startActivity(intent);
            }
        });

        btnTmp = (ImageButton) findViewById(R.id.btn_cd_tmp);
        btnTmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "사진 항목 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardBookActivity.this, CardBookListActivity.class);
                startActivity(intent);
            }
        });
    }
}
