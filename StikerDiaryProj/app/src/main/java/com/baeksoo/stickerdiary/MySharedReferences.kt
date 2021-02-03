package com.baeksoo.stickerdiary

import android.app.Application
import android.content.Context

class MySharedReferences : Application(){

    init {
        instance = this
    }

    companion object {
        lateinit var prefs: PreferenceUtil

        private var instance: MySharedReferences? = null
        fun ApplicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}