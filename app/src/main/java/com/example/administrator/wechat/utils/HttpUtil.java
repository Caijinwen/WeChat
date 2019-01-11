package com.example.administrator.wechat.utils;

public class HttpUtil {
    public static String url ="http://123.207.85.214/chat/";
    public static String getUrl;
    public HttpUtil(String name){
        getUrl=url+name;
    }
}
