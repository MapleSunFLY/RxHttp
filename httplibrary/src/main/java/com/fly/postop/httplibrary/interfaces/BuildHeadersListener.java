package com.fly.postop.httplibrary.interfaces;

import java.util.Map;

import okhttp3.Request;

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
 * 描述: 公共请求头
 */
public interface BuildHeadersListener {
    Map<String, String> buildHeaders(Request request);
}
