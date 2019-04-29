package com.fly.postop.httplibrary.config;

import android.content.Context;
import android.text.TextUtils;

import com.fly.postop.httplibrary.R;
import com.fly.postop.httplibrary.RxHttpUtils;
import com.fly.postop.httplibrary.cookie.CookieJarImpl;
import com.fly.postop.httplibrary.cookie.store.CookieStore;
import com.fly.postop.httplibrary.interfaces.BuildHeadersListener;
import com.fly.postop.httplibrary.interceptor.HeaderInterceptor;
import com.fly.postop.httplibrary.interceptor.NetCacheInterceptor;
import com.fly.postop.httplibrary.interceptor.NoNetCacheInterceptor;
import com.fly.postop.httplibrary.interceptor.RxHttpLogger;
import com.fly.postop.httplibrary.ssl.SSLUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

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
 * 包    名 : com.fly.postop.httplibrary.config
 * 作    者 : FLY
 * 创建时间 : 2019/4/26
 * 描述: 统一OkHttp配置信息
 */
public class OkHttpConfig {


    private static String defaultCachePath;

    private static OkHttpConfig instance;

    private static OkHttpClient.Builder okHttpClientBuilder;

    private OkHttpClient okHttpClient;

    public OkHttpConfig() {
        okHttpClientBuilder = new OkHttpClient.Builder();
    }

