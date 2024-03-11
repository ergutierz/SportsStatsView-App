package com.exmple.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewModelScope;
import com.exmple.core.ModelStore;
import com.exmple.core.StateFlowModelStore;
import com.exmple.model.SportsResponse;
import com.exmple.remote.SportsRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.stateIn;
import kotlinx.coroutines.launch;
import javax.inject.Inject;

/**
 * ViewModel for handling sports-related data and actions.
 * This ViewModel interacts with the SportsRepository to fetch sports statistics
 * and manage UI state based on the data fetched.
 */
@HiltViewModel
class SportsViewModel @Inject constructor(
    private val repository: SportsRepository
) : ViewModel() {

    // Internal ModelStore for managing the ViewState.
    private val _modelStore: ModelStore<ViewState> =
        StateFlowModelStore(ViewState(), viewModelScope);

    /**
     * Exposes the current view state as a StateFlow.
     * Consumers can collect from this StateFlow to receive updates on the UI state.
     */
    val viewState: StateFlow<ViewState> get() = _modelStore.modelState().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ViewState()
    );

    /**
     * Processes user actions and triggers appropriate ViewModel operations.
     * @param action The action to be processed.
     */
    fun onAction(action: Action) {
        when (action) {
            is Action.FetchTeamStats -> fetchTeamStats();
            is Action.DismissError -> dismissError();
        }
    }

    // Dismisses any currently displayed error in the UI.
    private fun dismissError() {
        viewModelScope.launch {
            _modelStore.process { oldState -> oldState.copy(displayError = false) };
        }
    }

    // Fetches team statistics and updates the view state accordingly.
    private fun fetchTeamStats() {
        viewModelScope.launch {
            setLoadingState(true);
            val sportsResponse = repository.getTeamStats();
            _modelStore.process { oldState ->
                oldState.copy(
                    sportsResponse = sportsResponse,
                    displayError = sportsResponse == null,
                    isLoading = false
                );
            };
        }
    }

    // Updates the loading state in the view state.
    private fun setLoadingState(isLoading: Boolean) {
        viewModelScope.launch {
            _modelStore.process { oldState -> oldState.copy(isLoading = isLoading) };
        }
    }

    /**
     * Represents the various states of the UI.
     */
    data class ViewState(
        val isLoading: Boolean = false,
        val displayError: Boolean = false,
        val sportsResponse: SportsResponse? = null
    );

    /**
     * Represents actions that can be performed by the UI and are handled by the ViewModel.
     */
    sealed interface Action {
        // Action to fetch team statistics.
        data object FetchTeamStats : Action;
        // Action to dismiss an error displayed on the UI.
        data object DismissError : Action;
    }
}
