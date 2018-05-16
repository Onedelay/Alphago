package com.alphago.alphago.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.model.Card;
import com.alphago.alphago.model.CardBook;
import com.alphago.alphago.model.Category;
import com.alphago.alphago.util.TTSHelper;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.alphago.alphago.R.id.btn_learn_exit;
import static com.alphago.alphago.fragment.LearningSelectionMethodDialog.TYPE_ALBUM;
import static com.alphago.alphago.fragment.LearningSelectionMethodDialog.TYPE_ALL;

public class WordLearningActivity extends NoStatusBarActivity {

    private TTSHelper tts;
    private ImageView learnImage;
    private TextView learnLabel;

    private List<CardBook> cards = new ArrayList<>(); // 학습 할 카드들
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_learning);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        String lang = StartActivity.sharedPreferences.getString("Language","ENG");

        learnImage = (ImageView) findViewById(R.id.learn_image);
        learnLabel = (TextView) findViewById(R.id.learn_label);
        tts = new TTSHelper(this, lang);

        findViewById(btn_learn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_learn_pre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    index--;
                    setWord(index);
                } else {
                    Toast.makeText(WordLearningActivity.this, "첫번째 카드입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_learn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < cards.size() - 1) {
                    index++;
                    setWord(index);
                } else {
                    Toast.makeText(WordLearningActivity.this, "마지막 카드입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_pronounce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(learnLabel.getText().toString().equals("usb")) tts.speak("U S B");
                //else
                tts.speak(learnLabel.getText().toString());
            }
        });

        DbHelper dbHelper = new DbHelper(getBaseContext());

        int type = getIntent().getIntExtra("learning_type", 0);

        if (type == TYPE_ALL) {
            List<Category> categories = dbHelper.categorySelect(lang);
            List<CardBook> cardBooks;

            // 현재 카드북으로 되어있지만, 카드에서 중복 제거 후 구현해야함.
            for (Category category : categories) {
                cardBooks = dbHelper.cardbookSelect(category.getId(), lang);
                cards.addAll(cardBooks);
            }
        } else if (type == TYPE_ALBUM) {
            ArrayList<Long> list = (ArrayList<Long>) getIntent().getSerializableExtra("category_select_list");

            for (Long id : list) {
                List<CardBook> cardBooks = dbHelper.cardbookSelect(id, lang);
                cards.addAll(cardBooks);
            }
        } else {
            Toast.makeText(this, "TYPE ERROR", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (!cards.isEmpty()) {
            Collections.shuffle(cards);
            setWord(index);
        } else {
            Toast.makeText(WordLearningActivity.this, "학습할 목록이 없습니다", Toast.LENGTH_SHORT).show();
        }
    }


    public void setWord(int index) {
        String filePath = cards.get(index).getFilePath();
        String label = cards.get(index).getName();

        Picasso.with(getBaseContext())
                .load(new File(filePath))
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .fit()
                .into(learnImage);

        learnLabel.setText(label);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
