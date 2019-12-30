package com.bw.moni1.utils;

import android.os.Handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 时间：2019/12/30
 * 作者：徐黎明
 * 类的作用：
 */
public class OkHttputil {
    //为了异步请求
    Handler handler=new Handler();
    private static OkHttputil instance;
    private final OkHttpClient okHttpClient;
    //构造方法
    public OkHttputil() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3,TimeUnit.SECONDS)
                .writeTimeout(3,TimeUnit.SECONDS)
               //日志拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }
    //二次锁
    public static OkHttputil getInstance() {
        if (instance == null) {
            synchronized (OkHttputil.class){
                if (instance == null) {
                    instance=new OkHttputil();
                }
            }
        }
        return instance;
    }
    //get方法
    public void doget(String url, final OkHttpCallBack okHttpCallBack){
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpCallBack != null) {
                            okHttpCallBack.fshibai(e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpCallBack != null) {
                            try {
                                okHttpCallBack.success(string);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
    public void dopost(String url, Map<String,String> parmas, final OkHttpCallBack okHttpCallBack){
        FormBody.Builder builder=new FormBody.Builder();
        for (Map.Entry<String,String> paramsMap: parmas.entrySet()){
            builder.add(paramsMap.getKey(),paramsMap.getValue());
        }
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpCallBack != null) {
                            okHttpCallBack.fshibai(e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (okHttpCallBack != null) {
                            try {
                                okHttpCallBack.success(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
    public interface OkHttpCallBack{
        void success(String repose);
        void fshibai(Throwable error);
    }

}
