package com.alphago.alphago.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;

public class GameResultActivity extends NoStatusBarActivity {

    private final int WORD_GAME = 0;
    private final int IMAGE_GAME = 1;

    private int type;
    private int res[] = new int[10];

    private Button btn_rgame_ret;
    private Button btn_rgame_home;
    private ImageView img_rgame_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        btn_rgame_ret = (Button)findViewById(R.id.btn_rgame_ret);
        btn_rgame_home = (Button)findViewById(R.id.btn_rgame_home);

        Intent intent = getIntent();
        res = intent.getIntArrayExtra("result");

        Button.OnClickListener onClickListener = new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_rgame_ret :
                        intent = getIntent();
                        type = intent.getIntExtra("type", 0);
                        if (type == WORD_GAME)
                            intent = new Intent(getApplicationContext(), GameWordActivity2.class);
                        else if (type == IMAGE_GAME)
                            intent = new Intent(getApplicationContext(), GameImageActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.btn_rgame_home :
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        };
        btn_rgame_ret.setOnClickListener(onClickListener);
        btn_rgame_home.setOnClickListener(onClickListener);
    }
}