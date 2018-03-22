package com.alphago.alphago.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class GameWordActivity3 extends NoStatusBarActivity {

    private List<Category> categoryList = new ArrayList<>();
    private List<CardBook> cardBookList = new ArrayList<>();
    private DbHelper dbHelper = new DbHelper(this);

    private int qst_num[] = new int[10];        // cardBookList.get(qst_num[i])
    private int qcount = 0;
    private boolean res[] = new boolean[10];
    private boolean result = false;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private ImageButton btn_wgame3_exit;
    private ImageButton btn_wgame3_next;

    private ImageView img_wgame3_tvqst;
    private TextView tv_wgame3_tvqst;
    private ImageView img_wgame3_qst;
    private EditText img_wgame3_lqst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_word3);

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

        btn_wgame3_exit = (ImageButton)findViewById(R.id.btn_wgame3_exit);
        btn_wgame3_next = (ImageButton)findViewById(R.id.btn_wgame3_next);

        img_wgame3_tvqst = (ImageView)findViewById(R.id.img_wgame3_tvqst);
        tv_wgame3_tvqst = (TextView)findViewById(R.id.tv_wgame3_tvqst);
        img_wgame3_qst = (ImageView) findViewById(R.id.img_wgame3_qst);
        img_wgame3_lqst = (EditText)findViewById(R.id.img_wgame3_lqst);

        // First Question
        if (qcount == 0) {
            // CreateQuestion(TestData.dataID.length);
            CreateQuestion(cardBookList.size());
            SetQuestion(qcount);
            // qcount++;
        }

        btn_wgame3_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWordActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_wgame3_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 답 확인
                String answer = cardBookList.get(qst_num[qcount]).getName();
                String input = img_wgame3_lqst.getText().toString();
                if (input.equals(answer))
                    result = true;
                else
                    result = false;

                // Show Result
                if (result == true)
                {
                    img_wgame3_tvqst.setImageResource(R.drawable.img_right);
                    res[qcount] = true;
                    qcount++;
                }
                else
                {
                    img_wgame3_tvqst.setImageResource(R.drawable.img_wrong);
                    res[qcount] = false;
                    qcount++;
                }

                // Change the screen after 2.0 seconds
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (qcount == 10) {
                            Intent intent = new Intent(GameWordActivity3.this, GameResultActivity.class);
                            intent.putExtra("result", res);
                            intent.putExtra("type", 0);
                            intent.putExtra("gameDifficulty",getIntent().getIntExtra("gameDifficulty",0));
                            startActivity(intent);
                            finish();
                        }
                        else {
                            //tv_wgame_tvqst.setText("Q" + (qcount + 1) + " " + cardBookList.get(qst_num[qcount]).getName());
                            tv_wgame3_tvqst.setText("Q" + (qcount + 1) + " ");
                            SetQuestion(qcount);
                            // qcount++;
                        }
                    }
                }, 2000);
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
            }
        }

    }

    protected void SetQuestion(final int qcount) {
        result = false;

        // 문제용 이미지 불러오기
        Picasso.with(getBaseContext())
                .load(new File(cardBookList.get(qst_num[qcount]).getFilePath()))
                .centerInside()
                .fit()
                .into(img_wgame3_qst);

        // 말풍선 초기화
        img_wgame3_tvqst.setImageResource(R.drawable.tv_qst2);
        img_wgame3_lqst.setText("");
    }
}