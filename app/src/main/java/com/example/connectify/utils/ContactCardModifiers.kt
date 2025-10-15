package com.example.connectify.utils

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Immutable

@Immutable
data class ContactCardModifiers(
    val cardModifier: Modifier = Modifier,
    val imageModifier: Modifier = Modifier,
    val textModifier: Modifier = Modifier
)