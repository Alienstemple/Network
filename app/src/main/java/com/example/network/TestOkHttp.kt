package com.example.network

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

fun main() {

    val httpClient = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(3, TimeUnit.SECONDS)
        .cookieJar(CookieJar.NO_COOKIES)
        .addNetworkInterceptor(HttpLoggingInterceptor())
        .build()

    val request = Request.Builder()
        .url(MainActivity.requestUrl)
        .build()

    var response: String
    try {
        var resp: Response = httpClient.newCall(request).execute()
        if (resp.isSuccessful) {
            response = resp.body?.string() ?: "No content"
        } else {
            response = "Response code: ${resp.code}"
        }
    } catch (e: java.lang.Exception) {
        Log.e(MainActivity.TAG, "Get failed!", e)
        response = e.stackTrace.toString()
    }

    println(response)
}
