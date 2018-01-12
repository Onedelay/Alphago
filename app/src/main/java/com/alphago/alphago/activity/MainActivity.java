package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alphago.alphago.R;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.alphago.alphago.handler.BackPressCloseHandler;

public class MainActivity extends AppCompatActivity {

    private Button btnRecognition;
    private Button btnCardBook;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        btnRecognition = (Button) findViewById(R.id.btn_recognition);
        btnRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        btnCardBook = (Button) findViewById(R.id.btn_card_book);
        btnCardBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"카드 북 버튼 클릭",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CardBookActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
