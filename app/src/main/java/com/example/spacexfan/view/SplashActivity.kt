package com.example.spacexfan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.spacexfan.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
        splash.startAnimation(animation)

        @Suppress("DEPRECATION") val mHandler = Handler()
        val monitor = Runnable {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        mHandler.postDelayed(monitor, 1200)

    }
}