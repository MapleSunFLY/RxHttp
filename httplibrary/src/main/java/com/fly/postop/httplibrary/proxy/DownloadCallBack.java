package com.fly.postop.httplibrary.proxy;

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
 * 描述: 下载回调接口
 */
public interface DownloadCallBack {

    /**
     * 失败回调
     *
     * @param errorMsg errorMsg
     */
    void onError(String errorMsg);


    /**
     * 成功回调
     *
     * @param filePath filePath
     */
    /**
     * 成功回调
     *
     * @param bytesRead     已经下载文件的大小
     * @param contentLength 文件的大小
     * @param progress      当前进度
     * @param filePath      文件路径
     */
    void onSuccess(long bytesRead, long contentLength, float progress, String filePath);

    /**
     * 下载完成
     *
     * @param bytesRead 已经下载文件的大小
     * @param contentLength 文件的大小
     * @param filePath 文件路径
     */
    void onCompleted(long bytesRead, long contentLength, String filePath);
}
