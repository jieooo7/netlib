package com.netlib.api;

import com.netlib.model.Bean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by thy on 16-6-13 下午5:11.
 * E-mail : jieooo7@163.com
 */
public class ApiRetrofitUtil {

    private static ApiRetrofitUtil retrofitUtil;

    public static ApiRetrofitUtil getInstance() {
        if (retrofitUtil == null)
            retrofitUtil = new ApiRetrofitUtil();
        return retrofitUtil;
    }

    private Retrofit retrofit;

    private ApiRetrofitUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("User-Agent", "a/1.0.0/4.2/meizu351/wifi")
                        .build();
                return chain.proceed(newRequest);
            }
        };
        builder.addInterceptor(interceptor);
        OkHttpClient okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    private APINetInterface netInterface;

    public APINetInterface getNetInterface() {
        if (netInterface == null)
            netInterface = retrofit.create(APINetInterface.class);
        return netInterface;
    }

    //登录
    public Observable<retrofit2.Response<Bean>> login(String username, String password) {
        return getNetInterface().login(username, password, false);
    }
}

//ApiRetrofitUtil.getInstance().get_jobs_detil(job_id,"")
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Observer<Response<JobDetilBean>>(){
//        }