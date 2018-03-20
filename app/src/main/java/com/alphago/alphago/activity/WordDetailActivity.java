package com.alphago.alphago.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.util.TTSHelper;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.alphago.alphago.R.id.btn_learn_exit;

public class WordDetailActivity extends NoStatusBarActivity {

    private TTSHelper tts;
    private ImageView learnImage;
    private TextView learnLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        learnLabel = findViewById(R.id.learn_label);
        learnImage = findViewById(R.id.learn_image);
        tts = new TTSHelper(this);

        String label = getIntent().getStringExtra("label");
        String filePath = getIntent().getStringExtra("filePath");

        learnLabel.setText(label);
        Picasso.with(this)
                .load(new File(filePath))
                .centerInside()
                .fit()
                .into(learnImage);

        findViewById(btn_learn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_pronounce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(learnLabel.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
