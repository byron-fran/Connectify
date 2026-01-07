package com.example.connectify.presentation.screens.contact

import com.example.connectify.domain.models.Contact

data class ContactUIState(
    val contact : Contact? = null,
    val contacts : List<Contact> = emptyList(),
    val selectedContact: Contact? = null,
    val loading: Boolean = false,
    val error: String? = null
)
