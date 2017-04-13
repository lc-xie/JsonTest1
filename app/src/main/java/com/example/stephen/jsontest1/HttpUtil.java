package com.example.stephen.jsontest1;

import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

/**
 * Created by stephen on 17-4-13.
 */

public class HttpUtil {
    public static String sendHttpRequest(String address){

        HttpURLConnection connection=null;
        try{
            URL url=new URL(address);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream in=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder response=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null){
                response.append(line);
            }
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
    }

}
