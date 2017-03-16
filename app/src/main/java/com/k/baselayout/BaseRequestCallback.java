package com.k.baselayout;

import org.json.JSONObject;

/**
 * Created by k on 2017/3/16.
 */

public interface BaseRequestCallback {
    void onRequestSuccess(JSONObject jsonObject);
    void onRequestFail(Exception e);
}
