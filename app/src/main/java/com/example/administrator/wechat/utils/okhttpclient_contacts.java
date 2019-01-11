package com.example.administrator.wechat.utils;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class okhttpclient_contacts {

    private static String URL="http://123.207.85.214/chat/member.php";
    public static void contacts(Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();
        client.newCall(request).enqueue(callback);

    }
}
