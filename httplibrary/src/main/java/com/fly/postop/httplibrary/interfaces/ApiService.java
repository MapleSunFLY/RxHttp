package com.fly.postop.httplibrary.interfaces;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

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
 * 包    名 : com.fly.postop.code.http.interf
 * 作    者 : FLY
 * 创建时间 : 2018/9/25
 * 描述: 公用api
 */
public interface ApiService {

    @GET
    Observable<String> doGet(@Url String url, @QueryMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    @FormUrlEncoded
    @POST
    Observable<String> doPost(@Url String url, @FieldMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    @Headers("Content-Type: application/json")
    @POST
    Observable<String> doPost(@Url String url, @Body Object object, @HeaderMap Map<String, String> headers);

    @PUT
    Observable<String> doPut(@Url String url, @QueryMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    @PUT
    Observable<String> doPut(@Url String url, @Body Object object, @HeaderMap Map<String, String> headers);

    @DELETE
    Observable<String> doDelete(@Url String url, @QueryMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    @DELETE
    Observable<String> doDelete(@Url String url, @Body Object object, @HeaderMap Map<String, String> headers);
}
