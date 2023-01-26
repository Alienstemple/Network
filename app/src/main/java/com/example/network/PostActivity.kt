package com.example.network

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.network.databinding.ActivityPostBinding
import com.example.network.model.Post
import com.example.network.model.PostId
import com.example.network.service.NetworkService

class PostActivity : AppCompatActivity() {
    private val backgroundHandler: Handler
    private val uiHandler =
        Handler(Looper.getMainLooper())  // Основной поток уже запущен. Возьмем от него looper

    private lateinit var sendedPost: Post
    private lateinit var receivedPost: PostId

    private val postPost = Runnable {
        receivedPost = NetworkService().postPost(sendedPost)
        uiHandler.post(updateUI)
    }

    private val updateUI = Runnable {
        updateResultTv()
    }

    init {
        val backgroundHandlerThread = HandlerThread("background")
        backgroundHandlerThread.start()    // Запустим фоновый поток
        backgroundHandler =
            Handler(backgroundHandlerThread.looper)   // В конструктор передадим looper запущенного фонового потока
    }

    private lateinit var binding: ActivityPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            post.setOnClickListener {
                sendedPost = Post(titleTv.text.toString(), bodyTv.text.toString(), 1)
                backgroundHandler.post(postPost)
            }
        }
    }

    private fun updateResultTv() {
        binding.resultPostTv.text = receivedPost.toString()
    }
}