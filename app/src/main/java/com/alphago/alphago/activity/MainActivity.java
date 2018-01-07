package com.alphago.alphago.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alphago.alphago.R;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;

public class MainActivity extends AppCompatActivity {

    Button btnRecognition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecognition = (Button) findViewById(R.id.btn_recognition);
        btnRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });
    }
}
