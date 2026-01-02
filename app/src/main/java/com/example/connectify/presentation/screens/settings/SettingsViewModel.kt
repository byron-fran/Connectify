package com.example.connectify.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectify.domain.useCases.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {



    fun deleteAllContacts() {
        viewModelScope.launch {
            contactUseCases.deleteAllContacts()
        }
    }

}