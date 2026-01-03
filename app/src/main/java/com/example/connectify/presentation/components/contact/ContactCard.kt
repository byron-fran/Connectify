package com.example.connectify.presentation.components.contact

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.BodySmall
import com.example.connectify.presentation.components.global.BoxCircle
import com.example.connectify.presentation.components.global.ContactImage
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.ContactCardModifiers

@Composable
fun ContactCard(
    contact: Contact,
    togglingFavoriteId: String?,
    modifier: Modifier = Modifier,
    modifiers: ContactCardModifiers = ContactCardModifiers(),
    onFavoriteToggle: (Contact) -> Unit,
    onChangeModalBottonSheet: () -> Unit,
    onNavigate: () -> Unit,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(Spacing.spacing_md),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onNavigate
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactInfoSection(
                contact,
                modifiers,
                modifier.weight(1f)
            )
            ContactActionsSection(
                contactId = contact.id,
                isFavorite = contact.isFavorite,
                togglingFavoriteId = togglingFavoriteId,
                onFavoriteToggle = {
                    onFavoriteToggle(contact)
                },
                onShowMoreOptions = {
                    onChangeModalBottonSheet()
                }
            )
        }
    }
}

@Composable
private fun ContactInfoSection(
    contact: Contact,
    modifiers: ContactCardModifiers,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(vertical = Spacing.spacing_sm)
    ) {

        ContactImage(
            uri = contact.imageUrl,
            modifier = modifiers.imageModifier
                .clip(CircleShape)
                .size(Card.card_md)
        ) {
            BoxCircle(
                modifier = modifiers.imageModifier
                    .background(
                        shape = CircleShape,
                        color = contact.colorDefault.copy(alpha = 0.6f),
                    )
                    .size(Card.card_md),
                content = {
                    BodyLarge(
                        text = contact.name.first().toString().uppercase(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.zIndex(1f)
                    )
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_xs)
        ) {
            BodyLarge(
                text = contact.name,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = modifiers
                    .textModifier
                    .testTag("contact_name_${contact.id}")

            )
            ContactSecondaryInfo(contact)
        }
    }
}

@Composable
private fun ContactSecondaryInfo(contact: Contact) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_sm)
        ) {
            BodySmall(
                text = contact.phoneNumber.toString(),
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.7f
                ),
                modifier = Modifier
                    .testTag("contact_phone_${contact.id}")
            )
            if (!contact.email.isNullOrBlank()) {
                CustomIcon(
                    icon = R.drawable.icon_circle,
                    modifier = Modifier.size(Spacing.spacing_sm),
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.7f
                    )
                )
                BodySmall(
                    text = contact.email ?: "",
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.7f
                    ),
                    modifier = Modifier
                        .testTag("contact_email_${contact.id}")
                )
            }
        }
    }

}

@Composable
private fun ContactActionsSection(
    contactId: String,
    isFavorite: Boolean,
    togglingFavoriteId: String?,
    onFavoriteToggle: () -> Unit,
    onShowMoreOptions: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomIconButton(
            icon = if (togglingFavoriteId == contactId || isFavorite)
                R.drawable.icon_star_round_filled
            else
                R.drawable.icon_star_outline,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(24.dp)
        ) {
            onFavoriteToggle()
        }
        CustomIconButton(
            icon = R.drawable.icon_more_vert,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        ) {
            onShowMoreOptions()
        }
    }
}