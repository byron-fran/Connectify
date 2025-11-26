package com.example.connectify.presentation.screens.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.useCases.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {

    private val _contactState = MutableStateFlow(ContactState())
    val contactState = _contactState.asStateFlow()

    init {
        getAllContacts()
    }

    fun insertContact(contact: Contact) {
        viewModelScope.launch {
            contactUseCases.insertContact(contact)
        }
    }


    fun getAllContacts() {
        viewModelScope.launch {
            contactUseCases.getAllContacts().collect {
                _contactState.update { state ->
                    state.copy(
                        contacts = it
                    )
                }
            }
        }

    }


    fun getContactById(id: String) {
        viewModelScope.launch {
            contactUseCases.getContactById(id).collect {
                _contactState.update { state ->
                    state.copy(
                        contact = it
                    )
                }
            }
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            contactUseCases.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactUseCases.deleteContact(contact)
        }

    }

    fun updateContactFavorite(isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _contactState.value.contact?.let {
                contactUseCases.updateContact(
                    contact = it.copy(
                        isFavorite = isFavorite
                    )
                )
            }
        }
    }
}