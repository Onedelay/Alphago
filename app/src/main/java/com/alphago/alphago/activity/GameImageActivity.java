package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.R;

public class GameImageActivity extends AppCompatActivity {

    private ImageView img_imggame_check1;
    private ImageView img_imggame_check2;
    private ImageView img_imggame_check3;
    private ImageView img_imggame_check4;
    private Button btn_imggame_exit;
    private Button btn_imggame_next;
    private ImageButton btn_imggame_ex1;
    private ImageButton btn_imggame_ex2;
    private ImageButton btn_imggame_ex3;
    private ImageButton btn_imggame_ex4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_image);

        img_imggame_check1 = (ImageView)findViewById(R.id.img_imggame_check1);
        img_imggame_check2 = (ImageView)findViewById(R.id.img_imggame_check1);
        img_imggame_check3 = (ImageView)findViewById(R.id.img_imggame_check1);
        img_imggame_check4 = (ImageView)findViewById(R.id.img_imggame_check1);
        btn_imggame_exit = (Button)findViewById(R.id.btn_imggame_exit);
        btn_imggame_next = (Button)findViewById(R.id.btn_imggame_next);
        btn_imggame_ex1 = (ImageButton)findViewById(R.id.btn_imggame_ex1);
        btn_imggame_ex2 = (ImageButton)findViewById(R.id.btn_imggame_ex2);
        btn_imggame_ex3 = (ImageButton)findViewById(R.id.btn_imggame_ex3);
        btn_imggame_ex4 = (ImageButton)findViewById(R.id.btn_imggame_ex4);

        btn_imggame_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameImageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_imggame_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Right or Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_imggame_ex1 :
                        img_imggame_check1.setVisibility(View.VISIBLE);
                        img_imggame_check1.bringToFront();
                        img_imggame_check2.setVisibility(View.INVISIBLE);
                        img_imggame_check3.setVisibility(View.INVISIBLE);
                        img_imggame_check4.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.btn_imggame_ex2 :
                        img_imggame_check2.setVisibility(View.VISIBLE);
                        img_imggame_check2.bringToFront();
                        img_imggame_check1.setVisibility(View.INVISIBLE);
                        img_imggame_check3.setVisibility(View.INVISIBLE);
                        img_imggame_check4.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.btn_imggame_ex3 :
                        img_imggame_check3.setVisibility(View.VISIBLE);
                        img_imggame_check3.bringToFront();
                        img_imggame_check1.setVisibility(View.INVISIBLE);
                        img_imggame_check2.setVisibility(View.INVISIBLE);
                        img_imggame_check4.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.btn_imggame_ex4 :
                        img_imggame_check4.setVisibility(View.VISIBLE);
                        img_imggame_check4.bringToFront();
                        img_imggame_check1.setVisibility(View.INVISIBLE);
                        img_imggame_check2.setVisibility(View.INVISIBLE);
                        img_imggame_check3.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
        btn_imggame_ex1.setOnClickListener(onClickListener);
        btn_imggame_ex2.setOnClickListener(onClickListener);
        btn_imggame_ex3.setOnClickListener(onClickListener);
        btn_imggame_ex4.setOnClickListener(onClickListener);
    }
}