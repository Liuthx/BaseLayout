package com.k.baselayout;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by k on 2017/3/16.
 */

public class BaseSDK {
    private static BaseSDK instance;

    public String openID;
    public BaseSDKCallback callback;
    public HashMap userInfo;

    private BaseSDK() {}

    public static synchronized BaseSDK getInstance() {
        if (instance == null) {
            instance = new BaseSDK();
        }
        return instance;
    }

    public static void checkLiveness(String openID, BaseSDKCallback callback) {
        BaseSDK sdk = BaseSDK.getInstance();

        sdk.openID = openID;
        sdk.callback = callback;

        sdk.checkIDCard(openID);
    }

    private void checkIDCard(String openID) {

        Log.i("", openID);

    }

}


