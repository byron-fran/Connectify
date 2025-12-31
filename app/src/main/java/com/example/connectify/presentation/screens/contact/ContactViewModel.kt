package com.example.connectify.presentation.screens.contact

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.useCases.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {

    private val _togglingFavoriteId = mutableStateOf<String?>(null)
    val togglingFavoriteId: State<String?> = _togglingFavoriteId

    private val _selectedContactId = MutableStateFlow<String?>(null)
    private val _error = MutableStateFlow<String?>(null)
    private val _allContacts: StateFlow<List<Contact>> = contactUseCases.getAllContacts()
        .catch { e ->
            _error.value = e.message
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _selectedContact: StateFlow<Contact?> = _selectedContactId
        .flatMapLatest { id ->
            id?.let { 
                contactUseCases.getContactById(it)
                    .catch { e ->
                        _error.value = e.message
                        emit(null)
                    }
            } ?: flowOf(null)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val contactState: StateFlow<ContactState> = combine(
        _allContacts,
        _selectedContact,
        _error
    ) { contacts, contact, error ->
        ContactState(
            contacts = contacts,
            contact = contact,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ContactState()
    )

    fun insertContact(contact: Contact) {

        viewModelScope.launch {
            try {
                contactUseCases.insertContact(contact)

            } catch (e: Exception) {
                _error.value = e.message

            }
        }
    }


    fun getContactById(id: String) {
        _selectedContactId.value = id
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            try {
                contactUseCases.updateContact(contact)

            } catch (e: Exception) {
               _error.value = e.message
            }
        }
    }

    fun deleteContact(contact: Contact) {

        viewModelScope.launch {
            try {
                contactUseCases.deleteContact(contact)
            } catch (e: Exception) {
               _error.value = e.message
            }
        }

    }

    fun toggleFavorite(contact: Contact) {
        viewModelScope.launch {
            val newValue = !contact.isFavorite
            val contactOptimistic = contact.copy(isFavorite = newValue)

            _togglingFavoriteId.value = contact.id

            try {
                contactUseCases.updateContact(contactOptimistic)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _togglingFavoriteId.value = null
            }
        }
    }
}