package com.example.network

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.example.network.model.Picture
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkService {

    private val mapper = jacksonObjectMapper()

    private var pictureList: List<Picture> =
        listOf(
            Picture(0, 0, "Title1", "url1", "url1"),
            Picture(0, 0, "Title2 kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", "url2", "url2"),
            Picture(0, 0, "Title3", "url3", "url3"))

    fun getPictures(): List<Picture> {
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

        Log.d(TAG, "$response")

        // Jackson from json to List
        pictureList = mapper.readValue(response, object : TypeReference<List<Picture>>(){})

        Log.d(TAG, "${pictureList.indices}")

        return pictureList
    }

    companion object {
        const val TAG = "NetwServLog"
    }
}