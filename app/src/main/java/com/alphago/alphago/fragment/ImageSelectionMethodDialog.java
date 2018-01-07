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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by su_me on 2018-01-07.
 */

public class ImageSelectionMethodDialog extends DialogFragment {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    Button btnImageCapture;
    Button btnImageSelect;
    private Uri mImageCaptureUri;

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
                doTakePhotoAction();
            }
        });

        btnImageSelect = (Button) rootView.findViewById(R.id.btn_img_album_select);
        btnImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "사진 선택 버튼 선택", Toast.LENGTH_SHORT).show();
                doTakeAlbumAction();

            }
        });

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != RESULT_OK) return;

        switch (requestCode) {

            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("Alphago", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // crop할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1); // x축 비율
                intent.putExtra("aspectY", 1); // y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }

            case CROP_FROM_IMAGE: {
                if (requestCode != RESULT_OK) return;

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Alphago/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    Intent intent = new Intent(getContext(), ImageRecognitionActivity.class);
                    intent.putExtra("image",photo);

                    storeCropImage(photo, filePath);

                    startActivity(intent);
                    break;
                }


            }


        }
    }

    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + "jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Alphago";
        File directoryAlphago = new File(dirPath);

        if(!directoryAlphago.exists()) directoryAlphago.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
