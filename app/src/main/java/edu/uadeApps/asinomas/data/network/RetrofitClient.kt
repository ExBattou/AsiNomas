package edu.uadeApps.asinomas.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val api: SwapiApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://swapi.info/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwapiApi::class.java)
    }
}