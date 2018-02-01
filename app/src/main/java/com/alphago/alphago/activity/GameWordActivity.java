package com.alphago.alphago.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.alphago.alphago.R;

public class GameWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_word);

        Button btn_exit = (Button)findViewById(R.id.btn_wordgame_exit);
        Button btn_next = (Button)findViewById(R.id.btn_wordgame_next);
    }
}
