package com.example.connectify.presentation.screens.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.ThemeMode

@Composable
fun ThemeScreen(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val themeMode by themeViewModel.themeMode.collectAsState()

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = { TitleMedium(stringResource(R.string.theme)) },
                canNavigateBack = true
            ) {
                onNavigateBack()
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            Column(
                modifier = Modifier
                    .padding(horizontal = Spacing.spacing_sm),
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md)
            ) {
                ThemeModeOption(
                    mode = ThemeMode.DARK,
                    selected = themeMode == ThemeMode.DARK,
                    onClick = { themeViewModel.setThemeMode(ThemeMode.DARK) }
                )
                ThemeModeOption(
                    mode = ThemeMode.LIGHT,
                    selected = themeMode == ThemeMode.LIGHT,
                    onClick = { themeViewModel.setThemeMode(ThemeMode.LIGHT) }
                )
                ThemeModeOption(
                    mode = ThemeMode.FOLLOW_SYSTEM,
                    selected = themeMode == ThemeMode.FOLLOW_SYSTEM,
                    onClick = { themeViewModel.setThemeMode(ThemeMode.FOLLOW_SYSTEM) }
                )
            }
        }
    }

}

@Composable
fun ThemeModeOption(
    mode: ThemeMode,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val (title, icon) = when (mode) {
        ThemeMode.DARK -> stringResource(R.string.dark_mode) to R.drawable.icon_dark_mode
        ThemeMode.LIGHT -> stringResource(R.string.light_mode) to R.drawable.icon_light_mode
        ThemeMode.FOLLOW_SYSTEM -> stringResource(R.string.system_mode_default) to R.drawable.icon_mobile_hand
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Spacing.spacing_md)
            )
            .padding(vertical = Spacing.spacing_md)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIcon(
                icon = icon,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
            BodyLarge(
                text = title,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
        }
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}