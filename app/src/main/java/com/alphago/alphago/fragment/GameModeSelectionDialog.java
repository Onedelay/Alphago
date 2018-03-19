package com.alphago.alphago.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alphago.alphago.R;
import com.alphago.alphago.activity.GameImageActivity;
import com.alphago.alphago.activity.GameWordActivity1;
import com.alphago.alphago.activity.GameWordActivity2;
import com.alphago.alphago.activity.GameWordActivity3;


/**
 * Created by su_me on 2018-01-07.
 */

public class GameModeSelectionDialog extends DialogFragment {
    public static final int GAME_MODE_WORD = 0;
    public static final int GAME_MODE_PICTURE = 1;
    public static final int GAME_DIFFICULTY_EASY = 0;
    public static final int GAME_DIFFICULTY_NORMAL = 1;
    public static final int GAME_DIFFICULTY_HARD = 2;

    private int gameMode = -1;
    private int gameDifficulty = -1;

    Intent intent;

    int grade;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View rootView = inflater.inflate(R.layout.dialog_mode_select_method, null);
        builder.setView(rootView);

        rootView.findViewById(R.id.btn_word_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "단어 맞추기 선택", Toast.LENGTH_SHORT).show();
                gameMode = GAME_MODE_WORD;
                setDifficultyGrade(rootView);
                selectDifficultyGrade(rootView);
            }
        });

        rootView.findViewById(R.id.btn_img_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "그림 맞추기 선택", Toast.LENGTH_SHORT).show();
                intent = new Intent(getContext(), GameImageActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setAttributes(params);
        }
    }

    private void setDifficultyGrade(View rootView){
        rootView.findViewById(R.id.select_mode_text).setVisibility(View.GONE);
        rootView.findViewById(R.id.select_difficulty_display).setVisibility(View.VISIBLE);
    }

    private void selectDifficultyGrade(View rootView){
        rootView.findViewById(R.id.btn_easy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), GameWordActivity1.class);
                intent.putExtra("gameDifficulty",GAME_DIFFICULTY_EASY);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), GameWordActivity2.class);
                intent.putExtra("gameDifficulty",GAME_DIFFICULTY_NORMAL);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.btn_hard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), GameWordActivity3.class);
                intent.putExtra("gameDifficulty",GAME_DIFFICULTY_HARD);
                startActivity(intent);
            }
        });
    }
}
