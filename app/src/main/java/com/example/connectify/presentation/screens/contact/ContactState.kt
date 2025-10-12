package com.example.connectify.presentation.screens.contact

import com.example.connectify.domain.models.Contact

data class ContactState(
    val contact : Contact? = null,
    val contacts : List<Contact> = emptyList()
)
