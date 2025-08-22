package com.project.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val loginApi: Api by lazy {
        instance.create(Api::class.java)
    }

    val imageUpload: Api by lazy {
        instance.create(Api::class.java)
    }

    val registrationApi: RegistrationApi by lazy {
        instance.create(RegistrationApi::class.java)
    }

    val postApi: Api by lazy {
        instance.create(Api::class.java)
    }
}