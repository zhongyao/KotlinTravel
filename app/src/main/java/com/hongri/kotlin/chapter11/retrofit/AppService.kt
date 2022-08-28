package com.hongri.kotlin.chapter11.retrofit

import android.app.backup.BackupAgent
import okhttp3.CacheControl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * @author：hongri
 * @date：8/28/22
 * @description：
 */
interface AppService {

    @GET("get_data.json")
    fun getAppData(): Call<App>

    /**
     * 动态拼接path
     */
    @GET("{page}/get_data.json")
    fun getData(@Path("page") page: Int): Call<App>

    /**
     * 添加请求参数
     */
    @GET("get_data.json")
    fun getData(@Query("u") user: String, @Query("t") token: String): Call<App>

    /**
     * post请求 body
     */
    @POST("data/create")
    fun postData(@Body data: App): Call<ResponseBody>

    /**
     * 动态添加Header参数
     */
    @GET("get_data.json")
    fun getData2(@Header("User-Agent")userAgent: String, @Header("Cache-Control")cacheControl: String): Call<App>
}