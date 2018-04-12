package com.alphago.alphago.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class ImageRecognitionActivity extends NoStatusBarActivity implements RequestImageTrainingFragment.OnRequestTrainingListener{
    private File imageFile;
    private TTSHelper tts;
    private Button saveBtn;
    private Button wrongBtn;
    private TextView textView;
    private int catID;
    private int ID;

    String maxLabel;

    private DbHelper dbHelper;
    private Button.OnClickListener saveClickListener;
    private Button.OnClickListener requestClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);

        dbHelper =  new DbHelper(getBaseContext());

        imageFile = (File) getIntent().getSerializableExtra("imageFile");

        if (imageFile.exists()) {
            ImageView myImage = (ImageView) findViewById(R.id.image_recognition);
            Picasso.with(getBaseContext())
                    .load(imageFile)
                    .centerInside()
                    .fit()
                    .into(myImage);
        }

        maxLabel = getIntent().getStringExtra("max_label");
        catID = getIntent().getIntExtra("cate_ID", 0);
        ID = getIntent().getIntExtra("ID", 0);

        textView = (TextView) findViewById(R.id.result_recog);
        textView.setText(maxLabel);

        tts = new TTSHelper(this);
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
        saveClickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!view.isSelected()) {
                    saveBtn.setText("SAVED");
                    view.setSelected(true);
                    String filePath = storeImageFile(imageFile, maxLabel);
                    Toast.makeText(getBaseContext(), "저장되었습니다!", Toast.LENGTH_SHORT).show();
                    dbHelper.insertImage(maxLabel, catID, ID, filePath, true);
                } else {
                    Toast.makeText(getBaseContext(), "이미 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        requestClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!view.isSelected()) {
                    new RequestImageTrainingFragment().show(getSupportFragmentManager(), "dialog");
                } else {
                    saveBtn.setOnClickListener(saveClickListener);
                }
            }
        };

        wrongBtn = findViewById(R.id.btn_wrong);
        wrongBtn.setOnClickListener(requestClickListener);

        if(maxLabel.equals("none")){
            saveBtn.setText("REQUEST");
            saveBtn.setOnClickListener(requestClickListener);
            wrongBtn.setVisibility(View.GONE);
        } else {
            saveBtn.setOnClickListener(saveClickListener);
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
    public void onRequestTraining(String category, String label) {
        maxLabel = label;
        textView.setText(maxLabel);
        catID = dbHelper.categoryIdSelect(category);
        ID = 0;

        saveBtn.setText("SAVE");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ImageRecognitionActivity.this, "레이블 ID가 없어서 저장 안됨", Toast.LENGTH_SHORT).show();
            }
        });
        //saveBtn.setOnClickListener(saveClickListener);
        wrongBtn.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }
}
