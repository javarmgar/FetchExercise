package com.example.core.domain.fetchusecase

import com.example.core.data.remote.model.HiringResponseModel
import com.example.core.data.remote.model.params.HiringParamsModel
import com.example.core.data.repository.HiringRepository

interface GetHiringUseCase {

    val hiringRepository: HiringRepository

    suspend operator fun invoke(hiringParamsModel: HiringParamsModel): List<HiringResponseModel>

}