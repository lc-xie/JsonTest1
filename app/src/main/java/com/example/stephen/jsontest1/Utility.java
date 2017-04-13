package com.example.stephen.jsontest1;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by stephen on 17-4-13.
 */

public class Utility {
    public static boolean handleProvinceResponseWithGSON(String response,List<Province>list){
        try{
            Gson gson=new Gson();
            List<Province>provinceList=gson.fromJson(response,new TypeToken<List<Province>>(){}.getType());
            for(Province province:provinceList){
                Province aProvince=new Province();
                aProvince.setId(province.getId());
                aProvince.setName(province.getName());
                list.add(aProvince);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean handleProvinceResponseWithJsonObject(String response){
        try{
            JSONArray allProvince=new JSONArray(response);
            for(int i=0;i<allProvince.length();i++){
                JSONObject provinceObject=allProvince.getJSONObject(i);
                Province province=new Province();
                province.setName(provinceObject.getString("name"));
                province.setId(provinceObject.getInt("id"));
                province.save();
            }
            return true;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }

}
