package com.fly.postop.rxhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.fly.postop.httplibrary.proxy.HttpCallBack;
import com.fly.postop.httplibrary.proxy.HttpHelper;
import com.shangyi.android.utils.ListUtils;
import com.shangyi.android.utils.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPost:
                httpPost();
                break;
            case R.id.btnGet:
                httpGet();
                break;
        }
    }

    private void httpGet() {
        String url = "/services/third/api/config/title/all";
        Map<String, Object> param = new HashMap<>();
        HttpHelper.getInstance().doGet(url, param, new HttpCallBack<ResponseJson<List<PositionEntity>>>() {
            @Override
            public void onSuccess(ResponseJson<List<PositionEntity>> objResult) {
                LogUtils.d("onsuccess: " + objResult.info);
                if (ListUtils.isEmpty(objResult.data)) {
                    LogUtils.d("result: " + objResult.data.size());
                }
            }

            @Override
            public void onError(String errorMsg) {
                LogUtils.d(errorMsg);
            }
        });
    }

    private void httpPost() {
        String url = "http://apis.juhe.cn/simpleWeather/query";
        Map<String, Object> param = new HashMap<>();
        param.put("city", "温州");
        param.put("key", "cb965e30b7bde3862929a1512247364b");
        HttpHelper.getInstance().doPost(url, param, new HttpCallBack<ResultEntity<WeatherEntity>>() {

            @Override
            public void onSuccess(ResultEntity<WeatherEntity> objResult) {
                LogUtils.d("onsuccess: " + objResult.reason);
                if (objResult.result != null) {
                    LogUtils.d("result: " + objResult.result.getCity());
                }
            }

            @Override
            public void onError(String errorMsg) {
                LogUtils.d(errorMsg);
            }
        });
    }
}
