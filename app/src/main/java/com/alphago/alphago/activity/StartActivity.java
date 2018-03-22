package com.alphago.alphago.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends NoStatusBarActivity {

    private String zipFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);

        // downloadFile();
    }

    private void downloadFile() {
        AlphagoServer.getInstance().fileDownload(getBaseContext(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //Log.d("WJY", "Server contacted and has file");
                    boolean writtenToDisk = writeResponseBodyToDist(response.body());
                    if (!writtenToDisk) {
                        Toast.makeText(StartActivity.this, "Save failure", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StartActivity.this, "Save success", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("#### WJY ####", "Server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StartActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        if (!dir.exists()) dir.mkdirs();

        FileInputStream fis;
        byte[] buffer = new byte[1024];

        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                Log.v("WJY", "unzipping to " + newFile.getAbsolutePath());

                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.close();
                zis.closeEntry();
                ze = zis.getNextEntry();

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(newFile));
                sendBroadcast(intent);
            }

            if (zis != null) zis.closeEntry();
            if (zis != null) zis.close();
            if (fis != null) fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeResponseBodyToDist(ResponseBody body) {
        try {
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test";

            File dirFile = new File(dirPath);
            if (!dirFile.exists()) dirFile.mkdirs();
            zipFileName = "download" + System.currentTimeMillis() + ".zip";
            File saveFile = new File(dirFile, zipFileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            int read;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(saveFile);

//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                intent.setData(Uri.fromFile(saveFile));
//                sendBroadcast(intent);

                while (true) {
                    read = inputStream.read(fileReader);
                    if (read == -1) break;
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    Log.d("WJY", "File download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();

                unzip(dirPath + File.separator + zipFileName, dirPath);
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            return false;
        }
    }
}
