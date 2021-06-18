package com.example.spacexfan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
        findViewById<TextView>(R.id.splash_text).startAnimation(animation)

        val mHandler = Handler()
        val monitor: Runnable = object : Runnable{
            override fun run() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

        mHandler.postDelayed(monitor, 2500)

    }
}