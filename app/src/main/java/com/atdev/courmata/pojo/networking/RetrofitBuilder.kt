package com.ebada.embassy.pojo.networking

import com.ebada.embassy.pojo.networking.HttpClient.okHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    const val BASE_URL = "https://freecourses.azurewebsites.net/api/"
    val retrofitBuilder: Retrofit.Builder by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())

    }

    val apiService: ApiService by lazy {

        retrofitBuilder.build()
            .create(ApiService::class.java)
    }

}