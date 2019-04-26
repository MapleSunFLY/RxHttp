package com.fly.postop.httplibrary.factory;

import com.fly.postop.httplibrary.retrofit.RetrofitBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

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
 * 包    名 : com.fly.postop.httplibrary.factory
 * 作    者 : FLY
 * 创建时间 : 2019/4/26
 * 描述:
 */
public class ApiServiceFactory {

    private volatile static ApiServiceFactory instance;

    /**
     * 缓存retrofit针对同一个域名下相同的ApiService不会重复创建retrofit对象
     */
    private static HashMap<String, Object> apiServiceCache;

    private String baseUrl;

    private CallAdapter.Factory[] callAdapterFactory;

    private Converter.Factory[] converterFactory;

    private OkHttpClient okHttpClient;

    public static ApiServiceFactory getInstance() {
        if (instance == null) {
            synchronized (ApiServiceFactory.class) {
                if (instance == null) {
                    instance = new ApiServiceFactory();
                }
            }
        }
        return instance;
    }

    private ApiServiceFactory() {
        apiServiceCache = new HashMap<>();
    }

    /**
     * 清空所有api缓存（用于切换环境时候使用）
     */
    public void clearAllApi() {
        apiServiceCache.clear();
    }

    public ApiServiceFactory setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ApiServiceFactory setCallAdapterFactory(CallAdapter.Factory... callAdapterFactory) {
        this.callAdapterFactory = callAdapterFactory;
        return this;
    }

    public ApiServiceFactory setConverterFactory(Converter.Factory... converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public ApiServiceFactory setOkClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public <A> A createApi(Class<A> apiClass) {
        A api = (A) apiServiceCache.get(apiClass.getCanonicalName());
        if (api == null) {
            Retrofit retrofit = new RetrofitBuilder()
                    .setBaseUrl(baseUrl)
                    .setCallAdapterFactory(callAdapterFactory)
                    .setConverterFactory(converterFactory)
                    .setOkHttpClient(okHttpClient)
                    .build();

            api = retrofit.create(apiClass);
            apiServiceCache.put(apiClass.getCanonicalName(), api);
        }

        return api;
    }

}
