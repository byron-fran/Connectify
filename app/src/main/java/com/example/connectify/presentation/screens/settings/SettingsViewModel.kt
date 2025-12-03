package com.example.connectify.presentation.screens.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.connectify.domain.repositories.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val context: Application
) : ViewModel() {


}