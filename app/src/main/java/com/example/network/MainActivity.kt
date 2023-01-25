package com.example.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.network.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var httpClient: OkHttpClient

    private lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.getBtn.setOnClickListener {
            initOkhttpClient()
            initRequest()
            var responseBody: String = makeGetRequest()
            mainBinding.resultTv.text = responseBody
        }
    }

    private fun initOkhttpClient() {
        Log.d(TAG, "In init client")
        httpClient = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .cache(Cache(File(this.cacheDir, "http"), 10 * 1024 * 1024))
            .cookieJar(CookieJar.NO_COOKIES)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .build()

        Log.d(TAG, "Client is ready: ${httpClient}")
    }

    private fun initRequest() {
        request = Request.Builder()
            .url(requestUrl)
            .build()

        Log.d(TAG, "Request is ready: ${request}")
    }

    private fun makeGetRequest(): String {
        try {
            var resp: Response = httpClient.newCall(request).execute()
            return if (resp.isSuccessful) {
                resp.body?.string() ?: "No content"
            } else {
                "Response code: ${resp.code}"
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "Get failed!", e)
            return e.stackTrace.toString()
        }
    }

    companion object {
        const val TAG = "MainActLog"
        const val requestUrl = "https://jsonplaceholder.typicode.com/albums/1/photos"
    }
}