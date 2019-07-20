package com.kaqi.niuniu.ireader.model.remote;

import android.util.Log;

import com.kaqi.niuniu.ireader.utils.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by niqiao on 17-4-20.
 */

public class RemoteHelper {
    private static final String TAG = "okhttp";
    private static RemoteHelper sInstance;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private String isSelfBaseUrl = "";

    private RemoteHelper() {
        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();

                                //在这里获取到request后就可以做任何事情了
                                Response response = chain.proceed(request);
                                Log.d(TAG, "intercept: " + request.url().toString());
                                return response;
                            }
                        }
                ).build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.API_BASE_URL)
                .build();
        isSelfBaseUrl = "";
    }


    public static RemoteHelper getInstance() {
        sInstance = null;
        if (sInstance == null) {
            synchronized (RemoteHelper.class) {
                if (sInstance == null) {
                    sInstance = new RemoteHelper();
                }
            }
        }
        return sInstance;
    }


    public Retrofit getRetrofit() {
        //TODO Testing
        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();

                                //在这里获取到request后就可以做任何事情了
                                Response response = chain.proceed(request);
                                Log.d(TAG, "intercept: " + request.url().toString());
                                return response;
                            }
                        }
                ).build();
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.API_BASE_URL)
                .build();
        return mRetrofit;
    }

    public Retrofit getRetrofit(String url) {
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
