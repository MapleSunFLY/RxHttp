package com.fly.postop.httplibrary.interceptor;

import com.shangyi.android.utils.NetUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.postop.httplibrary.interceptor
 * 作    者 : FLY
 * 创建时间 : 2019/4/26
 * 描述: 网络缓存
 */
public class NetCacheInterceptor implements Interceptor {
    /**
     * 默认缓存60秒
     */
    private int cacheTime;

    public NetCacheInterceptor(int cacheTime) {
        this.cacheTime = cacheTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        boolean connected =  NetUtils.isNetworkConnected();
        if (connected) {
            //如果有网络，缓存60s
            Response response = chain.proceed(request);
            CacheControl.Builder builder = new CacheControl.Builder()
                    .maxAge(cacheTime, TimeUnit.SECONDS);

            return response.newBuilder()
                    .header("Cache-Control", builder.build().toString())
                    .removeHeader("Pragma")
                    .build();
        }
        //如果没有网络，不做处理，直接返回
        return chain.proceed(request);
    }

}

