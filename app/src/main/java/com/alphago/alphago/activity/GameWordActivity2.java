package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alphago.alphago.R;

public class GameWordActivity2 extends AppCompatActivity {

    private ImageButton btn_wgame_exit;
    private ImageButton btn_wgame_next;
    private Button btn_wgame_ex1;
    private Button btn_wgame_ex2;
    private Button btn_wgame_ex3;
    private Button btn_wgame_ex4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_word2);

        btn_wgame_exit = (ImageButton)findViewById(R.id.btn_wgame_exit);
        btn_wgame_next = (ImageButton)findViewById(R.id.btn_wgame_next);
        btn_wgame_ex1 = (Button)findViewById(R.id.btn_wgame_ex1);
        btn_wgame_ex2 = (Button)findViewById(R.id.btn_wgame_ex2);
        btn_wgame_ex3 = (Button)findViewById(R.id.btn_wgame_ex3);
        btn_wgame_ex4 = (Button)findViewById(R.id.btn_wgame_ex4);

        btn_wgame_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWordActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_wgame_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Right or Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_wgame_ex1 :
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wgame_ex1.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_wgame_ex2 :
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wgame_ex2.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_wgame_ex3 :
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wgame_ex3.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_wgame_ex4 :
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wgame_ex4.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        btn_wgame_ex1.setOnClickListener(onClickListener);
        btn_wgame_ex2.setOnClickListener(onClickListener);
        btn_wgame_ex3.setOnClickListener(onClickListener);
        btn_wgame_ex4.setOnClickListener(onClickListener);
    }
}
