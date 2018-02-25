package com.alphago.alphago.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.squareup.picasso.Picasso;

import java.io.File;


public class ImageRecognitionActivity extends NoStatusBarActivity {

    private File imageFile;
    private ImageButton btnPronon;
    private Button btnRetry;
    private Button btnSave;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);

        imageFile = (File) getIntent().getSerializableExtra("imageFile");
        if (imageFile.exists()) {
            ImageView myImage = (ImageView) findViewById(R.id.image_recognition);
            Picasso.with(getBaseContext())
                    .load(imageFile)
                    .fit()
                    .into(myImage);
        }

        TextView textView = (TextView)findViewById(R.id.result_recog);
        textView.setText(getIntent().getStringExtra("max_label"));

        btnPronon = (ImageButton) findViewById(R.id.btn_pronounce);
        btnPronon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"발음 듣기 버튼 클릭",Toast.LENGTH_SHORT).show();
            }
        });

        btnRetry = (Button) findViewById(R.id.btn_retry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"다시하기 버튼 클릭",Toast.LENGTH_SHORT).show();
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"저장 버튼 클릭",Toast.LENGTH_SHORT).show();
            }
        });

        btnHome = (Button) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"홈 버튼 클릭",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ImageRecognitionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
