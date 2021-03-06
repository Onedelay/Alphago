package com.alphago.alphago.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;
import com.alphago.alphago.dto.ResponseImageLabel;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendImageActivity extends NoStatusBarActivity {
    private FrameLayout frameLoading;
    private View buttonRotate;
    private View buttonRetry;
    private View buttonSend;
    private File imageFile;
    private String category;
    private String max_label;
    private String ko_label;
    private String ja_label;
    private String ch_label;
    private int ID;
    private int cate_ID;
    private CropImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        imageFile = (File) getIntent().getSerializableExtra("sendImage");

        cropImageView = findViewById(R.id.cropImageView);
        cropImageView.setImageUriAsync(Uri.fromFile(imageFile));
        cropImageView.setAspectRatio(1, 1);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
        cropImageView.setAutoZoomEnabled(false);
        cropImageView.setCropRect(new Rect(1000, 1000, 1000, 1000));
        cropImageView.setShowProgressBar(false);

        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                Bitmap bitmap = result.getBitmap();
                if (bitmap != null) {
                    try {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                        byte[] bitmapData = bos.toByteArray();

                        FileOutputStream fos = new FileOutputStream(imageFile);
                        fos.write(bitmapData);
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                AlphagoServer.getInstance().sendImage(getBaseContext(), imageFile, new Callback<ResponseImageLabel>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseImageLabel> call, @NonNull Response<ResponseImageLabel> response) {
                        ResponseImageLabel body = response.body();
                        if (body != null) {
                            category = body.getCategory();
                            max_label = body.getResponseLabel();
                            ID = body.getLABEL_ID();
                            cate_ID = body.getCAT_ID();
                            ko_label = body.getKo();
                            ja_label = body.getJa();
                            ch_label = body.getZh_CN();

                            Intent intent = new Intent(getBaseContext(), ImageRecognitionActivity.class);
                            intent.putExtra("imageFile", imageFile);
                            intent.putExtra("category", category);
                            intent.putExtra("max_label", max_label);
                            intent.putExtra("ID", ID);
                            intent.putExtra("cate_ID", cate_ID);
                            intent.putExtra("ko_label", ko_label);
                            intent.putExtra("ja_label", ja_label);
                            intent.putExtra("ch_label", ch_label);

                            showLoadingView(false);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseImageLabel> call, Throwable t) {
                        showLoadingView(false);
                        Toast.makeText(SendImageActivity.this, "서버 연결 안됨", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        buttonRotate = findViewById(R.id.crop_image_menu_rotate_right);
        buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.rotateImage(90);
            }
        });

        buttonRetry = findViewById(R.id.btn_retry);
        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        buttonSend = findViewById(R.id.btn_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView(true);
                cropImageView.getCroppedImageAsync();
            }
        });

        frameLoading = findViewById(R.id.frame_loading);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showLoadingView(boolean show) {
        frameLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        cropImageView.setEnabled(!show);
        buttonRotate.setEnabled(!show);
        buttonRetry.setEnabled(!show);
        buttonSend.setEnabled(!show);
    }
}
