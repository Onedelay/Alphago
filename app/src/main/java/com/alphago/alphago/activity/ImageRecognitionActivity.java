package com.alphago.alphago.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
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
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


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
                    .centerInside()
                    .fit()
                    .into(myImage);
        }

        final String categoryName = getIntent().getStringExtra("category");
        final String maxLabel = getIntent().getStringExtra("max_label");
        final int catID = getIntent().getIntExtra("cate_ID", 0);
        final int ID = getIntent().getIntExtra("ID", 0);

        TextView textView = (TextView)findViewById(R.id.result_recog);
        textView.setText(maxLabel);

        final DbHelper dbHelper = new DbHelper(getBaseContext());

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
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = storeImageFile(imageFile, maxLabel);
                Toast.makeText(getBaseContext(),"저장되었습니다!",Toast.LENGTH_SHORT).show();

                dbHelper.insertImage(categoryName, maxLabel, catID, ID, filePath);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    // 내부저장소 Alphago 폴더 내 사진 저장
        private String storeImageFile(File imageFile, String imageLabel){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Alphago";

        File dirAlphago = new File(dirPath);
        if( !dirAlphago.exists() ) dirAlphago.mkdirs();

        String fileName = String.format(imageLabel+System.currentTimeMillis()+".jpg");

        File outFile = new File(dirAlphago, fileName);

        try{
            FileOutputStream outStream = new FileOutputStream(outFile);
            FileInputStream inputStream = new FileInputStream(imageFile);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            sendBroadcast(intent);

            byte[] buff = new byte[1024];
            while(inputStream.read(buff) > 0) {
                outStream.write(buff);
            }
            outStream.flush();
            outStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        return dirPath+"/"+fileName;
    }
}
