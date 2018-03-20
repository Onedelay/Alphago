package com.alphago.alphago.handler;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by su_me on 2018-03-20.
 */

public class BitmapToFileTask extends AsyncTask<Bitmap, Void, File> {
    private File targetFile;
    private OnCompleteListener onCompleteListener;

    public interface OnCompleteListener {
        void onComplete(File file);
    }

    public BitmapToFileTask(File targetFile, OnCompleteListener onCompleteListener) {
        this.targetFile = targetFile;
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    protected File doInBackground(Bitmap... bitmaps) {
        Bitmap bitmap = bitmaps[0];
        if (bitmap != null) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                byte[] bitmapData = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(targetFile);
                fos.write(bitmapData);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return targetFile;
        }
        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        onCompleteListener.onComplete(file);
    }
}
