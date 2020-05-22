package com.ebada.embassy.pojo.networking

import android.util.Log
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.util.concurrent.TimeUnit

object HttpClient {

    val okHttpClient: OkHttpClient by lazy {

        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interpetor)
            .addInterceptor(interceptorLOG)
            .build()
        //
    }

    val interceptorLOG: HttpLoggingInterceptor by lazy {

        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
            Log.d("BoDY::", this.level.toString())

        }
    }


    val interpetor: Interceptor by lazy {


        object : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request? = null
                try {
                    //..
                    val url = chain.request().url

                    val httpUrl = url.newBuilder()
                        //.addQueryParameter("appid", RetrofitBuilder.API_KEY)
                        .build()

                    val requestBuilder = chain.request().newBuilder().url(httpUrl)
                    requestBuilder.addHeader("Content-Type", "application/json")
                    request = requestBuilder.build()
                    return chain.proceed(request)

                } catch (ex: Exception) {
                    Log.d("okhttp>:", "May Time Out:" + ex.message)
                    //Toast.makeText(con,"",Toast.LENGTH_LONG).show()
                }
                return chain.proceed(request!!)
            }

        }

    }//....
}