package com.alphago.alphago.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.alphago.alphago.R;
import com.alphago.alphago.activity.ImageRecognitionActivity;
import com.alphago.alphago.activity.SendImageActivity;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;


/**
 * Created by su_me on 2018-01-07.
 */

public class ImageSelectionMethodDialog extends DialogFragment {
    public static final int TYPE_GALLERY = 0;
    public static final int TYPE_CAMERA = 1;


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
                EasyImage.openCamera(ImageSelectionMethodDialog.this, TYPE_CAMERA);
            }
        });

        btnImageSelect = (Button) rootView.findViewById(R.id.btn_img_album_select);
        btnImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openGallery(ImageSelectionMethodDialog.this, TYPE_GALLERY);
            }
        });

        // 상태바 숨김
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(getContext(), "onImagePickError", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                //Toast.makeText(getContext(), "onImagePicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SendImageActivity.class);
                intent.putExtra("sendImage", imageFiles.get(0));
                startActivity(intent);
                dismiss();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Toast.makeText(getContext(), "onCanceld", Toast.LENGTH_SHORT).show();
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getContext());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setAttributes(params);
        }
    }
}
