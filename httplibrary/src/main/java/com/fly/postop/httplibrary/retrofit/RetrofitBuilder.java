package com.fly.postop.httplibrary.retrofit;

import com.fly.postop.httplibrary.RxHttpUtils;
import com.fly.postop.httplibrary.config.OkHttpConfig;
import com.fly.postop.httplibrary.cookie.store.SPCookieStore;
import com.fly.postop.httplibrary.gson.GsonAdapter;
import com.fly.postop.httplibrary.interfaces.BuildHeadersListener;
import com.fly.postop.httplibrary.sign.HttpSignHeader;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
 * 包    名 : com.fly.postop.httplibrary.retrofit
 * 作    者 : FLY
 * 创建时间 : 2019/4/26
 * 描述: Retrofit 创建 并 初始化
 */
public class RetrofitBuilder {

    private String baseUrl;

    private CallAdapter.Factory[] callAdapterFactory;

    private Converter.Factory[] converterFactory;

    private OkHttpClient okHttpClient;

    public RetrofitBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitBuilder setCallAdapterFactory(CallAdapter.Factory... callAdapterFactory) {
        this.callAdapterFactory = callAdapterFactory;
        return this;
    }

    public RetrofitBuilder setConverterFactory(Converter.Factory... converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public RetrofitBuilder setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public Retrofit build() {
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(baseUrl);

        if (callAdapterFactory == null || callAdapterFactory.length <= 0) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        } else {
            for (CallAdapter.Factory factory : callAdapterFactory) {
                builder.addCallAdapterFactory(factory);
            }
        }

        if (converterFactory == null || converterFactory.length <= 0) {
            builder.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()));
        } else {
            for (Converter.Factory factory : converterFactory) {
                builder.addConverterFactory(factory);
            }
        }

        if (okHttpClient == null) {
            builder.client(createOkHttpClient());
        } else {
            builder.client(okHttpClient);
        }

        return builder.build();
    }

    private OkHttpClient createOkHttpClient() {

        OkHttpClient okHttpClient = new OkHttpConfig.Builder(RxHttpUtils.getInstance().getContext())
                .setCache(true)
                .setCookieType(new SPCookieStore(RxHttpUtils.getInstance().getContext()))
                .setHeaders(new BuildHeadersListener() {
                    @Override
                    public Map<String, String> buildHeaders(Request request) {
                        return HttpSignHeader.getBaseHeaders(request);
                    }
                })
                .build();
        return okHttpClient;
    }


}
