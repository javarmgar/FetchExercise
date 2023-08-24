package com.example.fetchexercise.framework.dataimpl.mapper

import com.example.core.data.mapper.MapperIO
import com.example.core.data.remote.model.HiringResponseModel
import com.example.fetchexercise.framework.dataimpl.remote.model.response.HiringResponse

class HiringMapperImpl: MapperIO<HiringResponse, HiringResponseModel> {

    private val defaultString = "null"

    override suspend fun map(input: HiringResponse): HiringResponseModel {
        return input.run {
            HiringResponseModel(
                id = this.id ?: defaultString,
                listId = this.listId ?: defaultString,
                name = this.name ?: defaultString,
            )
        }
    }

}