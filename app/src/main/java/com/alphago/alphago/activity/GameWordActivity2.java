package com.alphago.alphago.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.TestData;

import org.w3c.dom.Text;

public class GameWordActivity2 extends NoStatusBarActivity {

    private int qst_num[] = new int[10];
    private int ex_num[][] = new int[10][4];
    private int qcount = 0;
    private boolean res[] = new boolean[10];
    private boolean result = false;

    private ImageButton btn_wgame_exit;
    private ImageButton btn_wgame_next;

    private Button btn_wgame_ex1;
    private Button btn_wgame_ex2;
    private Button btn_wgame_ex3;
    private Button btn_wgame_ex4;
    private ImageView img_wgame_tvqst;
    private TextView tv_wgame_tvqst;

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

        img_wgame_tvqst = (ImageView)findViewById(R.id.img_wgame_tvqst);
        tv_wgame_tvqst = (TextView)findViewById(R.id.tv_wgame_tvqst);

        if (qcount == 0) {
            CreateQuestion(TestData.dataID.length);
            SetQuestion(qcount);
            qcount++;
        }

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
                    res[qcount-1] = true;
                }
                else
                {
                    img_wgame_tvqst.setImageResource(R.drawable.img_wrong);
                    res[qcount-1] = false;
                }

                // Change the screen after 2.5 seconds
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (qcount == 10) {
                            Intent intent = new Intent(GameWordActivity2.this, GameResultActivity.class);
                            intent.putExtra("result", res);
                            intent.putExtra("type", 0);
                            startActivity(intent);
                        }
                        else {
                            tv_wgame_tvqst.setText("Q" + (qcount + 1) + " " + TestData.dataLabel[qst_num[qcount]]);
                            SetQuestion(qcount);
                            qcount++;
                        }
                    }
                }, 2500);
                // finish();
            }
        });
    }

    protected void CreateQuestion(int dcount)
    {
        int d, rnum;

        for (int i = 0; i < 10; i++)
        {
            d = 0;
            rnum = (int)(Math.random() * dcount);

            for (int j = 0; j < i; j++)
            {
                if (rnum == qst_num[j])
                {
                    d = 1;
                    break;
                }
            }
            if (d == 1)
                i--;
            else
            {
                qst_num[i] = rnum;
                CreateExample(dcount, i);
            }
        }

    }

    protected void CreateExample(int dcount, int qindex)
    {
        int d, rnum;
        int qrnum = (int)(Math.random() * 4);
        ex_num[qindex][qrnum] = qst_num[qindex];

        for (int i = 0; i < 4; i++)
        {
            if (i == qrnum)
                continue;

            d = 0;
            rnum = (int)(Math.random() * dcount);

            for (int j = 0; j < i; j++)
            {
                if (rnum == ex_num[qindex][j] || rnum == qrnum)
                {
                    d = 1;
                    break;
                }
            }
            if (d == 1)
                i--;
            else
                ex_num[qindex][i] = rnum;
        }
    }

    protected void SetQuestion(final int qcount) {
        result = false;

        btn_wgame_ex1.setText(TestData.dataLabel[ex_num[qcount][0]]);
        btn_wgame_ex2.setText(TestData.dataLabel[ex_num[qcount][1]]);
        btn_wgame_ex3.setText(TestData.dataLabel[ex_num[qcount][2]]);
        btn_wgame_ex4.setText(TestData.dataLabel[ex_num[qcount][3]]);

        img_wgame_tvqst.setImageResource(R.drawable.tv_qst);
        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_wgame_ex1 :
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[qcount][0] == qst_num[qcount]);
                            result = true;
                        break;
                    case R.id.btn_wgame_ex2 :
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[qcount][1] == qst_num[qcount])
                            result = true;
                        break;
                    case R.id.btn_wgame_ex3 :
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[qcount][2] == qst_num[qcount])
                            result = true;
                        break;
                    case R.id.btn_wgame_ex4 :
                        btn_wgame_ex4.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame_ex1.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex2.setBackgroundResource(R.drawable.button_ex);
                        btn_wgame_ex3.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[qcount][3] == qst_num[qcount])
                            result = true;
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