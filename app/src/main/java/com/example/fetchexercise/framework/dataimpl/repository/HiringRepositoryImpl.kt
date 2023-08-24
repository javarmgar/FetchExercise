package com.example.fetchexercise.framework.dataimpl.repository

import android.util.Log
import com.example.core.data.remote.datasource.HiringRemoteDataSource
import com.example.core.data.remote.model.HiringResponseModel
import com.example.core.data.remote.model.params.HiringParamsModel
import com.example.core.data.repository.HiringRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HiringRepositoryImpl @Inject constructor(
    val remoteDataSource: HiringRemoteDataSource
): HiringRepository {

    private val LOG_TAG:String = "LT_HiringRepository"

    override suspend fun getHiringList(hiringParamsModel: HiringParamsModel): List<HiringResponseModel> {
        Log.d(LOG_TAG,"->   getHiringList() with hiringParamsModel:$hiringParamsModel")
        val hiringResponseModelList = remoteDataSource.getHiringResponseModel(hiringParamsModel)
        Log.d(LOG_TAG,"->   getHiringList() with hiringResponseModelList:$hiringResponseModelList")
        return hiringResponseModelList
    }

}