package com.example.connectify.presentation.screens.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.connectify.R
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.ButtonError
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.presentation.screens.contact.ContactDeleteDialog
import com.example.connectify.presentation.states.ContactUiEvent
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.Tag.SETTINGS_SCREEN

@Composable
fun SettingsScreen(
    isNotEmptyContacts: Boolean,
    onEvent: (ContactUiEvent) -> Unit,
    onNavigateTo: (Screens) -> Unit,
    onNavigateBack: () -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val toast = Toast.makeText(context, R.string.success_remove_all, Toast.LENGTH_SHORT)

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
        },
        modifier = Modifier.testTag(SETTINGS_SCREEN)
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
                Spacer(modifier = Modifier.weight(1f))
                if (isNotEmptyContacts) {
                    ButtonError(
                        text = stringResource(R.string.delete_all_contacts),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        showDialog = !showDialog
                    }
                }
            }

            ContactDeleteDialog(
                text = stringResource(R.string.delete_all_contacts_content),
                showDialog,
                onConfirm = {
                    onEvent(ContactUiEvent.DeleteAllContacts)
                    showDialog = !showDialog
                    toast.show()
                },
                onCancel = {
                    showDialog = !showDialog
                }
            )
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
            modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.spacing_md),
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