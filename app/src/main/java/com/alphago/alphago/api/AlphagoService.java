package com.alphago.alphago.api;

import com.alphago.alphago.dto.ResponeImageLabel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by su_me on 2018-02-04.
 */

public interface AlphagoService {

    @Multipart
    @POST("/upload")
    Call<ResponeImageLabel> sendImage(@Part MultipartBody.Part multipartBody);
}
