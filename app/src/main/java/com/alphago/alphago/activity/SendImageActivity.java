package com.alphago.alphago.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;
import com.alphago.alphago.dto.ResponeImageLabel;

import java.io.File;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendImageActivity extends NoStatusBarActivity {
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        imageFile = (File) getIntent().getSerializableExtra("sendImage");
        if (imageFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.sendImageView);
            myImage.setImageBitmap(myBitmap);
        }

//        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlphagoServer.getInstance().sendImage(getBaseContext(), imageFile, new Callback<ResponeImageLabel>() {
//                    @Override
//                    public void onResponse(Call<ResponeImageLabel> call, Response<ResponeImageLabel> response) {
//                        if (response.body() != null) {
//                            Toast.makeText(SendImageActivity.this, response.body().getResponseLabel(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponeImageLabel> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//            }
//        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ImageRecognitionActivity.class);
                intent.putExtra("imageFile",imageFile);
                startActivity(intent);
                finish();
            }
        });
    }
}