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
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.alphago.alphago.NoStatusBarActivity;
import com.alphago.alphago.R;
import com.alphago.alphago.api.AlphagoServer;
import com.alphago.alphago.database.DbHelper;
import com.alphago.alphago.fragment.InitSettingFragment;
import com.alphago.alphago.util.PermissionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends NoStatusBarActivity implements InitSettingFragment.OnSettingLanguageListener {
    private static final int REQUEST_PERMISSONS = 1;

    private String zipFileName = "";
    private DbHelper dbHelper;

    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        dbHelper = new DbHelper(getBaseContext());

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        int permissionWriteCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCamCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionWriteCheck == PackageManager.PERMISSION_GRANTED
                && permissionCamCheck == PackageManager.PERMISSION_GRANTED) {
            if (sharedPreferences.getBoolean("Default", false)) {
                controlStartActivity(0);
            }
        } else {
            guideMessage();
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
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("Default", true);
                        editor.apply();
                    }
                } else {
                    Log.d("#### WJY ####", "Server contact failed");
                    finish();
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
        //Toast.makeText(this, sharedPreferences.getString("Language", "none"), Toast.LENGTH_SHORT).show();
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
            //Charset CP866 = Charset.forName("CP866");
            ZipInputStream zis = new ZipInputStream(fis, Charset.forName("Cp437"));
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
                dbHelper.insertImage(Integer.parseInt(result[0]), Integer.parseInt(result[1]), result[2], result[3], result[4].substring(0, result[4].length() - 4), fileDir, false);
                //dbHelper.insertImage(result[2], Integer.parseInt(result[1]), Integer.parseInt(result[3].substring(0, result[3].length() - 4)), fileDir, false);

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
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pic2word";

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
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, permissions[i] + " Permission Granted", Toast.LENGTH_SHORT).show();
                if (!sharedPreferences.getBoolean("Default", false)) {
                    downloadFile();
                } else {
                    controlStartActivity(0);
                }
            } else {
                //Toast.makeText(this, permissions[i] + " Permission Denied", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "권한이 없어 종료됩니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void guideMessage(){
        if (sharedPreferences.getString("Language", "none").equals("none")) {
            InitSettingFragment settingFragment = new InitSettingFragment();
            settingFragment.show(getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    public void onSettingLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", language);
        editor.apply();

        PermissionUtils.checkPermissions(this, REQUEST_PERMISSONS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }
}
