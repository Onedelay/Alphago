package com.alphago.alphago.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alphago.alphago.R;
import com.alphago.alphago.activity.SendImageActivity;
import com.alphago.alphago.activity.WordLearningActivity;

/**
 * Created by su_me on 2018-01-07.
 */

public class LearningSelectionMethodDialog extends DialogFragment {
    public static final int TYPE_ALL = 0;
    public static final int TYPE_ALBUM = 1;

    private TextView method;
    private TextView method1;
    private TextView method2;

    Intent intent;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_img_select_method, null);
        builder.setView(rootView);

        method = (TextView) rootView.findViewById(R.id.how_to_img);
        method1 = (TextView) rootView.findViewById(R.id.btn_img_capture);
        method2 = (TextView) rootView.findViewById(R.id.btn_img_album_select);

        method.setText("학습 방법을 선택하세요.");
        method1.setText("카테고리 학습");
        method2.setText("전체 학습 ");

        intent = new Intent(getActivity(), WordLearningActivity.class);

        // 카테고리 학습
        rootView.findViewById(R.id.btn_img_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("learning_type", TYPE_ALBUM);
                startActivity(intent);
                getActivity().finish();

                 /* 다이얼로그 창 닫고
                * 인터페이스 전달
                * listener.onClickAll()
                * CardViewHolder를 참고하면서 인터페이스를 만들어보장
                * */
            }
        });

        // 전체 학습
        rootView.findViewById(R.id.btn_img_album_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("learning_type", TYPE_ALL);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // 상태바 숨김
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return builder.create();
    }

}
