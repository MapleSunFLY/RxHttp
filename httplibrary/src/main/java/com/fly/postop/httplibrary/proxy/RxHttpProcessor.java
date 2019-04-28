package com.fly.postop.httplibrary.proxy;

import android.util.Log;

import com.fly.postop.httplibrary.RxHttpUtils;
import com.fly.postop.httplibrary.base.BaseObserver;
import com.fly.postop.httplibrary.download.DownloadObserver;
import com.fly.postop.httplibrary.interceptor.Transformer;
import com.fly.postop.httplibrary.interfaces.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

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
 * 描述: Rxhttp具体实现
 */
public class RxHttpProcessor implements IHttpProcessor {


    @Override
    public void doGet(String url, Map<String, String> params, Map<String, String> headers, final ICallBack callBack) {
        RxHttpUtils.createApi(ApiService.class)
                .doGet(url, params, headers)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new BaseObserver<String>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(String s) {
                        callBack.doOnError(s);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    @Override
    public void doPost(String url, Map<String, String> params, Map<String, String> headers, final ICallBack callBack) {
        RxHttpUtils.createApi(ApiService.class)
                .doPost(url, params, headers)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new BaseObserver<String>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(String s) {
                        callBack.doOnError(s);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    @Override
    public void doPost(String url, Object object, Map<String, String> headers, final ICallBack callBack) {
        RxHttpUtils.createApi(ApiService.class)
                .doPost(url, object, headers)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new BaseObserver<String>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(String s) {
                        callBack.doOnError(s);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    @Override
    public void doPut(String url, Map<String, String> params, Map<String, String> headers, final ICallBack callBack) {
        RxHttpUtils.createApi(ApiService.class)
                .doPut(url, params, headers)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new BaseObserver<String>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(String s) {
                        callBack.doOnError(s);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    @Override
    public void doPut(String url, Object object, Map<String, String> headers, final ICallBack callBack) {
        RxHttpUtils.createApi(ApiService.class)
                .doPut(url, object, headers)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new BaseObserver<String>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(String s) {
                        callBack.doOnError(s);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    @Override
    public void doDelete(String url, Map<String, String> params, Map<String, String> headers, final ICallBack callBack) {
        RxHttpUtils.createApi(ApiService.class)
                .doDelete(url, params, headers)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new BaseObserver<String>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(String s) {
                        callBack.doOnError(s);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    @Override
    public void doDelete(String url, Object object, Map<String, String> headers, final ICallBack callBack) {
        RxHttpUtils.createApi(ApiService.class)
                .doDelete(url, object, headers)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new BaseObserver<String>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(String s) {
                        callBack.doOnError(s);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    @Override
    public void downloadFile(String fileUrl, String fileName, String filePath, final DownloadCallBack callBack) {
        RxHttpUtils.downloadFile(fileUrl)
                .subscribe(new DownloadObserver(fileName, filePath) {
                    private String tag = "download";

                    //可以去下下载
                    @Override
                    protected String setTag() {
                        return tag;
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        callBack.onError(errorMsg);
                        RxHttpUtils.cancel(tag);
                    }

                    @Override
                    protected void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath) {
                        callBack.onSuccess(bytesRead, contentLength, progress, filePath);
                        if (done) {
                            callBack.onCompleted(bytesRead, contentLength, filePath);
                            RxHttpUtils.cancel(tag);
                        }
                    }
                });
    }

    @Override
    public void uploadFilesWithParams(String uploadUrl, String fileName, Map<String, Object> paramsMap, List<String> filePaths, final ICallBack callBack) {
        RxHttpUtils.uploadFilesWithParams(uploadUrl, fileName, paramsMap, filePaths)
                .compose(Transformer.<ResponseBody>switchSchedulers())
                .subscribe(new BaseObserver<ResponseBody>() {

                    private Disposable mDisposable;

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void doOnError(String errorMsg) {
                        callBack.doOnError(errorMsg);
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void doOnNext(ResponseBody responseBody) {
                        try {
                            String msg = responseBody.string();
                            callBack.doOnNext(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.doOnError("上传失败");
                        }

                    }

                    @Override
                    public void doOnCompleted() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }
}
