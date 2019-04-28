package com.fly.postop.httplibrary.proxy;

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
 * 描述: 网络请求代理接口
 */
public interface IHttpProcessor {

    void doGet(String url, Map<String, String> params, Map<String, String> headers, ICallBack callBack);

    void doPost(String url, Map<String, String> params, Map<String, String> headers, ICallBack callBack);

    void doPost(String url, Object object, Map<String, String> headers, ICallBack callBack);

    void doPut(String url, Map<String, String> params, Map<String, String> headers, ICallBack callBack);

    void doPut(String url, Object object, Map<String, String> headers, ICallBack callBack);

    void doDelete(String url, Map<String, String> params, Map<String, String> headers, ICallBack callBack);

    void doDelete(String url, Object object, Map<String, String> headers, ICallBack callBack);

    void downloadFile(String fileUrl, String fileName ,String filePath, DownloadCallBack callBack);

    void uploadFilesWithParams(String uploadUrl, String fileName, Map<String, Object> paramsMap, List<String> filePaths, ICallBack callBack);
}
