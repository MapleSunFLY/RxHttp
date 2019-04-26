package com.fly.postop.httplibrary.download;

import com.fly.postop.httplibrary.factory.ApiServiceFactory;
import com.fly.postop.httplibrary.interceptor.Transformer;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
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
 * 包    名 : com.fly.postop.httplibrary.download
 * 作    者 : FLY
 * 创建时间 : 2019/4/26
 * 描述: 为下载单独建一个retrofit
 */
public class DownloadHelper {
    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return ApiServiceFactory.getInstance()
                .setOkClient(new OkHttpClient.Builder().addInterceptor(new DownloadInterceptor()).build())
                .createApi(DownloadApi.class)
                .downloadFile(fileUrl)
                .compose(Transformer.<ResponseBody>switchSchedulers());
    }
}