    public static OkHttpConfig getInstance() {

        if (instance == null) {
            synchronized (OkHttpConfig.class) {
                if (instance == null) {
                    instance = new OkHttpConfig();
                }
            }

        }
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = okHttpClientBuilder.build();
        }
        return okHttpClient;
    }

    public static class Builder {
        public Context context;
        private boolean isCache;
        private int cacheTime = 60;
        private int noNetCacheTime = 3600;
        private String cachePath;
        private long cacheMaxSize;
        private CookieStore cookieStore;
        private long readTimeout;
        private long writeTimeout;
        private long connectTimeout;
        private InputStream bksFile;
        private String password;
        private InputStream[] certificates;
        private Interceptor[] interceptors;
        private BuildHeadersListener buildHeadersListener;
        private HostnameVerifier hostnameVerifier;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 添加公共请求头
         *
         * @param buildHeadersListener
         * @return
         */
        public Builder setHeaders(BuildHeadersListener buildHeadersListener) {
            this.buildHeadersListener = buildHeadersListener;
            return this;
        }

        /**
         * 开启缓存策略(默认false)
         * 1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
         * 2、在没有网络的时候，去读缓存中的数据。
         *
         * @param isCache
         * @return
         */
        public Builder setCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        /**
         * 默认有网络时候缓存60秒
         *
         * @param cacheTime
         * @return
         */
        public Builder setHasNetCacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        /**
         * 设置无网络缓存时间 默认3600秒
         *
         * @param noNetCacheTime
         * @return
         */
        public Builder setNoNetCacheTime(int noNetCacheTime) {
            this.noNetCacheTime = noNetCacheTime;
            return this;
        }

        /**
         * 设置缓存地址
         *
         * @param cachePath
         * @return
         */
        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        /**
         * 设置缓存大小
         *
         * @param cacheMaxSize
         * @return
         */
        public Builder setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        /**
         * 全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
         * 不设置的话，默认不对cookie做处理
         *
         * @param cookieStore
         * @return
         */
        public Builder setCookieType(CookieStore cookieStore) {
            this.cookieStore = cookieStore;
            return this;
        }

        /**
         * 全局超时配置
         *
         * @param readTimeout
         * @return
         */
        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 全局超时配置
         *
         * @param writeTimeout
         * @return
         */
        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        /**
         * 全局超时配置
         *
         * @param connectTimeout
         * @return
         */
        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 添加自定义拦截器
         *
         * @param interceptors
         * @return
         */
        public Builder setAddInterceptor(Interceptor... interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        /**
         * 全局ssl证书认证
         * 1、信任所有证书,不安全有风险（默认信任所有证书）
         * .setSslSocketFactory()
         * 2、使用预埋证书，校验服务端证书（自签名证书）
         * .setSslSocketFactory(cerInputStream)
         *
         * @param certificates
         * @return
         */
        public Builder setSslSocketFactory(InputStream... certificates) {
            this.certificates = certificates;
            return this;
        }

        /**
         * 使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
         * 获取证书
         * InputStream cerInputStream = null;
         * InputStream bksInputStream = null;
         * try {
         * cerInputStream = getAssets().open("YourSSL.cer");
         * bksInputStream = getAssets().open("your.bks");
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         *
         * @param bksFile
         * @param password
         * @param certificates
         * @return
         */
        public Builder setSslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
            this.bksFile = bksFile;
            this.password = password;
            this.certificates = certificates;
            return this;
        }

        /**
         * 设置Hostname校验规则，默认实现返回true，需要时候传入相应校验规则即可
         *
         * @param hostnameVerifier
         * @return
         */
        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }


        public OkHttpClient build() {

            OkHttpConfig.getInstance();

            setCookieConfig();
            setCacheConfig();
            setHeadersConfig();
            setSslConfig();
            setHostnameVerifier();
            addInterceptors();
            setTimeout();
            setDebugConfig();

            return okHttpClientBuilder.build();
        }

        private void addInterceptors() {
            if (null != interceptors && interceptors.length > 0) {
                for (Interceptor interceptor : interceptors) {
                    okHttpClientBuilder.addInterceptor(interceptor);
                }
            }
        }

        /**
         * 配置开发环境
         */
        private void setDebugConfig() {
            if (RxHttpUtils.getInstance().getIsDebug()) {
                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new RxHttpLogger());
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(logInterceptor);
            }
        }


        /**
         * 配置headers
         */
        private void setHeadersConfig() {
            if (buildHeadersListener != null) {
                okHttpClientBuilder.addInterceptor(new HeaderInterceptor() {
                    @Override
                    public Map<String, String> buildHeaders(Request request) {
                        return buildHeadersListener.buildHeaders(request);
                    }
                });
            }

        }

        /**
         * 配饰cookie保存到sp文件中
         */
        private void setCookieConfig() {
            if (null != cookieStore) {
                okHttpClientBuilder.cookieJar(new CookieJarImpl(cookieStore));
            }
        }

        /**
         * 配置缓存
         */
        private void setCacheConfig() {
            File externalCacheDir = context.getExternalCacheDir();
            if (null == externalCacheDir) {
                return;
            }
            defaultCachePath = externalCacheDir.getPath() + "/RxHttpCacheData";
            if (isCache) {
                Cache cache;
                if (!TextUtils.isEmpty(cachePath) && cacheMaxSize > 0) {
                    cache = new Cache(new File(cachePath), cacheMaxSize);
                } else {
                    long defaultCacheSize = 1024 * 1024 * 100;
                    cache = new Cache(new File(defaultCachePath), defaultCacheSize);
                }

                okHttpClientBuilder
                        .cache(cache)
                        .addInterceptor(new NoNetCacheInterceptor(noNetCacheTime))
                        .addNetworkInterceptor(new NetCacheInterceptor(cacheTime));
            }
        }

        /**
         * 配置超时信息
         */
        private void setTimeout() {
            okHttpClientBuilder.readTimeout(readTimeout <= 0 ? 30 : readTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(writeTimeout <= 0 ? 30 : writeTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.connectTimeout(connectTimeout <= 0 ? 30 : connectTimeout, TimeUnit.SECONDS);
            okHttpClientBuilder.retryOnConnectionFailure(true);
        }

        /**
         * 配置证书
         */
        private void setSslConfig() {
            SSLUtils.SSLParams sslParams = null;

            if (null == certificates) {
                //信任所有证书,不安全有风险
                sslParams = SSLUtils.getSslSocketFactory();
            } else {
                if (null != bksFile && !TextUtils.isEmpty(password)) {
                    //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
                } else {
                    //使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(certificates);
                }
            }

            okHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        }

        private void setHostnameVerifier() {
            if (null == hostnameVerifier) {
                okHttpClientBuilder.hostnameVerifier(SSLUtils.UnSafeHostnameVerifier);
            } else {
                okHttpClientBuilder.hostnameVerifier(hostnameVerifier);
            }
        }
    }
}
