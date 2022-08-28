package com.hongri.kotlin.chapter11.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author：hongri
 * @date：8/28/22
 * @description：
 */
object ServiceCreator {
    //实际使用需要修改真实的baseUrl
    private const val BASE_URL = "http://10.3.2.3/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //上述方法可再优化【泛型实化】
    inline fun <reified T> create(): T = create(T::class.java)
}