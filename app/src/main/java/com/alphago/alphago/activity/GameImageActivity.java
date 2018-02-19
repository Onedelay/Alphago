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

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;

public class GameImageActivity extends NoStatusBarActivity {

    private ImageButton btn_igame_exit;
    private ImageButton btn_igame_next;
    private Button btn_igame_ex1;
    private Button btn_igame_ex2;
    private Button btn_igame_ex3;
    private Button btn_igame_ex4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_image);

        btn_igame_exit = (ImageButton)findViewById(R.id.btn_igame_exit);
        btn_igame_next = (ImageButton)findViewById(R.id.btn_igame_next);
        btn_igame_ex1 = (Button)findViewById(R.id.btn_igame_ex1);
        btn_igame_ex2 = (Button)findViewById(R.id.btn_igame_ex2);
        btn_igame_ex3 = (Button)findViewById(R.id.btn_igame_ex3);
        btn_igame_ex4 = (Button)findViewById(R.id.btn_igame_ex4);

        btn_igame_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameImageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_igame_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Right or Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_igame_ex1 :
                        Toast.makeText(GameImageActivity.this, btn_igame_ex1.getText(), Toast.LENGTH_SHORT).show();
                        /* img_imggame_check1.setVisibility(View.VISIBLE);
                        img_imggame_check1.bringToFront();
                        img_imggame_check2.setVisibility(View.INVISIBLE);
                        img_imggame_check3.setVisibility(View.INVISIBLE);
                        img_imggame_check4.setVisibility(View.INVISIBLE); */
                        break;
                    case R.id.btn_igame_ex2 :
                        Toast.makeText(GameImageActivity.this, btn_igame_ex2.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_igame_ex3 :
                        Toast.makeText(GameImageActivity.this, btn_igame_ex3.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_igame_ex4 :
                        Toast.makeText(GameImageActivity.this, btn_igame_ex4.getText(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        btn_igame_ex1.setOnClickListener(onClickListener);
        btn_igame_ex2.setOnClickListener(onClickListener);
        btn_igame_ex3.setOnClickListener(onClickListener);
        btn_igame_ex4.setOnClickListener(onClickListener);
    }
}