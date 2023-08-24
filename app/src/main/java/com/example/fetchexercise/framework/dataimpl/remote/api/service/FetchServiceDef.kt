package com.example.fetchexercise.framework.dataimpl.remote.api.service

import com.example.core.data.remote.base.ServiceDefinition
import com.example.fetchexercise.framework.dataimpl.remote.model.response.HiringResponse
import com.example.fetchexercise.framework.library.key.KeysRemote
import retrofit2.Response
import retrofit2.http.GET

interface FetchServiceDef: ServiceDefinition {

    @GET(KeysRemote.FETCH_URL_HIRING)
    suspend fun getHiringList(): Response<List<HiringResponse>>
}