package com.k.baselayout;

/**
 * Created by k on 2017/3/16.
 */

public interface BaseSDKCallback {

    public static enum ZTOFaceLivenessError {
        ZTOFaceLivenessCancel,      //用户取消
        ZTOFaceLivenessForbidden,   //权限不足
        ZTOFaceLivenessFail,        //检查失败
    }

    void onCheckResult(boolean success, String token);
    void onCheckFail(ZTOFaceLivenessError error);
}
