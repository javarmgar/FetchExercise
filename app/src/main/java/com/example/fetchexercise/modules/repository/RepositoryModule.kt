package com.example.fetchexercise.modules.repository

import com.example.core.data.repository.HiringRepository
import com.example.fetchexercise.framework.dataimpl.repository.HiringRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHiringRepository(hiringRepositoryImpl: HiringRepositoryImpl): HiringRepository

}