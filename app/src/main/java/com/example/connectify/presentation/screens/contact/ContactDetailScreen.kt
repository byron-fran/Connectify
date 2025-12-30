package com.example.connectify.presentation.screens.contact

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.contact.CallPhone
import com.example.connectify.presentation.components.contact.ContactHeader
import com.example.connectify.presentation.components.contact.SendEmail
import com.example.connectify.presentation.components.contact.SendMessage
import com.example.connectify.presentation.components.global.BodyMedium
import com.example.connectify.presentation.components.global.ButtonError
import com.example.connectify.presentation.components.global.ButtonPrimary
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.RoundedCorner
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.ContactCardModifiers
import com.example.connectify.utils.SharedTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactDetailScreen(
    contactId: String?,
    screenKey: String? = null,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contactViewModel: ContactViewModel = hiltViewModel<ContactViewModel>(),
    onNavigateToEdit: (Screens) -> Unit,
    onNavigateBack: () -> Unit

) {

    val scrollState = rememberScrollState()
    val contact = contactViewModel.contactState.collectAsState().value.contact
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val togglingFavoriteId = contactViewModel.togglingFavoriteId.value

    LaunchedEffect(contactId) {
        contactId?.let {
            withContext(Dispatchers.IO) {
                contactViewModel.getContactById(contactId)
            }
        }
    }

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = { },
                actions = {
                    CustomIconButton(
                        icon = R.drawable.icon_edit,
                        color = MaterialTheme.colorScheme.onBackground
                    ) {
                        contact?.let {
                            onNavigateToEdit(Screens.EditContact(contact.id))
                        }
                    }
                }
            ) { onNavigateBack() }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
    ) { paddingValues ->
        contact?.let {
            with(sharedTransitionScope) {
                Column(
                    modifier = Modifier
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
                        modifier = Modifier.padding(horizontal = Spacing.spacing_sm),
                        onDelete = {
                            showDialog = !showDialog
                        }
                    ) {
                        contactViewModel.toggleFavorite(contact)
                    }
                }
                ContactDeleteDialog(
                    showDialog,
                    onConfirm = {
                        contactViewModel.deleteContact(it)
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
    onDelete: () -> Unit,
    onFavoriteToggle: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        ContactDataInfo(
            phoneNumber = contact.phoneNumber.toString(),
            email = contact.email ?: "",
            isFavorite = contact.isFavorite,
            contactId = contact.id,
            togglingFavoriteId = togglingFavoriteId,
            modifier = Modifier.fillMaxWidth()
        ) {
            onFavoriteToggle()
        }
        Spacer(modifier = Modifier.height(Spacing.spacing_sm))
        CallPhone(contact.phoneNumber.toString())
        SendMessage(contact.phoneNumber.toString())
        contact.email?.let { e ->
            if (e.isNotEmpty()) {
                SendEmail(e)
            }
        }

        Spacer(modifier = Modifier.height(Spacing.spacing_md))
        ButtonError(stringResource(R.string.delete_contact), modifier = Modifier.fillMaxWidth()) {
            onDelete()
        }
    }
}

@Composable
fun ContactDeleteDialog(
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
                        text = stringResource(R.string.confirm_delete_message),
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
    contactId: String,
    isFavorite: Boolean,
    togglingFavoriteId: String?,
    onFavoriteToggle: () -> Unit
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
                ContactDataInfoChip(phoneNumber, R.drawable.icon_phone)
                if (email.trim().isNotEmpty()) {
                    ContactDataInfoChip(email, R.drawable.icon_email)
                }

            }
        }
        CustomIconButton(
            icon = if (togglingFavoriteId == contactId || isFavorite)
                R.drawable.icon_star_round_filled
            else
                R.drawable.icon_star_outline,
            size = Card.card_sm,
            color = MaterialTheme.colorScheme.tertiary
        ) {
            onFavoriteToggle()
        }

    }
}

@Composable
fun ContactDataInfoChip(
    text: String,
    icon: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(RoundedCorner.rounded_corner_md)
            )
            .padding(Spacing.spacing_md),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomIcon(icon, color = MaterialTheme.colorScheme.onSurface)
        BodyMedium(text, color = MaterialTheme.colorScheme.onSurface)
    }
}