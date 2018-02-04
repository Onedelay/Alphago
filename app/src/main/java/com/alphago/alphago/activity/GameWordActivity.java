package com.alphago.alphago.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;

public class GameWordActivity extends NoStatusBarActivity {

    private ImageView game_qst;
    private Button btn_exit;
    private Button btn_next;
    private Button btn_wordgame_ex1;
    private Button btn_wordgame_ex2;
    private Button btn_wordgame_ex3;
    private Button btn_wordgame_ex4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_word);

        game_qst = (ImageView)findViewById(R.id.game_qst);
        btn_exit = (Button)findViewById(R.id.btn_wordgame_exit);
        btn_next = (Button)findViewById(R.id.btn_wordgame_next);
        btn_wordgame_ex1 = (Button)findViewById(R.id.btn_wordgame_ex1);
        btn_wordgame_ex2 = (Button)findViewById(R.id.btn_wordgame_ex2);
        btn_wordgame_ex3 = (Button)findViewById(R.id.btn_wordgame_ex3);
        btn_wordgame_ex4 = (Button)findViewById(R.id.btn_wordgame_ex4);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Right or Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_wordgame_ex1 :
                        btn_wordgame_ex1.setBackgroundResource(R.drawable.button_ans);
                        btn_wordgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wordgame_ex1.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_wordgame_ex2 :
                        btn_wordgame_ex2.setBackgroundResource(R.drawable.button_ans);
                        btn_wordgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wordgame_ex2.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_wordgame_ex3 :
                        btn_wordgame_ex3.setBackgroundResource(R.drawable.button_ans);
                        btn_wordgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wordgame_ex3.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_wordgame_ex4 :
                        btn_wordgame_ex4.setBackgroundResource(R.drawable.button_ans);
                        btn_wordgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wordgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        Toast.makeText(getApplicationContext(), btn_wordgame_ex4.getText(),
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        btn_wordgame_ex1.setOnClickListener(onClickListener);
        btn_wordgame_ex2.setOnClickListener(onClickListener);
        btn_wordgame_ex3.setOnClickListener(onClickListener);
        btn_wordgame_ex4.setOnClickListener(onClickListener);
    }
}
