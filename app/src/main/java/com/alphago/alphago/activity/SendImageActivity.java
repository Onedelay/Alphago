package com.alphago.alphago.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;
import com.alphago.alphago.dto.ResponeImageLabel;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendImageActivity extends NoStatusBarActivity {
    private FrameLayout frameLoading;
    private File imageFile;
    private String category;
    private String max_label;
    private int ID;
    private int cate_ID;
    private CropImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        imageFile = (File) getIntent().getSerializableExtra("sendImage");

        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        cropImageView.setImageUriAsync(Uri.fromFile(imageFile));
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
        cropImageView.setAutoZoomEnabled(false);
        cropImageView.setCropRect(new Rect(1000, 1000, 1000, 1000));

        findViewById(R.id.crop_image_menu_rotate_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(90);
            }
        });

        findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLoading.setVisibility(View.VISIBLE);

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        frameLoading.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                frameLoading.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    }
//                }).start();

                cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
                    @Override
                    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                    }
                });
                cropImageView.getCroppedImageAsync();

                Bitmap bitmap = cropImageView.getCroppedImage();
                try{
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] bitmapData = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(imageFile);
                    fos.write(bitmapData);
                    fos.flush();
                    fos.close();
                } catch (Exception e){
                    e.printStackTrace();
                }

                AlphagoServer.getInstance().sendImage(getBaseContext(), imageFile, new Callback<ResponeImageLabel>() {
                    @Override
                    public void onResponse(Call<ResponeImageLabel> call, Response<ResponeImageLabel> response) {
                        frameLoading.setVisibility(View.GONE);
                        if (response.body() != null) {
                            category = response.body().getCategory();
                            max_label = response.body().getResponseLabel();
                            ID = response.body().getID();
                            cate_ID = response.body().getCate_ID();

                            Intent intent = new Intent(getBaseContext(), ImageRecognitionActivity.class);
                            intent.putExtra("imageFile", imageFile);
                            intent.putExtra("category", category);
                            intent.putExtra("max_label", max_label);
                            intent.putExtra("ID", ID);
                            intent.putExtra("cate_ID",cate_ID);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponeImageLabel> call, Throwable t) {
                        frameLoading.setVisibility(View.GONE);
                        Toast.makeText(SendImageActivity.this, "서버 연결 안됨", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        frameLoading = (FrameLayout) findViewById(R.id.frame_loading);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
