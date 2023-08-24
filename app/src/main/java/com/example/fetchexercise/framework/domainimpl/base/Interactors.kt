package com.example.fetchexercise.framework.domainimpl.base

import com.example.core.domain.fetchusecase.GetHiringUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Interactors @Inject constructor(
    val getHiringUseCase: GetHiringUseCase,
)