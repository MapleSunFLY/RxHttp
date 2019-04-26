package com.fly.postop.httplibrary.interceptor;

import android.util.Log;

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
 * 包    名 : com.fly.postop.httplibrary.interceptor
 * 作    者 : FLY
 * 创建时间 : 2019/4/26
 * 描述: 日志打印格式化处理
 */
public class RxHttpLogger implements HttpLoggingInterceptor.Logger {
    private StringBuffer mMessage = new StringBuffer();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        if (message.startsWith("--> GET")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            //TODO json 格式化 记得修改
            //message = JsoU.formatJson(message);
        }
        mMessage.append(message.concat("\n"));
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Log.e("RxHttpUtils", mMessage.toString());
        }
    }
}
