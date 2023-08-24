package com.example.fetchexercise.modules.datasource.remote

import com.example.core.data.remote.datasource.HiringRemoteDataSource
import com.example.fetchexercise.framework.dataimpl.remote.datasource.HiringRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindHiringRemoteDataSource(hiringRemoteDataSourceImpl: HiringRemoteDataSourceImpl): HiringRemoteDataSource

}