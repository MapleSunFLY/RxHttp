package com.fly.postop.httplibrary.sign;

import android.text.TextUtils;
import android.util.Base64;

import com.fly.postop.httplibrary.R;
import com.fly.postop.httplibrary.RxHttpUtils;
import com.shangyi.android.utils.NetUtils;
import com.shangyi.android.utils.SPUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Request;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.postop.code.http.sign
 * 作    者 : FLY
 * 创建时间 : 2018/9/26
 * 描述: 请求头获取
 */
public class HttpSignHeader {

    public static final String USER_TOKEN = "LoginUserToken";

    /**
     * 签名算法HmacSha256
     */
    public static final String HMAC_SHA256 = "HmacSHA256";
    /**
     * 编码UTF-8
     */
    public static final String ENCODING_UTF8 = "UTF-8";
    /**
     * 换行符（Line Break）
     */
    public static final String SEPARATOR_LB = "\n";
    /**
     * 分隔符冒号（':' symbol）
     */
    public static final String SEPARATOR_SPE1 = ":";

    public static final String X_CA_SIGNATURE = "X-Ca-Signature";
    public static final String X_CA_DEVICE_TOKEN = "X-Ca-Device-Token";
    public static String X_CA_TOKEN = "X-Ca-Token";
    public static String X_CA_NONCE = "X-Ca-Nonce";
    private static String X_CA_TIMESTAMP = "X-Ca-Timestamp";
    private static String X_CA_STAGE = "X-Ca-Stage";
    private static String X_CA_APP_MARKET = "X-Ca-App-Market";
    private static String X_CA_APP_VERSION = "X-Ca-App-Version";
    private static String X_CA_NETWORK = "X-Ca-Network";
    private static String X_CA_APP = "X-Ca-App";
    private static String X_CA_OS = "X-Ca-OS";

    public static String PRIVATESIGNKEY = "AppSignature_2018";

    /**
     * 需要加入签名的HTTP头
     */
    private static final String[] SIGNATURE_HEADERS = {
            X_CA_TIMESTAMP,
            X_CA_NONCE,
            X_CA_STAGE,
            X_CA_APP_MARKET,
            X_CA_APP_VERSION,
            X_CA_NETWORK,
            X_CA_DEVICE_TOKEN,
            X_CA_TOKEN
    };

    public static HashMap<String, String> getBaseHeaders(Request request) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(X_CA_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        headers.put(X_CA_STAGE, RxHttpUtils.getInstance().getContext().getString(R.string.http_type));
        headers.put(X_CA_APP_MARKET, DeviceInfoConfig.getInstance().getChannelName(RxHttpUtils.getInstance().getContext()));
        headers.put(X_CA_APP_VERSION, DeviceInfoConfig.getInstance().getVersionName(RxHttpUtils.getInstance().getContext()));
        headers.put(X_CA_NETWORK, NetUtils.networkType(RxHttpUtils.getInstance().getContext()));
        headers.put(X_CA_APP, String.valueOf(DeviceInfoConfig.getInstance().appType));//0医生端，1患者端
        headers.put(X_CA_OS, DeviceInfoConfig.getInstance().OS_TYPE_ANDROID);
        headers.put(HttpSignHeader.X_CA_NONCE, UUID.randomUUID().toString());
        headers.put(HttpSignHeader.X_CA_DEVICE_TOKEN,  UUID.randomUUID().toString());
        headers.put(HttpSignHeader.X_CA_TOKEN, SPUtils.get(USER_TOKEN, ""));
        String sign = HttpSignHeader.sign(request.method().toUpperCase(), request.url().uri().getPath(), request.url().query(), headers);
        headers.put(HttpSignHeader.X_CA_SIGNATURE, sign);
        return headers;
    }

    /**
     * 请求参数排序 照key值升序排列
     *
     * @param method
     * @param uri
     * @param queryString
     * @param headers
     * @return
     */
    public static String sign(String method, String uri, String queryString,
                              Map<String, String> headers) {
        if (!TextUtils.isEmpty(queryString)) {
            TreeMap<String, String> param_map = new TreeMap<String, String>();
            String[] param_pairs = queryString.split("&");
            for (String pair : param_pairs) {
                String[] param = pair.split("=");
                if (param.length != 2) {
                    if (param.length > 0) param_map.put(param[0], "");
                    continue;
                }
                param_map.put(param[0], param[1]);
            }
            StringBuilder sign = new StringBuilder();
            Iterator it = param_map.keySet().iterator();
            //拼接参数
            while (it.hasNext()) {
                String key = it.next().toString();
                String value = param_map.get(key).toString();
                sign.append(key).append("=").append(value).append("&");
            }
            queryString = sign.deleteCharAt(sign.length() - 1).toString();
        }
        return sign(PRIVATESIGNKEY, method, uri, queryString, headers);
    }

    /**
     * 根据密钥、请求方法、URI、RUL参数、请求头Headers进行签名
     *
     * @param secret      签名密钥
     * @param method      请求方法
     * @param uri         请求的URI
     * @param queryString URL参数
     * @param headers     用于计算签名的请求头
     * @return String 签名后的字符串
     */
    public static String sign(String secret, String method, String uri, String queryString,
                              Map<String, String> headers) {
        String headersString = buildHeaders(headers);
        if (TextUtils.isEmpty(queryString)) {
            queryString = "";
        }
        try {
            byte[] toSignByte = buildStringToSign(method, uri, queryString, headersString).getBytes(ENCODING_UTF8);
            Mac hmacSha256 = initHmacSha256(secret);
            byte[] encodeByte = Base64.encode(hmacSha256.doFinal(toSignByte), Base64.NO_WRAP);
            return new String(encodeByte, ENCODING_UTF8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建待签名的HTTP头字符串
     *
     * @param headers HTTP请求中所有的Header
     * @return 待签名的请求头字符串
     */
    private static String buildHeaders(Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();
        for (String header : SIGNATURE_HEADERS) {
            sb.append(header);
            sb.append(SEPARATOR_SPE1);
            String headerValue = headers.get(header);
            if (TextUtils.isEmpty(headerValue)) {
                sb.append("");
            } else {
                sb.append(headerValue);
            }
            sb.append(SEPARATOR_LB);
        }
        return sb.toString();
    }

    /**
     * 构建待签名的字符串
     *
     * @param method        请求的方法
     * @param uri           请求的URI
     * @param queryString   URL后面的参数
     * @param headersString 拼接好的请求头字符串
     * @return String 待签名的字符串
     */
    private static String buildStringToSign(String method, String uri, String queryString,
                                            String headersString) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.toUpperCase()).append(SEPARATOR_LB);
        sb.append(uri).append(SEPARATOR_LB);
        sb.append(queryString).append(SEPARATOR_LB);
        sb.append(headersString);
        return sb.toString();
    }

    /**
     * 用给定的密钥初始化HmacSha256
     *
     * @param secret 密钥字符串
     * @return Mac对象
     */
    private static Mac initHmacSha256(String secret) {
        try {
            Mac hmacSha256 = Mac.getInstance(HMAC_SHA256);
            byte[] keyBytes = secret.getBytes(ENCODING_UTF8);
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, HMAC_SHA256));
            return hmacSha256;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
