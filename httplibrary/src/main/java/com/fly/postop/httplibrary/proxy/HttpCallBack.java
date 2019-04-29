package com.fly.postop.httplibrary.proxy;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shangyi.android.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
 * 描述: 解析数据
 */
public abstract class HttpCallBack<Result> implements ICallBack {

    /**
     * 是否隐藏toast
     *
     * @return
     */
    protected boolean isHideToast() {
        return false;
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(String result) {
        //将网络访问框架得到的数据解析
        Gson gson = new Gson();
        //获取HttpCallBack后面的泛型
        Type type = analysisClassInfo(this);
        Result objResult = gson.fromJson(result, type);
        onSuccess(objResult);
    }

    private Type analysisClassInfo(Object obj) {
        //相当于可以得到参数化内型，类型变量，基础类型
        Type genType = obj.getClass().getGenericSuperclass();
        Type[] actualType = ((ParameterizedType) genType).getActualTypeArguments();
        return actualType[0];
    }

    public abstract void onSuccess(Result objResult);


    public abstract void onError(String errorMsg);


}
