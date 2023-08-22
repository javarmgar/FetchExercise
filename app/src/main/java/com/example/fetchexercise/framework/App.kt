package com.example.fetchexercise.framework

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    val LOG_TAG:String = "LT_APP"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG,"-> onCreate() ")
    }

}