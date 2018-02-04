package com.alphago.alphago.api;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.alphago.alphago.dto.ResponeImageLabel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by su_me on 2018-02-04.
 */

public class AlphagoServer {
    private static AlphagoServer instance;
    private AlphagoService alphagoService;

    public static AlphagoServer getInstance() {
        if (instance == null) {
            instance = new AlphagoServer();
        }
        return instance;
    }

    private AlphagoServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.79.52.192:3002/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        alphagoService = retrofit.create(AlphagoService.class);
    }

    public void sendImage(@NonNull Context context, File imageFile, Callback<ResponeImageLabel> callback) {
        Uri uri = Uri.parse(imageFile.getAbsolutePath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image"), imageFile);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("userfile", imageFile.getName(), requestBody);
        alphagoService.sendImage(multipartBody).enqueue(callback);
    }
}