package com.alphago.alphago.api;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.alphago.alphago.dto.ResponeImageLabel;
import com.alphago.alphago.dto.ResponseRequestResult;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://52.79.52.192:3002/")
                .baseUrl("http://13.124.3.169:3030/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        alphagoService = retrofit.create(AlphagoService.class);
    }

    public void sendImage(@NonNull Context context, File imageFile, Callback<ResponeImageLabel> callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image"), imageFile);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("userfile", imageFile.getName(), requestBody);
        alphagoService.sendImage(multipartBody).enqueue(callback);
    }

    public void fileDownload(@NonNull Context context, Callback<ResponseBody> callback){
        alphagoService.downloadFile().enqueue(callback);
    }

    public void requestTrain(@NonNull Context context, File imageFile, String fileName, Callback<ResponseRequestResult> callBack){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image"), imageFile);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("userfile", fileName, requestBody);
        alphagoService.requestTrain(multipartBody).enqueue(callBack);
    }
}