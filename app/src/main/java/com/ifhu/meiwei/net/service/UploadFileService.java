package com.ifhu.meiwei.net.service;

import com.ifhu.meiwei.bean.BaseEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 上传文件的服务
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public interface UploadFileService {
    @Multipart
    @POST("image_upload")
    public Observable<BaseEntity<String>> imageUpload(@Part() MultipartBody.Part file );

}
