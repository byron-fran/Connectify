package com.example.connectify.presentation.screens.search

import com.example.connectify.domain.models.Contact

data class SearchUiState(
    val result : List<Contact> = emptyList()
)
