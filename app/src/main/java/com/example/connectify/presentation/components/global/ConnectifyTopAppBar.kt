package com.example.connectify.presentation.components.global

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.connectify.R
import com.example.connectify.ui.theme.Card


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectifyToAppBar(
    title: String,
    canNavigateBack: Boolean = true,
    onNavigate: () -> Unit
) {

    CenterAlignedTopAppBar(
        title = {
            AnimatedVisibility(true) {
                TitleMedium(text = title)
            }
        },
        navigationIcon = {
            if (canNavigateBack){
                CustomIconButton(
                    icon = R.drawable.icon_arrow_back,
                    color = MaterialTheme.colorScheme.onBackground,
                    size = Card.card_sm
                ) {
                    onNavigate()
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}