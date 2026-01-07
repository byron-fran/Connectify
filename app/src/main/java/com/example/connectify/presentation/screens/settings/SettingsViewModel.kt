package com.example.connectify.presentation.screens.settings

import androidx.lifecycle.ViewModel
import com.example.connectify.domain.useCases.ContactUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {





}