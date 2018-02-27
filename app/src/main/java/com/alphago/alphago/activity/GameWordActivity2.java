package com.alphago.alphago.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.TestData;

public class GameWordActivity2 extends NoStatusBarActivity {

    private int ex_num[] = new int[4];
    private int qst_index;
    private boolean result = false;
    private ImageButton btn_wgame_exit;
    private ImageButton btn_wgame_next;
    private Button btn_wgame_ex1;
    private Button btn_wgame_ex2;
    private Button btn_wgame_ex3;
    private Button btn_wgame_ex4;
    private ImageView img_wgame_tvqst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_word2);

        qst_index = CreateQuestion(TestData.dataID.length);

        btn_wgame_exit = (ImageButton)findViewById(R.id.btn_wgame_exit);
        btn_wgame_next = (ImageButton)findViewById(R.id.btn_wgame_next);
        btn_wgame_ex1 = (Button)findViewById(R.id.btn_wgame_ex1);
        btn_wgame_ex2 = (Button)findViewById(R.id.btn_wgame_ex2);
        btn_wgame_ex3 = (Button)findViewById(R.id.btn_wgame_ex3);
        btn_wgame_ex4 = (Button)findViewById(R.id.btn_wgame_ex4);

        img_wgame_tvqst = (ImageView)findViewById(R.id.img_wgame_tvqst);

        btn_wgame_ex1.setText(TestData.dataLabel[ex_num[0]]);
        btn_wgame_ex2.setText(TestData.dataLabel[ex_num[1]]);
        btn_wgame_ex3.setText(TestData.dataLabel[ex_num[2]]);
        btn_wgame_ex4.setText(TestData.dataLabel[ex_num[3]]);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_wgame_ex1 :
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[0] == ex_num[qst_index])
                            result = true;
                        break;
                    case R.id.btn_wgame_ex2 :
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[1] == ex_num[qst_index])
                            result = true;
                        break;
                    case R.id.btn_wgame_ex3 :
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[2] == ex_num[qst_index])
                            result = true;
                        break;
                    case R.id.btn_wgame_ex4 :
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[3] == ex_num[qst_index])
                            result = true;
                        break;
                }
            }
        };
        btn_wgame_ex1.setOnClickListener(onClickListener);
        btn_wgame_ex2.setOnClickListener(onClickListener);
        btn_wgame_ex3.setOnClickListener(onClickListener);
        btn_wgame_ex4.setOnClickListener(onClickListener);

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
                // Show Result
                if (result == true)
                {
                    img_wgame_tvqst.setImageResource(R.drawable.img_right);
                    Toast.makeText(getApplicationContext(), qst_index + "Right", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    img_wgame_tvqst.setImageResource(R.drawable.img_wrong);
                    Toast.makeText(getApplicationContext(), qst_index + "Wrong", Toast.LENGTH_SHORT).show();
                }

                // Change the screen after 2.5 seconds
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Intent intent = new Intent(GameWordActivity2.this, GameResultActivity.class);
                        startActivity(intent);
                    }
                }, 2500);
                // finish();
            }
        });
    }

    protected int CreateQuestion(int dcount)
    {
        int d, rnum;

        for (int i = 0; i < 4; i++)
        {
            d = 0;
            // data 개수만큼의 숫자 중 random number 생성
            rnum = (int)(Math.random() * dcount);

            // 중복 검사
            for (int j = 0; j < i; j++)
            {
                if (rnum == ex_num[j])
                {
                    d = 1;
                    break;
                }
            }
            if (d == 1)
                i--;
            else
                ex_num[i] = rnum;
        }
        // index of question
        return (int)(Math.random() * 4);
    }
}