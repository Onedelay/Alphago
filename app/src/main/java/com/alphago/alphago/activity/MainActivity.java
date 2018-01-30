package com.alphago.alphago.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alphago.alphago.R;
import com.alphago.alphago.fragment.GameModeSelectionDialog;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.alphago.alphago.handler.BackPressCloseHandler;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

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
                finish();
            }
        });

        findViewById(R.id.btn_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameModeSelectionDialog().show(getSupportFragmentManager(), "dialog");
            }
        });


    }
}
