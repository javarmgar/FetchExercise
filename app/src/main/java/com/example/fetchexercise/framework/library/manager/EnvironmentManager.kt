package com.example.fetchexercise.framework.library.manager

import android.util.Log
import com.example.fetchexercise.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnvironmentManager @Inject constructor(){
    private val LOG_TAG: String = "LT_Environment_Manager"
    val environment: String = when(BuildConfig.BUILD_TYPE){
        BuildTypes.debug.name,
        BuildTypes.qa.name,
        BuildTypes.release.name -> BuildConfig.BUILD_TYPE
        else -> {
            Log.d(LOG_TAG, "EnvironmentManager environment attr: ${BuildConfig.BUILD_TYPE} is not a valid env setting default: debug")
            BuildTypes.debug.name
        }
    }

    val isDev:Boolean = BuildTypes.debug.name == environment
    val isQA:Boolean = BuildTypes.qa.name == environment
    val isProd:Boolean = BuildTypes.release.name == environment
    val isDebug:Boolean = isDev || isQA
}

enum class BuildTypes{
    debug,
    qa,
    release
}