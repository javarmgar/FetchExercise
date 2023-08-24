package com.example.fetchexercise.modules.interactor

import com.example.core.domain.fetchusecase.GetHiringUseCase
import com.example.fetchexercise.framework.domainimpl.fethusecase.GetHiringUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    @Binds
    abstract fun bindGetHiringUseCase( getHiringUseCaseImpl: GetHiringUseCaseImpl): GetHiringUseCase

}