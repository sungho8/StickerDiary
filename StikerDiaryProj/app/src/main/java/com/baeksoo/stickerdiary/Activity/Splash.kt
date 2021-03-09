package com.baeksoo.stickerdiary.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.baeksoo.stickerdiary.LoginActivity
import com.baeksoo.stickerdiary.MainActivity
import com.baeksoo.stickerdiary.MySharedReferences
import com.baeksoo.stickerdiary.R

class Splash : AppCompatActivity() {

    val SPLASH_VIEW_TIME: Long = 300 //(ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MySharedReferences.prefs.getThemeId())
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   // 상태바
        setContentView(R.layout.splash)

        Handler().postDelayed({ //delay를 위한 handler
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }
}