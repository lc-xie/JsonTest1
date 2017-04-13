package com.example.stephen.jsontest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText=(TextView)findViewById(R.id.text_view);
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button){
            //sendRequestWithHttpConnection();
            //sendRequestWithOkHttp();
            String address="http://guolin.tech/api/china";
            String response=HttpUtil.sendHttpRequest(address);
            parseJSONWithJsonObject(response);
            show(response);
        }
    }

    //使用httpconnection
    private void sendRequestWithHttpConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection =null;
                BufferedReader reader=null;
                try{
                    URL url=new URL("http://guolin.tech/api/china");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    InputStream in =connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    show(response.toString());
                    parseJSONWithGson(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try{
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    //使用okhttp
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    client.connectTimeoutMillis();
                    client.readTimeoutMillis();
                    Request request=new Request.Builder()
                            .url("http://guolin.tech/api/china")
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONWithJsonObject(responseData);
//                    parseJSONWithGson(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGson(String jsonData){
        Gson gson=new Gson();
        List<Province>provinceList=gson.fromJson(jsonData, new TypeToken<List<Province>>(){}.getType());
        for(Province province:provinceList){
            Log.d("MainActivity","id is: "+province.getId());
            Log.d("MainActivity","name is: "+province.getName());
        }
    }

    private void parseJSONWithJsonObject(String str){
        try{
            JSONArray jsonArray=new JSONArray(str);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                int id=jsonObject.getInt("id");
                String name=jsonObject.getString("name");
                Log.d("MainActivity","id is: "+id);
                Log.d("MainActivity","name is: "+name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void show(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(str);
            }
        });
    }
}
