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

public class CardBookListActivity extends NoStatusBarActivity {
    private Button btnCdListBack;
    private ImageButton btnCdListTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_book_list);

        btnCdListBack = (Button) findViewById(R.id.btn_cd_list_back);
        btnCdListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "뒤로 버튼 클릭",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnCdListTmp = (ImageButton) findViewById(R.id.btn_cd_list_tmp);
        btnCdListTmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "항목 클릭",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardBookListActivity.this, SelectImageDetail.class);
                startActivity(intent);
            }
        });
    }
}
