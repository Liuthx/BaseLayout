package com.k.baselayout;

import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by k on 2017/3/16.
 */

public class BaseRequestUtil {

    static final String testUrl = "https://api.douban.com/v2/book/6548683";

    public static void requestGet(String requestURL, BaseRequestCallback callback) {
        try {
            URL url = new URL(requestURL);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setConnectTimeout(5 * 1000);
            urlConn.setReadTimeout(5 * 1000);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("GET");

            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.addRequestProperty("Connection", "Keep-Alive");

            urlConn.connect();

            if (urlConn.getResponseCode() == 200) {
                String result = streamToString(urlConn.getInputStream());
                JSONObject jsonObject = new JSONObject(result);
                callback.onRequestSuccess(jsonObject);
            } else {
                callback.onRequestFail(new Exception());
            }
            urlConn.disconnect();
        }
        catch (Exception e) {
            callback.onRequestFail(e);
        }
    }

    public static void requestPost(String requestURL, HashMap<String, String> paramsMap, BaseRequestCallback callback) {

        try {

            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key,  URLEncoder.encode(paramsMap.get(key),"utf-8")));
                pos++;
            }
            String params =tempParams.toString();
            byte[] postData = params.getBytes();

            URL url = new URL(requestURL);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setConnectTimeout(5 * 1000);
            urlConn.setReadTimeout(5 * 1000);
            urlConn.setDoOutput(false);

            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestMethod("POST");
            urlConn.setInstanceFollowRedirects(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.connect();

            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            if (urlConn.getResponseCode() == 200) {
                String result = streamToString(urlConn.getInputStream());
                JSONObject jsonObject = new JSONObject(result);
                callback.onRequestSuccess(jsonObject);
            } else {
                callback.onRequestFail(new Exception());
            }

            urlConn.disconnect();
        } catch (Exception e) {
            callback.onRequestFail(e);
        }
    }

    private static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e("", e.toString());
            return null;
        }
    }
}

