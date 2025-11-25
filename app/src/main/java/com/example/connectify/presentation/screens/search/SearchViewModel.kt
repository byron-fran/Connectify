package com.example.connectify.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectify.domain.useCases.SearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCases: SearchUseCases
) : ViewModel() {

    private var _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState : StateFlow<SearchUiState> get() = _searchUiState.asStateFlow()

    private var searchJob: Job? = null

    fun searchContacts(query: String) {

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(800)
            searchUseCases.getSearchResult(query).collect {  contacts ->
                _searchUiState.update { state ->
                    state.copy(
                        result = contacts
                    )
                }
            }
        }
    }

    fun clearState () {
        _searchUiState.update { state ->
            state.copy(
                result = emptyList()
            )
        }
    }

}