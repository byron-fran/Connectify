package com.example.connectify.presentation.screens.contact

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.contact.ContactHeader
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.BodyMedium
import com.example.connectify.presentation.components.global.ButtonError
import com.example.connectify.presentation.components.global.ButtonPrimary
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.navigation.Screens
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
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contactViewModel: ContactViewModel = hiltViewModel<ContactViewModel>(),
    onNavigateTo: (Screens) -> Unit,
    onNavigate: () -> Unit

) {

    val scrollState = rememberScrollState()
    val contact = contactViewModel.contactState.collectAsState().value.contact
    var showDialog by remember { mutableStateOf(false) }

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
                title = contact?.name ?: "",
                actions = {
                    CustomIconButton(
                        R.drawable.icon_edit,
                        color = MaterialTheme.colorScheme.onBackground
                    ) {
                        contact?.let {
                            onNavigateTo(Screens.EditContact(contact.id))
                        }
                    }
                }
            ) { onNavigate() }
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
                                key = SharedTransition.sharedTransitionImageKey(contact.id)
                            ),
                        ),
                        textModifier = Modifier.sharedElement(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = SharedTransition.sharedTransitionTitleKey(contact.id)
                            ),
                            boundsTransform = { initialBounds, targetBounds ->
                                keyframes {
                                    durationMillis = 800
                                    initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                                    targetBounds at 800
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
                        modifier = Modifier.padding(horizontal = Spacing.spacing_sm)
                    ) {
                        showDialog = !showDialog
                    }
                }
                ContactDeleteDialog(
                    showDialog,
                    onConfirm = {
                        contactViewModel.deleteContact(it)
                        onNavigate()
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
    modifier: Modifier = Modifier,
    onDelete: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            BodyLarge(" ")
            CustomIconButton(R.drawable.icon_star_filled) {

            }

        }
        ContactCardAction(
            title = stringResource(R.string.send_message),
            icon = R.drawable.icon_message
        ) {

        }
        ContactCardAction(
            title = stringResource(R.string.call_contact),
            icon = R.drawable.icon_phone
        ) {

        }
        ContactCardAction(
            title = stringResource(R.string.send_email),
            icon = R.drawable.icon_email
        ) {

        }
        Spacer(modifier = Modifier.height(Spacing.spacing_md))
        ButtonError(stringResource(R.string.delete_contact), modifier = Modifier.fillMaxWidth()) {
            onDelete()
        }
    }
}


@Composable
fun ContactCardAction(
    title: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() }.fillMaxWidth(),
        shape = RoundedCornerShape(RoundedCorner.rounded_corner_md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = Spacing.spacing_lg,
                horizontal = Spacing.spacing_sm
            ),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIcon(icon)
            BodyMedium(text = title, color = MaterialTheme.colorScheme.primary)
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
            Box( modifier = Modifier
                    .background(
                        color = Color.Cyan,
                        shape = RoundedCornerShape(RoundedCorner.rounded_corner_sm)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column( modifier = Modifier.padding(Spacing.spacing_xl),
                    verticalArrangement = Arrangement.spacedBy(Spacing.spacing_lg),
                ) {
                    BodyLarge(stringResource(R.string.confirm_delete_message))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md)
                    ) {
                        ButtonError(
                            text = stringResource(R.string.confirm_delete),
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