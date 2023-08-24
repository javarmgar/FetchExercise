package com.example.fetchexercise.modules.base

import com.example.fetchexercise.framework.dataimpl.remote.api.service.FetchServiceDef
import com.example.fetchexercise.framework.dataimpl.remote.conf.HeadersEmpty
import com.example.fetchexercise.framework.library.key.KeysRemote
import com.example.fetchexercise.framework.library.manager.EnvironmentManager
import com.example.fetchexercise.framework.library.manager.WebServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Headers

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val LOG_TAG: String = "LT_NetworkModule"

    @Provides
    @Singleton
    fun provideFetchService(
        webServiceManager: WebServiceManager,
        environmentManager: EnvironmentManager,
        keysRemote: KeysRemote,
        @HeadersEmpty headers: Headers
    ): FetchServiceDef = webServiceManager.getWebService(
        environmentManager.isDebug,
        keysRemote.BASE_URL_FETCH,
        headers,
        FetchServiceDef::class.java
    )
}