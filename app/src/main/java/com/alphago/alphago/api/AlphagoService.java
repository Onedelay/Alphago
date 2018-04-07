package com.alphago.alphago.api;

import com.alphago.alphago.dto.ResponeImageLabel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by su_me on 2018-02-04.
 */

public interface AlphagoService {

    @Multipart
    @POST("/inception/upload")
    Call<ResponeImageLabel> sendImage(@Part MultipartBody.Part multipartBody);

    @GET("/inception/down_base")
    Call<ResponseBody> downloadFile();

    @Multipart
    @POST("/inception/train_req")
    Call<ResponseBody> requestTrain(@Part MultipartBody.Part multipartBody);
}
