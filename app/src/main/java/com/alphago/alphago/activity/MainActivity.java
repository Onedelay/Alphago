package com.alphago.alphago.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.fragment.GameModeSelectionDialog;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.alphago.alphago.handler.BackPressCloseHandler;
import com.alphago.alphago.util.PermissionUtils;

public class MainActivity extends NoStatusBarActivity {
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

        findViewById(R.id.btn_recognition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        findViewById(R.id.btn_card_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardBookActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameModeSelectionDialog().show(getSupportFragmentManager(), "dialog");
            }
        });

        findViewById(R.id.btn_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });
    }
}
