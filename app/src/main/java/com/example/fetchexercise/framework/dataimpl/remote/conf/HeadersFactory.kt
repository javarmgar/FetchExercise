package com.example.fetchexercise.framework.dataimpl.remote.conf

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeadersFactory {

    @HeadersBasic
    @Provides
    @Singleton
    fun getBaseHeaders(): Headers = Headers.Builder().run {
        add("Content-Type", "application/json")
        add("Accept", "application/json")
        build()
    }

    @HeadersBasicV2
    @Provides
    @Singleton
    fun getBaseHeadersV2(): Headers = Headers.Builder().run  {
        add("Content-Type", "application/json")
        add("Accept", "*/*")
        build()
    }

    @HeadersEmpty
    @Provides
    @Singleton
    fun getEmptyHeaders(): Headers = Headers.Builder().build()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeadersBasic

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeadersBasicV2


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeadersEmpty