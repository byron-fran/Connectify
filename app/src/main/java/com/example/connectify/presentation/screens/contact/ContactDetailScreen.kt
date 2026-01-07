package com.example.connectify.presentation.screens.contact

import android.content.ClipData
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.contact.ContactHeader
import com.example.connectify.presentation.components.contact.callPhone
import com.example.connectify.presentation.components.contact.sendEmail
import com.example.connectify.presentation.components.contact.sendSMS
import com.example.connectify.presentation.components.global.BodyMedium
import com.example.connectify.presentation.components.global.ButtonError
import com.example.connectify.presentation.components.global.ButtonPrimary
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.presentation.states.ContactUiEvent
import com.example.connectify.ui.theme.RoundedCorner
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.ui.theme.Spacing.spacing_md
import com.example.connectify.utils.ContactCardModifiers
import com.example.connectify.utils.SharedTransition
import com.example.connectify.utils.Tag.CONTACT_DETAIL_SCREEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactDetailScreen(
    contactId: String?,
    screenKey: String? = null,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    togglingFavoriteId : String?,
    contactUiState : ContactUIState,
    onEvent : (ContactUiEvent) -> Unit,
    onNavigateToEdit: (Screens) -> Unit,
    onNavigateBack: () -> Unit

) {

    val scrollState = rememberScrollState()
    val contact = contactUiState.contact
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(contactId) {
        contactId?.let {
            withContext(Dispatchers.IO) {
                onEvent(ContactUiEvent.GetContact(contactId))

            }
        }
    }

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = { },
                actions = {
                    CustomIconButton( icon = R.drawable.icon_edit, color = MaterialTheme.colorScheme.onBackground) {
                        contact?.let { onNavigateToEdit(Screens.EditContact(contact.id)) }
                    }
                }
            ) { onNavigateBack() }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.testTag("${CONTACT_DETAIL_SCREEN}_$contactId")
    ) { paddingValues ->
        contact?.let {
            with(sharedTransitionScope) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                ) {
                    val contactModifier = ContactCardModifiers(

                        imageModifier = Modifier.sharedElement(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = SharedTransition.sharedTransitionImageKey(
                                    contact.id,
                                    screenKey
                                )
                            ),
                        ),
                        textModifier = Modifier.sharedElement(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = SharedTransition.sharedTransitionTitleKey(
                                    contact.id,
                                    screenKey
                                )
                            ),
                            boundsTransform = { initialBounds, targetBounds ->
                                keyframes {
                                    durationMillis = 500
                                    initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                                    targetBounds at 500
                                }
                            }
                        )
                    )
                    ContactHeader(
                        contact = it,
                        modifiers = contactModifier
                    )
                    ContactDetailBody(
                        contact,
                        togglingFavoriteId = togglingFavoriteId,
                        modifier = Modifier.padding(horizontal = Spacing.spacing_sm)
                    ) {
                        onEvent(ContactUiEvent.ToggleFavorite(contact))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    ButtonError(

                        text = stringResource(R.string.delete_contact),
                        modifier = Modifier
                            .padding(horizontal = Spacing.spacing_sm)
                            .fillMaxWidth()
                    ) {
                        showDialog = !showDialog
                    }
                }
                ContactDeleteDialog(
                    text = stringResource(R.string.confirm_delete_message),
                    showDialog,
                    onConfirm = {
                        onEvent(ContactUiEvent.DeleteContact(it))
                        onNavigateBack()
                    },
                    onCancel = { showDialog = !showDialog }
                )
            }
        }
    }
}


@Composable
fun ContactDetailBody(
    contact: Contact,
    togglingFavoriteId: String?,
    modifier: Modifier = Modifier,
    onFavoriteToggle: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Column( verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md)) {

            Spacer(modifier = Modifier.height(Spacing.spacing_sm))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomIconButton(
                        icon = R.drawable.icon_phone,
                        modifier = ModifierButtonDetail()
                    ) {
                        callPhone(contact.phoneNumber.toString(), context)
                    }
                    CustomIconButton(
                        icon = R.drawable.icon_message,
                        modifier = ModifierButtonDetail()
                    ) {
                        sendSMS(contact.phoneNumber.toString(), context)
                    }
                    contact.email?.let { e ->
                        if (e.isNotEmpty()) {
                            CustomIconButton(
                                icon = R.drawable.icon_email,
                                modifier = ModifierButtonDetail()
                            ) {
                                sendEmail(e, context)
                            }
                        }
                    }
                }
                CustomIconButton(
                    icon = if (togglingFavoriteId == contact.id || contact.isFavorite)
                        R.drawable.icon_star_round_filled
                    else
                        R.drawable.icon_star_outline,
                    color = MaterialTheme.colorScheme.tertiary
                ) {
                    onFavoriteToggle()
                }
            }
            ContactDataInfo(
                phoneNumber = contact.phoneNumber.toString(),
                email = contact.email ?: "",
                modifier = Modifier.fillMaxWidth()
            )

        }

    }
}

@Composable
fun ContactDeleteDialog(
    text: String,
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {

    if (showDialog) {

        Dialog(onDismissRequest = { onCancel() }) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(RoundedCorner.rounded_corner_sm)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(
                        vertical = Spacing.spacing_xl,
                        horizontal = Spacing.spacing_sm
                    ),
                    verticalArrangement = Arrangement.spacedBy(Spacing.spacing_lg),
                ) {
                    BodyMedium(
                        text = text,
                        maxLines = 3,
                        textAlign = TextAlign.Center

                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md)
                    ) {
                        ButtonError(
                            text = "Sí, eliminar",
                            modifier = Modifier.weight(1f)
                        ) {
                            onConfirm()
                        }
                        ButtonPrimary(
                            text = stringResource(R.string.cancel),
                            modifier = Modifier.weight(1f)
                        ) {
                            onCancel()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactDataInfo(
    modifier: Modifier = Modifier,
    email: String = "",
    phoneNumber: String,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md)) {

                ContactDataInfoChip(
                    text = "+$phoneNumber",
                    icon = R.drawable.icon_phone,
                    modifier = Modifier.fillMaxWidth()
                )
                if (email.trim().isNotEmpty()) {
                    ContactDataInfoChip(
                        text = email,
                        icon = R.drawable.icon_email,
                        modifier = Modifier.fillMaxWidth()

                    )
                }

            }
        }

    }
}

@Composable
fun ContactDataInfoChip(
    text: String,
    icon: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val toast = Toast.makeText(context, R.string.copy, Toast.LENGTH_SHORT)

    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(RoundedCorner.rounded_corner_md)
            )
            .padding(Spacing.spacing_md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIcon(icon, color = MaterialTheme.colorScheme.onSurface)
            BodyMedium(text, color = MaterialTheme.colorScheme.onSurface)
        }
        CustomIconButton(
            icon = R.drawable.icon_copy_content,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        ) {
            scope.launch {
                clipboardManager.setClipEntry(
                    ClipEntry(ClipData.newPlainText("text", text))

                )
                delay(500)
                toast.show()
            }
        }
    }
}

@Composable
private fun ModifierButtonDetail(): Modifier {
    return Modifier
        .padding(spacing_md)
        .background(
            color = MaterialTheme.colorScheme.secondary,
            shape = CircleShape
        )
}