package com.czwd.flow_wanandroid.network

import android.os.Handler
import android.os.Looper
import com.blankj.utilcode.util.ToastUtils
import com.czwd.flow_wanandroid.utils.GlobalViewModel
import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://www.wanandroid.com/"
    private const val CODE_NOT_LOGIN = -1001

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val cookieStore = ConcurrentHashMap<String, MutableList<Cookie>>()

    private val cookieJar = object : CookieJar {
        override fun saveFromResponse(url: HttpUrl, cookies: List<okhttp3.Cookie>) {
            CookieDataStoreManager.saveCookies(url, cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<okhttp3.Cookie> {
            return CookieDataStoreManager.loadCookies(url)
        }
    }



    private val authInterceptor = Interceptor{chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        val bodyString = response.peekBody(Long.MAX_VALUE).string()
        val gson = Gson()
        try {
            val baseResponse = gson.fromJson(bodyString, RawResponse::class.java)
            if (baseResponse.errorCode == CODE_NOT_LOGIN) {
                Handler(Looper.getMainLooper()).post {
                    ToastUtils.showLong("登录已过期，请重新登录")
                    // TODO: 跳转登录页，例如：
                    // val intent = Intent(FlowApplication.context, LoginActivity::class.java)
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    // FlowApplication.context.startActivity(intent)
                    //使用navigation跳转到登录页面
                    GlobalViewModel.notLogin()


                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        response
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
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

    private class RawResponse {
        val errorCode: Int = 0
        val errorMsg: String? = null
    }

}