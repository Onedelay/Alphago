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

    private Button btn_rgame_ret;
    private Button btn_rgame_home;
    private ImageView img_rgame_result;
    // private int right = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        btn_rgame_ret = (Button)findViewById(R.id.btn_rgame_ret);
        btn_rgame_home = (Button)findViewById(R.id.btn_rgame_home);

        /*if (right > 8 && right <= 10)
            img_rgame_result.setBackgroundResource(R.drawable.bg_result1);
        else if (right > 4 && right <= 8)
            img_rgame_result.setBackgroundResource(R.drawable.bg_result2);
        else if (right <= 4 && right >= 0)
            img_rgame_result.setBackgroundResource(R.drawable.bg_result3); */

        Button.OnClickListener onClickListener = new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_rgame_ret :
                        intent = new Intent(getApplicationContext(), GameWordActivity2.class);
                        // Intent intent = new Intent(getApplicationContext(), GameImageActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.btn_rgame_home:
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
