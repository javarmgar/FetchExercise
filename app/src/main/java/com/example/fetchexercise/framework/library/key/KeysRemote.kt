package com.example.fetchexercise.framework.library.key

import com.example.fetchexercise.framework.library.manager.BuildTypes
import com.example.fetchexercise.framework.library.manager.EnvironmentManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeysRemote @Inject constructor(environmentManager: EnvironmentManager){

    //Base URL
    val BASE_URL_FETCH = when(environmentManager.environment) {
        BuildTypes.debug.name,
        BuildTypes.qa.name,
        BuildTypes.release.name -> "https://fetch-hiring.s3.amazonaws.com/"
        else                    -> "https://fetch-hiring.s3.amazonaws.com/"
    }

    //Query Params
    //None
    companion object{
        //URL paths
        const val FETCH_URL_HIRING = "hiring.json"
    }
}