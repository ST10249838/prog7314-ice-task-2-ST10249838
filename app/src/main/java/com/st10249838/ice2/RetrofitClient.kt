package com.st10249838.ice2

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * A singleton object responsible for creating and configuring a Retrofit instance.
 */
object RetrofitClient {
    // The base URL of the API. All endpoint paths will be appended to this.
    private const val BASE_URL = "https://cargpt.onrender.com/"

    // An interceptor that logs network request and response details to Logcat.
    // This is very useful for debugging.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // The HTTP client used by Retrofit. Here we configure timeouts and add the logging interceptor.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS) // Time to wait for a connection to be established.
        .readTimeout(60, TimeUnit.SECONDS)    // Time to wait for data to be read from the server.
        .writeTimeout(60, TimeUnit.SECONDS)   // Time to wait for data to be written to the server.
        .build()

    /**
     * Lazily creates and provides a singleton instance of the ApiService.
     * The Retrofit instance is only created the first time it's accessed.
     */
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            // GsonConverterFactory is used to serialize/deserialize JSON data.
            .addConverterFactory(GsonConverterFactory.create())
            // Set the custom OkHttpClient.
            .client(okHttpClient)
            .build()
        // Create an implementation of the ApiService interface.
        retrofit.create(ApiService::class.java)
    }
}