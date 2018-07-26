package com.alphago.alphago.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alphago.alphago.Constants;
import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.fragment.GameModeSelectionDialog;
import com.alphago.alphago.fragment.HelpFragment;
import com.alphago.alphago.fragment.ImageSelectionMethodDialog;
import com.alphago.alphago.handler.BackPressCloseHandler;

import static com.alphago.alphago.Constants.LANGUAGE_CHI;
import static com.alphago.alphago.Constants.LANGUAGE_JAP;

public class MainActivity extends NoStatusBarActivity implements HelpFragment.OnCloseListener {
    private BackPressCloseHandler backPressCloseHandler;
    private String lang;

    private HelpFragment fragment;

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

        final DbHelper dbHelper = new DbHelper(getBaseContext());

        lang = StartActivity.sharedPreferences.getString("Language", "ENG");

        backPressCloseHandler = new BackPressCloseHandler(this);

        findViewById(R.id.btn_recognition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetWork())
                    new ImageSelectionMethodDialog().show(getSupportFragmentManager(), "dialog");
                else
                    Toast.makeText(MainActivity.this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
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
                if (dbHelper.getCardbookCount() < 10) {
                    Toast.makeText(MainActivity.this, "10장의 카드를 채워주세요!", Toast.LENGTH_SHORT).show();
                } else {
                    new GameModeSelectionDialog().show(getSupportFragmentManager(), "dialog");
                }
            }
        });

        findViewById(R.id.btn_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguageDialog();
            }
        });

        findViewById(R.id.hideButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInformation();
            }
        });

        findViewById(R.id.btn_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new HelpFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container_main, fragment).commit();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }

    @Override
    public void onClose() {
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void setLanguageDialog() {
        final SharedPreferences.Editor editor = StartActivity.sharedPreferences.edit();

        final String[] languages = {"영어", "일본어", "중국어"};

        int checked = -1;
        switch (Constants.getLanguage(lang)) {
            case LANGUAGE_JAP:
                checked = 1;
                break;
            case LANGUAGE_CHI:
                checked = 2;
                break;
            default:
                checked = 0;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("언어를 선택하세요.");
        builder.setSingleChoiceItems(languages, checked,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 1:
                                lang = "JAP";
                                Toast.makeText(MainActivity.this, "일본어로 선택되었습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                lang = "CHI";
                                Toast.makeText(MainActivity.this, "중국어로 선택되었습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "영어로 선택되었습니다.", Toast.LENGTH_SHORT).show();
                                lang = "ENG";
                        }
                        editor.putString("Language", lang);
                        editor.apply();
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showInformation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("개인정보 처리 취급방침을 보시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url)));
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private Boolean isNetWork() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager != null ? manager.getActiveNetworkInfo() : null;
        return activeNetwork != null;

    }
}
