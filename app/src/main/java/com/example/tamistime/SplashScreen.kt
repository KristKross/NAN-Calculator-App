package com.example.tamistime

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        android.os.Handler().postDelayed({
            val titleText = findViewById<TextView>(R.id.titleText)
            fadeIn(titleText)
        }, 1000L)

        android.os.Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}

private fun fadeIn(view: View) {
    val fadeIn = AlphaAnimation(0.0f, 1.0f)
    fadeIn.duration = 1000
    view.startAnimation(fadeIn)
    view.visibility = View.VISIBLE
}