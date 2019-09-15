package com.us.assignment.networking

import com.us.assignment.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {


    private var retrofitGoogleClient: Retrofit? = null


    val googlePlacesClient: Retrofit
        get() {
            if (retrofitGoogleClient == null) {

                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient.Builder()

                httpClient.addInterceptor(ConnectivityInterceptor())
                httpClient.connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(1200, TimeUnit.SECONDS)
                httpClient.addInterceptor(logging)
                retrofitGoogleClient = Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofitGoogleClient!!
        }
}