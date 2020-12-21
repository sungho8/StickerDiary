package com.baeksoo.stickerdiary

import android.app.Application

class MySharedReferences : Application(){
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}