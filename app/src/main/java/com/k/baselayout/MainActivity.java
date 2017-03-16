package com.k.baselayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "调用sdk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }


    // 调用流程
    private void test() {
        BaseSDK.checkLiveness("openID", new BaseSDKCallback() {
            @Override
            public void onCheckResult(boolean success, String token) {
                if (success) {
                    Log.i(TAG, "验证成功, token为:" + token);
                } else {
                    Log.i(TAG, "验证不通过");
                }
            }

            @Override
            public void onCheckFail(ZTOFaceLivenessError error) {
                switch (error) {
                    case ZTOFaceLivenessCancel:
                    {
                        Log.i(TAG, "用户主动取消");
                    }
                    break;
                    case ZTOFaceLivenessFail:
                    {
                        Log.i(TAG, "网络请求失败?");
                    }
                    break;
                    case ZTOFaceLivenessForbidden:
                    {
                        Log.i(TAG, "权限禁止");
                    }
                    break;
                }
            }
        });
    }
}
