package com.example.fetchexercise.presentation.viewmodel.Interface

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.data.remote.model.HiringResponseModel
import com.example.core.data.remote.model.params.HiringParamsModel
import com.example.core.domain.fetchusecase.GetHiringUseCase
import com.example.fetchexercise.framework.library.utils.Resource
import com.example.fetchexercise.framework.library.utils.Status
import kotlinx.coroutines.Dispatchers

interface InterfaceGetHiringUseCase {

    fun getHiringUseCase(
        getHiringUseCase: GetHiringUseCase,
        hiringParamsModel: HiringParamsModel,
        hiringListLD: LiveData<Resource<List<HiringResponseModel>>>
    ): LiveData<Resource<List<HiringResponseModel>>> = liveData(Dispatchers.IO) {

        when(hiringListLD.value?.status){
            Status.INIT -> {
                val stateLoading = Resource.loading(data = null)
                emit(stateLoading)
                try{
                    val hiringList = getHiringUseCase(hiringParamsModel)
                    val stateSuccess = Resource.success(data = hiringList)
                    val stateDone = Resource.done(data = hiringList)
                    emit(stateSuccess)
                    emit(stateDone)
                }catch(exception: Exception){
                    val stateError = Resource.error(data = null, msg = exception.message ?: "Error Occurred!")
                    val stateInit = Resource.init(data = null)
                    emit(stateError)
                    emit(stateInit)
                }
            }
            else -> {

            }
        }
    }
}