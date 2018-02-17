package com.alphago.alphago.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.fragment.GameModeSelectionDialog;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.alphago.alphago.handler.BackPressCloseHandler;
import com.alphago.alphago.util.PermissionUtils;

import java.util.ArrayList;

public class MainActivity extends NoStatusBarActivity {
    private static final int REQUEST_PERMISSONS = 1;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtils.checkPermissions(this, REQUEST_PERMISSONS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        backPressCloseHandler = new BackPressCloseHandler(this);

        findViewById(R.id.btn_recognition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        findViewById(R.id.btn_card_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "카드 북 버튼 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CardBookActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameModeSelectionDialog().show(getSupportFragmentManager(), "dialog");
                /* Intent intent = new Intent(MainActivity.this, GameImageActivity.class);
                startActivity(intent); */
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSONS) {
            for (int i=0; i<permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, permissions[i] + " Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
