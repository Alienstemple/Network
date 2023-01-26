package com.example.network.service

import android.util.Log
import com.example.network.MainActivity
import com.example.network.model.Picture
import com.example.network.model.Post
import com.example.network.model.PostId
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkService {
    private val httpClient = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(3, TimeUnit.SECONDS)
        .cookieJar(CookieJar.NO_COOKIES)
        .addNetworkInterceptor(HttpLoggingInterceptor())
        .build()

    private val mapper = jacksonObjectMapper()
    private var pictureList: List<Picture> = emptyList()

    fun postPost(sendedPost: Post): PostId {
        val serializedPost = mapper.writeValueAsString(sendedPost)
        val request = Request.Builder()
            .method("POST", serializedPost.toRequestBody())
            .url(postUrl)
            .build()

        var response: String = try {
            var resp: Response = httpClient.newCall(request).execute()
            if (resp.isSuccessful) {
                resp.body?.string() ?: "No content"
            } else {
                "Response code: ${resp.code}"
            }
        } catch (e: java.lang.Exception) {
            Log.e(MainActivity.TAG, "Get failed!", e)
            e.stackTrace.toString()
        }

        Log.d(TAG, response)

        // Jackson from json to Post
        return mapper.readValue(response, PostId::class.java)
    }

    fun getPictures(): List<Picture> {

        val request = Request.Builder()
            .url(getUrl)
            .build()

        var response: String = try {
            var resp: Response = httpClient.newCall(request).execute()
            if (resp.isSuccessful) {
                resp.body?.string() ?: "No content"
            } else {
                "Response code: ${resp.code}"
            }
        } catch (e: java.lang.Exception) {
            Log.e(MainActivity.TAG, "Get failed!", e)
            e.stackTrace.toString()
        }

        Log.d(TAG, "$response")

        // Jackson from json to List
        pictureList = mapper.readValue(response, object : TypeReference<List<Picture>>() {})

        Log.d(TAG, "${pictureList.indices}")

        return pictureList
    }

    companion object {
        const val TAG = "NetwServLog"
        const val getUrl = "https://jsonplaceholder.typicode.com/albums/1/photos"
        const val postUrl = "https://jsonplaceholder.typicode.com/posts"
    }
}