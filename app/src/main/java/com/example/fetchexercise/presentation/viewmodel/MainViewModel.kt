package com.example.fetchexercise.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.remote.model.HiringResponseModel
import com.example.core.data.remote.model.params.HiringParamsModel
import com.example.fetchexercise.framework.domainimpl.base.Interactors
import com.example.fetchexercise.framework.library.utils.Resource
import com.example.fetchexercise.framework.library.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactors: Interactors,
):  ViewModel(){

    private val LOG_TAG:String = "LT_HomeMenuViewModel"

    private val _hiringListState: MutableStateFlow<Resource<List<HiringResponseModel>>> = MutableStateFlow(Resource.init(data = null))
    val hiringListState: StateFlow<Resource<List<HiringResponseModel>>> = _hiringListState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchHiringList(hiringParamsModel: HiringParamsModel){
        Log.d(LOG_TAG, "fetchHiringList()   STEP 1:")
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            when(_hiringListState.value.status){
                Status.INIT -> {
                    val stateLoading = Resource.loading(data = null)
                    _hiringListState.update {
                        it.copy(
                            status = stateLoading.status,
                            data = stateLoading.data,
                            message = stateLoading.message
                        )
                    }
                    try{
                        val hiringList = interactors.getHiringUseCase(hiringParamsModel)
                        val stateSuccess = Resource.success(data = hiringList)
                        val stateDone = Resource.done(data = hiringList)
                        _hiringListState.update {
                            it.copy(
                                status = stateSuccess.status,
                                data = stateSuccess.data,
                                message = stateSuccess.message
                            )
                        }
                        _hiringListState.update {
                            it.copy(
                                status = stateDone.status,
                                data = stateDone.data,
                                message = stateDone.message
                            )
                        }
                    }catch(exception: Exception){
                        val stateError = Resource.error(data = null, msg = exception.message ?: "Error Occurred!")
                        val stateInit = Resource.init(data = null)
                        _hiringListState.update {
                            it.copy(
                                status = stateError.status,
                                data = stateError.data,
                                message = stateError.message
                            )
                        }
                        _hiringListState.update {
                            it.copy(
                                status = stateInit.status,
                                data = stateInit.data,
                                message = stateInit.message
                            )
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

}