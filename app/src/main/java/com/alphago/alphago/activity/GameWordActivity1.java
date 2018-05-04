package com.alphago.alphago.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameWordActivity1 extends NoStatusBarActivity {

    private List<Category> categoryList = new ArrayList<>();
    private List<CardBook> cardBookList = new ArrayList<>();
    private DbHelper dbHelper = new DbHelper(this);

    private int qst_num[] = new int[10];        // cardBookList.get(qst_num[i])
    private int ex_num[][] = new int[10][2];    // cardBookList.get(ex_num[i][j])
    private int qcount = 0;
    private boolean res[] = new boolean[10];
    private boolean result = false;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private ImageButton btn_wgame1_exit;
    private ImageButton btn_wgame1_next;

    private Button btn_wgame1_ex1;
    private Button btn_wgame1_ex2;
    private ImageView img_wgame1_tvqst;
    private TextView tv_wgame1_tvqst;
    private ImageView img_wgame1_qst;
    private TextView img_wgame1_lqst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_word1);

        // Load CardBook in Database
        categoryList = dbHelper.categorySelect();
        int catSize = categoryList.size();
        long catId = 0;
        int carSize = 0;

        for (int i = 0; i < catSize; i++) {
            catId = categoryList.get(i).getId();
            // 해당 카테고리의 카드북 반환
            List<CardBook> tmpList = dbHelper.cardbookSelect(catId);
            // 카드북의 사이즈 반환
            carSize = tmpList.size();
            // 사이즈만큼 돌면서 카드북에 추가
            for (int j = 0; j < carSize; j++) {
                cardBookList.add(tmpList.get(j));
            }
        }

        btn_wgame1_exit = (ImageButton)findViewById(R.id.btn_wgame1_exit);
        btn_wgame1_next = (ImageButton)findViewById(R.id.btn_wgame1_next);
        btn_wgame1_ex1 = (Button)findViewById(R.id.btn_wgame1_ex1);
        btn_wgame1_ex2 = (Button)findViewById(R.id.btn_wgame1_ex2);

        img_wgame1_tvqst = (ImageView)findViewById(R.id.img_wgame1_tvqst);
        tv_wgame1_tvqst = (TextView)findViewById(R.id.tv_wgame1_tvqst);
        img_wgame1_qst = (ImageView) findViewById(R.id.img_wgame1_qst);
        img_wgame1_lqst = (TextView)findViewById(R.id.img_wgame1_lqst);

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        // First Question
        if (qcount == 0) {
            // CreateQuestion(TestData.dataID.length);
            CreateQuestion(cardBookList.size());
            SetQuestion(qcount);
            qcount++;
        }

        btn_wgame1_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWordActivity1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_wgame1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_wgame1_next.setEnabled(false);
                // Show Result
                if (result == true)
                {
                    img_wgame1_tvqst.setImageResource(R.drawable.img_right);
                    res[qcount-1] = true;
                }
                else
                {
                    vibe.vibrate(500);
                    img_wgame1_tvqst.setImageResource(R.drawable.img_wrong);
                    res[qcount-1] = false;
                }

                // Change the screen after 1.5 seconds
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (qcount == 10) {
                            Intent intent = new Intent(GameWordActivity1.this, GameResultActivity.class);
                            intent.putExtra("result", res);
                            intent.putExtra("type", 0);
                            intent.putExtra("gameDifficulty",getIntent().getIntExtra("gameDifficulty",0));
                            startActivity(intent);
                            finish();
                        }
                        else {
                            //tv_wgame_tvqst.setText("Q" + (qcount + 1) + " " + cardBookList.get(qst_num[qcount]).getName());
                            tv_wgame1_tvqst.setText("Q" + (qcount + 1) + " ");
                            SetQuestion(qcount);
                            qcount++;
                        }
                    }
                }, 1500);
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
        int qrnum = (int)(Math.random() * 2);
        ex_num[qindex][qrnum] = qst_num[qindex];

        for (int i = 0; i < 2; i++)
        {
            if (i == qrnum)
                continue;

            d = 0;
            rnum = (int)(Math.random() * dcount);
            if (rnum == ex_num[qindex][qrnum])
                d = 1;

            for (int j = 0; j < i; j++)
            {
                if (rnum == ex_num[qindex][j])
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
        btn_wgame1_next.setEnabled(true);
        result = false;

        // 문제용 이미지 불러오기
        Picasso.with(getBaseContext())
                .load(new File(cardBookList.get(qst_num[qcount]).getFilePath()))
                .centerInside()
                .fit()
                .into(img_wgame1_qst);
        img_wgame1_lqst.setText(cardBookList.get(ex_num[qcount][0]).getName());

        // Set Examples
        /* btn_wgame_ex1.setText(TestData.dataLabel[ex_num[qcount][0]]);
        btn_wgame_ex2.setText(TestData.dataLabel[ex_num[qcount][1]]); */

        // 말풍선 및 Example 선택 표시 초기화
        img_wgame1_tvqst.setImageResource(R.drawable.tv_qst);
        btn_wgame1_ex1.setBackgroundResource(R.drawable.button_ex);
        btn_wgame1_ex2.setBackgroundResource(R.drawable.button_ex);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_wgame1_ex1 :
                        btn_wgame1_ex1.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame1_ex2.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[qcount][0] == qst_num[qcount])
                            result = true;
                        else
                            result = false;
                        break;
                    case R.id.btn_wgame1_ex2 :
                        btn_wgame1_ex2.setBackgroundResource(R.drawable.button_ans);
                        btn_wgame1_ex1.setBackgroundResource(R.drawable.button_ex);
                        if (ex_num[qcount][1] == qst_num[qcount])
                            result = true;
                        else
                            result = false;
                        break;
                }
            }
        };
        btn_wgame1_ex1.setOnClickListener(onClickListener);
        btn_wgame1_ex2.setOnClickListener(onClickListener);
    }
}
