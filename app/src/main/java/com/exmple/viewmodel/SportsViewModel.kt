package com.exmple.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exmple.core.ModelStore
import com.exmple.core.StateFlowModelStore
import com.exmple.model.SportsResponse
import com.exmple.remote.SportsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    private val _modelStore: ModelStore<ViewState> =
        StateFlowModelStore(ViewState(), viewModelScope)
    val viewState: StateFlow<ViewState> get() = _modelStore.modelState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ViewState()
    )

    fun onAction(action: Action) {
        when (action) {
            is Action.FetchTeamStats -> fetchTeamStats()
            is Action.DismissError -> dismissError()
        }
    }

    private fun dismissError() {
        viewModelScope.launch {
            _modelStore.process { oldState -> oldState.copy(displayError = false) }
        }
    }

    private fun fetchTeamStats() {
        viewModelScope.launch {
            setLoadingState(true)
            val teamNumber = Random.nextInt(1, 32)
            val sportsResponse = repository.getTeamStats(26)
            _modelStore.process { oldState ->
                oldState.copy(
                    sportsResponse = sportsResponse,
                    displayError = sportsResponse == null,
                    isLoading = false
                )
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
       viewModelScope.launch {
           _modelStore.process { oldState -> oldState.copy(isLoading = isLoading) }
       }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val displayError: Boolean = false,
        val sportsResponse: SportsResponse? = null
    )

    sealed interface Action {
        data object FetchTeamStats : Action
        data object DismissError : Action
    }
}
