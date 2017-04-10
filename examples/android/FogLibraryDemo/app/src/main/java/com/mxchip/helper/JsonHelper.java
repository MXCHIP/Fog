package com.mxchip.helper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rocke on 2016/06/12.
 */
public class JsonHelper {
    public static String getFogToken(String message) {
        if (message.indexOf("token") > -1) {
            try {
                JSONObject loginjson = new JSONObject(message);
                return loginjson.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    public static String getFogData(String message) {
        if (message.indexOf("meta") > -1) {
            if (message.indexOf("data") > -1) {
                try {
                    JSONObject metajson = new JSONObject(message);
                    return metajson.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    public static String getFogEndUserid(String message) {
        if (message.indexOf("clientid") > -1) {
            try {
                JSONObject loginjson = new JSONObject(message);
                return loginjson.getString("clientid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getFogCode(String message) {
        if (message.indexOf("meta") > -1) {
            if (message.indexOf("code") > -1) {
                try {
                    JSONObject metajson = new JSONObject(message);
                    JSONObject msgjson = new JSONObject(metajson.getString("meta"));
                    return msgjson.getString("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
