package com.alphago.alphago.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.R;

public class GameImageActivity extends AppCompatActivity {

    private ImageView img_imggame_check;
    private Button btn_imggame_exit;
    private Button btn_imggame_next;
    private Button btn_imggame_ex1;
    private Button btn_imggame_ex2;
    private Button btn_imggame_ex3;
    private Button btn_imggame_ex4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_image);

        img_imggame_check = (ImageView)findViewById(R.id.img_imggame_check);
        btn_imggame_exit = (Button)findViewById(R.id.btn_imggame_exit);
        btn_imggame_next = (Button)findViewById(R.id.btn_imggame_next);
        btn_imggame_ex1 = (Button)findViewById(R.id.btn_imggame_ex1);
        btn_imggame_ex2 = (Button)findViewById(R.id.btn_imggame_ex2);
        btn_imggame_ex3 = (Button)findViewById(R.id.btn_imggame_ex3);
        btn_imggame_ex4 = (Button)findViewById(R.id.btn_imggame_ex4);

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
                /* Drawable drawable1 = btn_imggame_ex1.getBackground();
                Drawable drawable2 = btn_imggame_ex2.getBackground();
                Drawable drawable3 = btn_imggame_ex3.getBackground();
                Drawable drawable4 = btn_imggame_ex4.getBackground(); */

                switch (v.getId()) {
                    case R.id.btn_imggame_ex1 :
                        img_imggame_check.bringToFront();
                        break;
                    case R.id.btn_imggame_ex2 :
                        btn_imggame_ex2.setBackgroundResource(R.drawable.button_ans);
                        btn_imggame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_imggame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_imggame_ex4.setBackgroundResource(R.drawable.button_ex);
                        break;
                    case R.id.btn_imggame_ex3 :
                        btn_imggame_ex3.setBackgroundResource(R.drawable.button_ans);
                        btn_imggame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_imggame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_imggame_ex4.setBackgroundResource(R.drawable.button_ex);
                        break;
                    case R.id.btn_imggame_ex4 :
                        btn_imggame_ex4.setBackgroundResource(R.drawable.button_ans);
                        btn_imggame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_imggame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_imggame_ex3.setBackgroundResource(R.drawable.button_ex);
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