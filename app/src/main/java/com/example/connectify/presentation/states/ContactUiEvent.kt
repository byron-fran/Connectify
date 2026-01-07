package com.example.connectify.presentation.states

import com.example.connectify.domain.models.Contact

sealed class ContactUiEvent {

    data class InsertContact(val contact: Contact) : ContactUiEvent()
    data class GetContact(val contactId: String) : ContactUiEvent()
    data class DeleteContact(val contact: Contact) : ContactUiEvent()
    data class ToggleFavorite(val contact : Contact) : ContactUiEvent()
    data class UpdateContact(val contact: Contact) : ContactUiEvent()
    data object DeleteAllContacts : ContactUiEvent()

}