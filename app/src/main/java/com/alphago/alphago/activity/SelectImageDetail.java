package com.alphago.alphago.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alphago.alphago.R;

public class SelectImageDetail extends AppCompatActivity {

    private Button btnDetailBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image_detail);

        btnDetailBack = (Button) findViewById(R.id.btn_detail_back);
        btnDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
