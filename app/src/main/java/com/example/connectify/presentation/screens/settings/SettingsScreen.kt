package com.example.connectify.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.ui.theme.Spacing

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onNavigateTo: (Screens) -> Unit,
    onNavigateBack: () -> Unit,

    ) {

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = {
                    TitleMedium(stringResource(R.string.settings))
                },
                canNavigateBack = true
            ) {
                onNavigateBack()
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier.padding(horizontal = Spacing.spacing_sm),
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md)

            ) {
                SettingsCard(
                    title = stringResource(R.string.theme),
                    icon = R.drawable.icon_brightness,
                ) {
                    onNavigateTo(Screens.Theme)
                }
            }

        }
    }
}

@Composable
fun SettingsCard(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Spacing.spacing_md),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { onClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.spacing_md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(

                horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomIcon(
                    icon = icon,
                    color = MaterialTheme.colorScheme.onBackground
                )
                BodyLarge(title)
            }
            CustomIcon(
                R.drawable.icon_keyboard_arrow_right,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}