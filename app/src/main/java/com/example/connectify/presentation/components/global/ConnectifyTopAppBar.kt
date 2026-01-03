package com.example.connectify.presentation.components.global

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.connectify.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectifyToAppBar(
    title : @Composable () -> Unit,
    canNavigateBack: Boolean = true,
    actions: @Composable (RowScope.() -> Unit) = {},
    onNavigate: () -> Unit
) {

    CenterAlignedTopAppBar(
        title = title,
        navigationIcon = {
            if (canNavigateBack) {
                CustomIconButton(
                    icon = R.drawable.icon_arrow_back,
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    onNavigate()
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        actions = actions
    )
}