package com.alphago.alphago.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.util.PermissionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends NoStatusBarActivity {
    private static final int REQUEST_PERMISSONS = 1;

    private String zipFileName = "";
    private DbHelper dbHelper;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        dbHelper = new DbHelper(getBaseContext());

        PermissionUtils.checkPermissions(this, REQUEST_PERMISSONS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("Default",false)){
            controlStartActivity(0);
        }
    }

    private void downloadFile() {
        final long startTime = System.currentTimeMillis();
        AlphagoServer.getInstance().fileDownload(getBaseContext(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("WJY", "Server contacted and has file");
                    boolean writtenToDisk = writeResponseBodyToDist(response.body());
                    if (!writtenToDisk) {
                        Toast.makeText(StartActivity.this, "기본 이미지가 저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StartActivity.this, "기본 이미지가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("Default", true);
                        editor.apply();
                    }
                } else {
                    Log.d("#### WJY ####", "Server contact failed");
                }

                final long endTime = System.currentTimeMillis();
                controlStartActivity(endTime - startTime);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StartActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                final long endTime = System.currentTimeMillis();
                controlStartActivity(endTime - startTime);
            }
        });
    }

    private void controlStartActivity(long time) {
        if (time < 2000L) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000 - time);
        } else {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
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
                String fileDir = destDir + File.separator + fileName;
                File newFile = new File(fileDir);
                Log.v("WJY", "unzipping to " + newFile.getAbsolutePath());

                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }

                String[] result = fileName.split("_");
                dbHelper.insertImage(result[2], Integer.parseInt(result[1]), Integer.parseInt(result[3].substring(0, result[3].length() - 4)), fileDir, false);

                fileOutputStream.close();
                zis.closeEntry();
                ze = zis.getNextEntry();

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(newFile));
                sendBroadcast(intent);
            }

            zis.closeEntry();
            zis.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeResponseBodyToDist(ResponseBody body) {
        try {
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Alphago";

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
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSONS) {
            for (int i=0; i<permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, permissions[i] + " Permission Granted", Toast.LENGTH_SHORT).show();
                    if(!sharedPreferences.getBoolean("Default",false)) {
                        downloadFile();
                    } else {
                        controlStartActivity(0);
                    }
                } else {
                    //Toast.makeText(this, permissions[i] + " Permission Denied", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this, "권한이 없어 종료됩니다.", Toast.LENGTH_SHORT).show();
//                    try{
//                        Thread.sleep(2000L);
//                        finish();
//                    } catch (Exception e){}
                }
            }
        }
    }
}
