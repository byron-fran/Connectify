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
    modifiers: ContactCardModifiers = ContactCardModifiers(),
    onChangeFavorite: () -> Unit,
    onNavigate: () -> Unit,
) {

    Card(
        modifier = Modifier
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .padding(vertical = Spacing.spacing_sm)
                    .weight(1f)
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
                        modifier = modifiers.textModifier

                    )
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
                                )
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
                                    )
                                )
                            }
                        }
                    }
                }
            }
            CustomIconButton(
                icon = if (contact.isFavorite) R.drawable.icon_star_round_filled else R.drawable.icon_star_outline,
                size = Card.card_sm,
                color = MaterialTheme.colorScheme.tertiary
            ) {
                onChangeFavorite()
            }
        }
    }
}