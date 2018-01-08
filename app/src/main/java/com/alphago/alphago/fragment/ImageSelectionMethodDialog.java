package com.alphago.alphago.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alphago.alphago.R;
import com.alphago.alphago.activity.ImageRecognitionActivity;


/**
 * Created by su_me on 2018-01-07.
 */

public class ImageSelectionMethodDialog extends DialogFragment {

    private Button btnImageCapture;
    private Button btnImageSelect;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_img_select_method, null);
        builder.setView(rootView);

        btnImageCapture = (Button) rootView.findViewById(R.id.btn_img_capture);
        btnImageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "사진 촬영 버튼 선택", Toast.LENGTH_SHORT).show();
                // 메인 액티비티에게 전달해 줄 인터페이스 구현.
                // 프래그먼트에서 액티비티로 이벤트 전달하는 것
                Intent intent = new Intent(getContext(), ImageRecognitionActivity.class);
                startActivity(intent);
            }
        });

        btnImageSelect = (Button) rootView.findViewById(R.id.btn_img_album_select);
        btnImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "사진 선택 버튼 선택", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ImageRecognitionActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }


}
