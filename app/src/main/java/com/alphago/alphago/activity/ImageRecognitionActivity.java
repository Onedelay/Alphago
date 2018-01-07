package com.alphago.alphago.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.alphago.alphago.R;

public class ImageRecognitionActivity extends AppCompatActivity {

    private ImageView imageRecognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);

        Intent intent = getIntent();
        Bitmap photo = intent.getExtras().getParcelable("image");
        imageRecognition = (ImageView) findViewById(R.id.image_recognition);
        imageRecognition.setImageBitmap(photo);

    }
}
