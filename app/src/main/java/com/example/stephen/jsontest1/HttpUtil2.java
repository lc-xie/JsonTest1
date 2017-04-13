package com.example.stephen.jsontest1;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by stephen on 17-4-13.
 */

public class HttpUtil2 {
    public static void sendOKHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
