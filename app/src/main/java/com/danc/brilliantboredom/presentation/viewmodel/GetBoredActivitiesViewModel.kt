package com.danc.brilliantboredom.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danc.brilliantboredom.domain.usecases.GetBoredActivities
import com.danc.brilliantboredom.presentation.states.BoredActivityState
import com.danc.brilliantboredom.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetBoredActivitiesViewModel @Inject constructor(
    private val getBoredActivities: GetBoredActivities
): ViewModel() {

    private val _state = mutableStateOf(BoredActivityState())
    val state: State<BoredActivityState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var activityJob: Job? = null

    init {
        onGetBoredActivity()
    }

    private fun onGetBoredActivity() {
        activityJob?.cancel()
        activityJob = viewModelScope.launch {
            delay(500L)
            getBoredActivities()
                .onEach { response ->
                    when(response) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                boredActivity = response.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                boredActivity = response.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    response.message ?: "Unknown"
                                )
                            )
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                boredActivity = response.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }

}