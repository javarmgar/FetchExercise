package com.example.core.data.repository

import com.example.core.data.remote.model.HiringResponseModel
import com.example.core.data.remote.model.params.HiringParamsModel

interface HiringRepository {

    suspend fun getHiringList(hiringParamsModel: HiringParamsModel): List<HiringResponseModel>

}