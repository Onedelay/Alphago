package com.alphago.alphago.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.SoundManager;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameImageActivity extends NoStatusBarActivity {

    private List<Category> categoryList = new ArrayList<>();
    private List<CardBook> cardBookList = new ArrayList<>();
    private DbHelper dbHelper = new DbHelper(this);

    private int qst_num[] = new int[10];
    private int ex_num[][] = new int[10][4];
    private int qcount = 0;
    private boolean res[] = new boolean[10];
    private boolean result = false;

    private ImageView btn_igame_exit;
    private ImageView btn_igame_next;

    private ImageView img_igame_check1;
    private ImageView img_igame_check2;
    private ImageView img_igame_check3;
    private ImageView img_igame_check4;

    private ImageView btn_igame_ex1;
    private ImageView btn_igame_ex2;
    private ImageView btn_igame_ex3;
    private ImageView btn_igame_ex4;
    private ImageView img_igame_tvqst;
    private TextView tv_igame_tvqst;
    private TextView img_igame_qst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_image);

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

        img_igame_check1 = (ImageView) findViewById(R.id.img_igame_check1);
        img_igame_check2 = (ImageView) findViewById(R.id.img_igame_check2);
        img_igame_check3 = (ImageView) findViewById(R.id.img_igame_check3);
        img_igame_check4 = (ImageView) findViewById(R.id.img_igame_check4);

        btn_igame_exit = (ImageView) findViewById(R.id.btn_igame_exit);
        btn_igame_next = (ImageView) findViewById(R.id.btn_igame_next);
        btn_igame_ex1 = (ImageView) findViewById(R.id.btn_igame_ex1);
        btn_igame_ex2 = (ImageView) findViewById(R.id.btn_igame_ex2);
        btn_igame_ex3 = (ImageView) findViewById(R.id.btn_igame_ex3);
        btn_igame_ex4 = (ImageView) findViewById(R.id.btn_igame_ex4);

        img_igame_tvqst = (ImageView) findViewById(R.id.img_igame_tvqst);
        tv_igame_tvqst = (TextView) findViewById(R.id.tv_igame_tvqst);
        img_igame_qst = (TextView) findViewById(R.id.img_igame_qst);

        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // First Question
        if (qcount == 0) {
            // CreateQuestion(TestData.dataID.length);
            CreateQuestion(cardBookList.size());
            img_igame_qst.setText(cardBookList.get(qst_num[qcount]).getName()); // TestData.dataLabel[qst_num[qcount]]
            SetQuestion(qcount);
            qcount++;
        }

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
                btn_igame_next.setEnabled(false);
                // Show Result
                if (result == true) {
                    SoundManager.playSound(1, 1);
                    img_igame_tvqst.setImageResource(R.drawable.img_right);
                    res[qcount - 1] = true;
                } else {
                    vibe.vibrate(500);
                    img_igame_tvqst.setImageResource(R.drawable.img_wrong);
                    res[qcount - 1] = false;
                }

                // Change the screen after 1.5 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (qcount == 10) {
                            Intent intent = new Intent(GameImageActivity.this, GameResultActivity.class);
                            intent.putExtra("result", res);
                            intent.putExtra("type", 1);
                            startActivity(intent);
                        } else {
                            tv_igame_tvqst.setText("Q" + (qcount + 1));
                            SetQuestion(qcount);
                            qcount++;
                        }
                    }
                }, 1500);
                // finish();
            }
        });
    }

    protected void CreateQuestion(int dcount) {
        int d, rnum;

        for (int i = 0; i < 10; i++) {
            d = 0;
            rnum = (int) (Math.random() * dcount);

            for (int j = 0; j < i; j++) {
                if (rnum == qst_num[j]) {
                    d = 1;
                    break;
                }
            }
            if (d == 1)
                i--;
            else {
                qst_num[i] = rnum;
                CreateExample(dcount, i);
            }
        }

    }

    protected void CreateExample(int dcount, int qindex) {
        int d, rnum;
        int qrnum = (int) (Math.random() * 4);
        ex_num[qindex][qrnum] = qst_num[qindex];

        for (int i = 0; i < 4; i++) {
            if (i == qrnum)
                continue;

            d = 0;
            rnum = (int) (Math.random() * dcount);
            if (rnum == ex_num[qindex][qrnum])
                d = 1;

            for (int j = 0; j < i; j++) {
                if (rnum == ex_num[qindex][j]) {
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SoundManager.cleanup();
    }

    protected void SetQuestion(final int qcount) {
        btn_igame_next.setEnabled(true);
        result = false;

        // 문제용 Label
        img_igame_qst.setText(cardBookList.get(qst_num[qcount]).getName()); // TestData.dataLabel[qst_num[qcount]]

        // Set Examples
        /* btn_igame_ex1.setText(TestData.dataLabel[ex_num[qcount][0]]);
        btn_igame_ex2.setText(TestData.dataLabel[ex_num[qcount][1]]);
        btn_igame_ex3.setText(TestData.dataLabel[ex_num[qcount][2]]);
        btn_igame_ex4.setText(TestData.dataLabel[ex_num[qcount][3]]); */
        Picasso.with(getBaseContext())
                .load(new File(cardBookList.get(ex_num[qcount][0]).getFilePath()))
                .centerInside()
                .fit()
                .into(btn_igame_ex1);
        Picasso.with(getBaseContext())
                .load(new File(cardBookList.get(ex_num[qcount][1]).getFilePath()))
                .centerInside()
                .fit()
                .into(btn_igame_ex2);
        Picasso.with(getBaseContext())
                .load(new File(cardBookList.get(ex_num[qcount][2]).getFilePath()))
                .centerInside()
                .fit()
                .into(btn_igame_ex3);
        Picasso.with(getBaseContext())
                .load(new File(cardBookList.get(ex_num[qcount][3]).getFilePath()))
                .centerInside()
                .fit()
                .into(btn_igame_ex4);

        // Example 선택 표시(game_right_border) 초기화
        img_igame_tvqst.setImageResource(R.drawable.tv_qst);
        img_igame_check1.setVisibility(View.GONE);
        img_igame_check2.setVisibility(View.GONE);
        img_igame_check3.setVisibility(View.GONE);
        img_igame_check4.setVisibility(View.GONE);
        /* btn_igame_ex1.setBackgroundResource(0);
        btn_igame_ex2.setBackgroundResource(0);
        btn_igame_ex3.setBackgroundResource(0);
        btn_igame_ex4.setBackgroundResource(0); */

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_igame_ex1:
                        img_igame_check1.setVisibility(View.VISIBLE);
                        img_igame_check2.setVisibility(View.GONE);
                        img_igame_check3.setVisibility(View.GONE);
                        img_igame_check4.setVisibility(View.GONE);
                        /* btn_igame_ex1.setBackgroundResource(R.drawable.game_right_border);
                        btn_igame_ex2.setBackgroundResource(0);
                        btn_igame_ex3.setBackgroundResource(0);
                        btn_igame_ex4.setBackgroundResource(0); */
                        if (ex_num[qcount][0] == qst_num[qcount])
                            result = true;
                        else
                            result = false;
                        break;
                    case R.id.btn_igame_ex2:
                        img_igame_check1.setVisibility(View.GONE);
                        img_igame_check2.setVisibility(View.VISIBLE);
                        img_igame_check3.setVisibility(View.GONE);
                        img_igame_check4.setVisibility(View.GONE);
                        /* btn_igame_ex1.setBackgroundResource(0);
                        btn_igame_ex2.setBackgroundResource(R.drawable.game_right_border);
                        btn_igame_ex3.setBackgroundResource(0);
                        btn_igame_ex4.setBackgroundResource(0); */
                        if (ex_num[qcount][1] == qst_num[qcount])
                            result = true;
                        else
                            result = false;
                        break;
                    case R.id.btn_igame_ex3:
                        img_igame_check1.setVisibility(View.GONE);
                        img_igame_check2.setVisibility(View.GONE);
                        img_igame_check3.setVisibility(View.VISIBLE);
                        img_igame_check4.setVisibility(View.GONE);
                         /* btn_igame_ex1.setBackgroundResource(0);
                        btn_igame_ex2.setBackgroundResource(0);
                        btn_igame_ex3.setBackgroundResource(R.drawable.game_right_border);
                        btn_igame_ex4.setBackgroundResource(0); */
                        if (ex_num[qcount][2] == qst_num[qcount])
                            result = true;
                        else
                            result = false;
                        break;
                    case R.id.btn_igame_ex4:
                        img_igame_check1.setVisibility(View.GONE);
                        img_igame_check2.setVisibility(View.GONE);
                        img_igame_check3.setVisibility(View.GONE);
                        img_igame_check4.setVisibility(View.VISIBLE);
                        /* btn_igame_ex1.setBackgroundResource(0);
                        btn_igame_ex2.setBackgroundResource(0);
                        btn_igame_ex3.setBackgroundResource(0);
                        btn_igame_ex4.setBackgroundResource(R.drawable.game_right_border); */
                        if (ex_num[qcount][3] == qst_num[qcount])
                            result = true;
                        else
                            result = false;
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