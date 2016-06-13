package com.netlib.api;

import com.netlib.model.Bean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by thy on 16-6-13 下午5:12.
 * E-mail : jieooo7@163.com
 */
public interface APINetInterface {

    @FormUrlEncoded
    @POST(UrlConstant.BASE_URL)
    Observable<retrofit2.Response<Bean>> login(
            @Field("username") String username
            , @Field("password") String password
            , @Field("remember") boolean remember
    );


}
