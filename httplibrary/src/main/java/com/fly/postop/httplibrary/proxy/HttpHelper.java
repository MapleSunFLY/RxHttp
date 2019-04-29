package com.fly.postop.httplibrary.proxy;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.fly.postop.httplibrary.interfaces.RestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * 包    名 : com.fly.postop.httplibrary.proxy
 * 作    者 : FLY
 * 创建时间 : 2019/4/28
 * 描述:请求帮助类，连接插件实现 ,隔离了框架与使用
 */
public class HttpHelper implements IHttpProcessor {
    private static HttpHelper instance;
    private static IHttpProcessor mIHttpProcessor;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        synchronized (HttpHelper.class) {
            if (instance == null) {
                instance = new HttpHelper();
                //更换框架，更换实践
                mIHttpProcessor = new RxHttpProcessor();
            }
        }
        return instance;
    }

    public void doHttp(String url, String restMethod, ICallBack callBack) {
        this.doHttp(url, restMethod, null, null, null, callBack);
    }

    public void doHttp(String url, String restMethod, Object object, ICallBack callBack) {
        this.doHttp(url, restMethod, null, object, null, callBack);
    }

    public void doHttp(String url, String restMethod, Map<String, Object> params, ICallBack callBack) {
        this.doHttp(url, restMethod, params, null, null, callBack);
    }

    public void doHttp(String url, String restMethod, Object object, Map<String, String> headers, ICallBack callBack) {
        this.doHttp(url, restMethod, null, object, headers, callBack);
    }

    public void doHttp(String url, String restMethod, Map<String, Object> params, Map<String, String> headers, ICallBack callBack) {
        this.doHttp(url, restMethod, params, null, headers, callBack);
    }

    public void doHttp(String url, String restMethod, Map<String, Object> params, Object object, Map<String, String> headers, ICallBack callBack) {
        if (RestMethod.POST.equalsIgnoreCase(restMethod)) {
            if (object != null) this.doPost(url, object, headers, callBack);
            this.doPost(url, params, headers, callBack);
        } else if (RestMethod.PUT.equalsIgnoreCase(restMethod)) {
            if (object != null) this.doPut(url, object, headers, callBack);
            this.doPut(url, params, headers, callBack);
        } else if (RestMethod.DELETE.equalsIgnoreCase(restMethod)) {
            if (object != null) this.doDelete(url, object, headers, callBack);
            this.doDelete(url, params, headers, callBack);
        } else {
            this.doGet(url, params, headers, callBack);
        }
    }

    public void doGet(String url, ICallBack callBack) {
        this.doDelete(url, null, callBack);
    }

    public void doGet(String url, Map<String, Object> params, ICallBack callBack) {
        this.doDelete(url, null, callBack);
    }


    @Override
    public void doGet(String url, Map<String, Object> params, Map<String, String> headers, ICallBack callBack) {
        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
            url = url.substring(1);
        }

        if (params == null) params = new ArrayMap<>();

        if (headers == null) headers = new ArrayMap<>();

        mIHttpProcessor.doGet(url, params, headers, callBack);
    }

    public void doPost(String url, ICallBack callBack) {
        this.doDelete(url, null, callBack);
    }

    public void doPost(String url, Map<String, Object> params, ICallBack callBack) {
        this.doDelete(url, params, null, callBack);
    }

    public void doPost(String url, Object object, ICallBack callBack) {
        this.doDelete(url, object, null, callBack);
    }

    @Override
    public void doPost(String url, Map<String, Object> params, Map<String, String> headers, ICallBack callBack) {
        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
            url = url.substring(1);
        }

        if (params == null) params = new ArrayMap<>();

        if (headers == null) headers = new ArrayMap<>();

        mIHttpProcessor.doPost(url, params, headers, callBack);
    }

    @Override
    public void doPost(String url, Object object, Map<String, String> headers, ICallBack callBack) {
        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
            url = url.substring(1);
        }

        if (object == null) object = new Object();

        if (headers == null) headers = new ArrayMap<>();

        mIHttpProcessor.doPost(url, object, headers, callBack);
    }

    public void doPut(String url, ICallBack callBack) {
        this.doDelete(url, null, callBack);
    }

    public void doPut(String url, Map<String, Object> params, ICallBack callBack) {
        this.doDelete(url, params, null, callBack);
    }

    public void doPut(String url, Object object, ICallBack callBack) {
        this.doDelete(url, object, null, callBack);
    }

    @Override
    public void doPut(String url, Map<String, Object> params, Map<String, String> headers, ICallBack callBack) {
        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
            url = url.substring(1);
        }

        if (params == null) params = new ArrayMap<>();

        if (headers == null) headers = new ArrayMap<>();

        mIHttpProcessor.doPut(url, params, headers, callBack);
    }

    @Override
    public void doPut(String url, Object object, Map<String, String> headers, ICallBack callBack) {
        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
            url = url.substring(1);
        }

        if (object == null) object = new Object();

        if (headers == null) headers = new ArrayMap<>();

        mIHttpProcessor.doPut(url, object, headers, callBack);
    }

    public void doDelete(String url, ICallBack callBack) {
        this.doDelete(url, null, callBack);
    }

    public void doDelete(String url, Map<String, Object> params, ICallBack callBack) {
        this.doDelete(url, params, null, callBack);
    }

    public void doDelete(String url, Object object, ICallBack callBack) {
        this.doDelete(url, object, null, callBack);
    }

    @Override
    public void doDelete(String url, Map<String, Object> params, Map<String, String> headers, ICallBack callBack) {
        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
            url = url.substring(1);
        }

        if (params == null) params = new ArrayMap<>();

        if (headers == null) headers = new ArrayMap<>();

        mIHttpProcessor.doDelete(url, params, headers, callBack);
    }

    @Override
    public void doDelete(String url, Object object, Map<String, String> headers, ICallBack callBack) {
        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
            url = url.substring(1);
        }

        if (object == null) object = new Object();

        if (headers == null) headers = new ArrayMap<>();

        mIHttpProcessor.doDelete(url, object, headers, callBack);
    }

    @Override
    public void downloadFile(String fileUrl, String fileName, String filePath, DownloadCallBack callBack) {
        mIHttpProcessor.downloadFile(fileUrl, fileName, filePath, callBack);
    }


    public void uploadFile(String uploadUrl, String fileName, String filePath, ICallBack callBack) {
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        this.uploadFiles(uploadUrl, fileName, filePaths, callBack);
    }


    public void uploadFiles(String uploadUrl, String fileName, List<String> filePaths, ICallBack callBack) {
        this.uploadFilesWithParams(uploadUrl, fileName, null, filePaths, callBack);
    }

    @Override
    public void uploadFilesWithParams(String uploadUrl, String fileName, Map<String, Object> paramsMap, List<String> filePaths, ICallBack callBack) {
        mIHttpProcessor.uploadFilesWithParams(uploadUrl, fileName, paramsMap, filePaths, callBack);
    }
}
