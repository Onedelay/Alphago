package com.alphago.alphago.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphago.alphago.Constants;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.alphago.alphago.fragment.RequestImageTrainingFragment;
import com.alphago.alphago.util.TTSHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ImageRecognitionActivity extends NoStatusBarActivity implements RequestImageTrainingFragment.OnRequestTrainingListener {
    private File imageFile;
    private TTSHelper tts;
    private Button saveBtn;
    private Button wrongBtn;
    private TextView resultWord;
    private TextView resultKorean;
    private int catID;
    private int ID;

    private String maxLabel;
    private String japLabel;
    private String chLabel;
    private String enLabel;

    private DbHelper dbHelper;
    private Button.OnClickListener saveClickListener;
    private Button.OnClickListener requestClickListener;

    private RequestImageTrainingFragment requestFragment;

    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        lang = StartActivity.sharedPreferences.getString("Language", "ENG");

        dbHelper = new DbHelper(getBaseContext());

        imageFile = (File) getIntent().getSerializableExtra("imageFile");

        if (imageFile.exists()) {
            ImageView myImage = (ImageView) findViewById(R.id.image_recognition);
            Picasso.with(getBaseContext())
                    .load(imageFile)
                    .centerInside()
                    .fit()
                    .into(myImage);
        }

        Intent intent = getIntent();

        japLabel = intent.getStringExtra("ja_label");
        chLabel = intent.getStringExtra("ch_label");
        enLabel = intent.getStringExtra("max_label");

        String lang = StartActivity.sharedPreferences.getString("Language", "ENG");

        maxLabel = intent.getStringExtra("max_label");
        if (lang.equals("JAP")) {
            maxLabel = intent.getStringExtra("ja_label");
        } else if (lang.equals("CHI")) {
            maxLabel = intent.getStringExtra("ch_label");
        }

        catID = intent.getIntExtra("cate_ID", 0);
        ID = intent.getIntExtra("ID", 0);

        resultWord = (TextView) findViewById(R.id.recog_result);
        resultWord.setText(maxLabel);

        resultKorean = findViewById(R.id.result_kor);
        resultKorean.setText(intent.getStringExtra("ko_label"));

        tts = new TTSHelper(this, lang);
        findViewById(R.id.btn_pronounce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maxLabel.equals("usb")) tts.speak("U.S.B");
                else tts.speak(maxLabel);
            }
        });

        findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        saveBtn = (Button) findViewById(R.id.btn_save);
        saveClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!view.isSelected()) {
                    saveBtn.setText("저장완료");
                    view.setSelected(true);
                    String filePath = storeImageFile(imageFile, maxLabel);
                    Toast.makeText(getBaseContext(), "저장되었습니다!", Toast.LENGTH_SHORT).show();
                    dbHelper.insertImage(catID, ID, enLabel, japLabel, chLabel, filePath, true);
                } else {
                    Toast.makeText(getBaseContext(), "이미 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        requestClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!view.isSelected()) {
                    requestFragment = new RequestImageTrainingFragment();
                    requestFragment.show(getSupportFragmentManager(), "dialog");
                } else {
                    saveBtn.setOnClickListener(saveClickListener);
                }
            }
        };

        wrongBtn = findViewById(R.id.btn_wrong);
        wrongBtn.setOnClickListener(requestClickListener);

        try {
            if (maxLabel.equals("none")) {
                saveBtn.setText("틀렸어요");
                saveBtn.setOnClickListener(requestClickListener);
                wrongBtn.setVisibility(View.GONE);
            } else {
                saveBtn.setOnClickListener(saveClickListener);
            }
        } catch (NullPointerException e){
            Toast.makeText(this, "None 오류", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }


        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageRecognitionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    // 내부저장소 Alphago 폴더 내 사진 저장
    private String storeImageFile(File imageFile, String imageLabel) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Alphago";

        File dirAlphago = new File(dirPath);
        if (!dirAlphago.exists()) dirAlphago.mkdirs();

        String fileName = String.format(imageLabel + System.currentTimeMillis() + ".jpg");

        File outFile = new File(dirAlphago, fileName);

        try {
            FileOutputStream outStream = new FileOutputStream(outFile);
            FileInputStream inputStream = new FileInputStream(imageFile);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            sendBroadcast(intent);

            byte[] buff = new byte[1024];
            while (inputStream.read(buff) > 0) {
                outStream.write(buff);
            }
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath + "/" + fileName;
    }

    @Override
    public void onRequestTraining(String category, String label, String jaLabel, String chLabel, String korLabel, int cateId, int labelId) {
        switch (Constants.getLanguage(lang)) {
            case Constants.LANGUAGE_JAP:
                maxLabel = jaLabel;
                break;
            case Constants.LANGUAGE_CHI:
                maxLabel = chLabel;
                break;
            default:
                maxLabel = label;
        }
        resultWord.setText(maxLabel);
        resultKorean.setText(korLabel);

        this.enLabel = label;
        this.japLabel = jaLabel;
        this.chLabel = chLabel;
        catID = cateId;
        ID = labelId;

        saveBtn.setText("저장하기");
        saveBtn.setOnClickListener(saveClickListener);
        wrongBtn.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
