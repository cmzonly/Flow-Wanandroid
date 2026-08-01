package com.czwd.flow_wanandroid.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://www.wanandroid.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    @PublishedApi   //@PublishedApi：告诉编译器"这个成员虽然标记为 internal，但允许被 inline 函数访问"
    internal val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    inline fun <reified T> createService(): T {
        //如果retrofit属性使用private不使用@PublishedApi注解和internal关键字标识,此处调用
        //retrofit属性会报错,因为内联函数无法调用私有成员变量,为什么呢,因为内联函数会在被调用的
        //地方获取到内联函数中的代码,比如retrofit,kotlin依然保证其封装性,所以会报错,提示无法访问
        return retrofit.create(T::class.java)
    }

}