package com.example.core.data.remote.datasource

import com.example.core.data.remote.base.RemoteDataSource
import com.example.core.data.remote.model.HiringResponseModel
import com.example.core.data.remote.model.params.HiringParamsModel

interface HiringRemoteDataSource: RemoteDataSource {
    suspend fun getHiringResponseModel(hiringParamsModel: HiringParamsModel): List<HiringResponseModel>

}