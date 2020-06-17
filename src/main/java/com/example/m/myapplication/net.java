package com.example.m.myapplication;


import okhttp3.OkHttpClient;
//请求网络
public class net {
        public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
            OkHttpClient okHttpClient=new OkHttpClient();
            okhttp3.Request request=new okhttp3.Request.Builder()
                    .url(address)
                    .build();
            okHttpClient.newCall(request).enqueue(callback);

        }
    }