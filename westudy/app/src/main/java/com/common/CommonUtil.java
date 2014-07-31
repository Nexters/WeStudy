package com.common;

import org.json.JSONObject;
import org.json.JSONArray;
import android.util.Log;

public class CommonUtil {
    public static JSONArray stringToJSONArray(String str) {
        try{
            JSONArray data = new JSONArray(str);
            return data;
        }catch(Exception e){
            Log.e("JSONArray Exception",e.toString());
            return null;
        }
    }

    public static JSONObject stringToJSONObject(String str) {
        try{
            JSONObject data = new JSONObject(str);
            return data;
        }catch(Exception e){
            Log.e("JSONObject Exception",e.toString());
            return null;
        }
    }
}
