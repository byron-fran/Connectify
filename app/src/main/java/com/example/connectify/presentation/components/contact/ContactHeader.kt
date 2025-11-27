package com.example.connectify.presentation.components.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.global.BoxCircle
import com.example.connectify.presentation.components.global.ContactImage
import com.example.connectify.presentation.components.global.TitleLarge
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.ContactCardModifiers

@Composable
fun ContactHeader(
    modifiers: ContactCardModifiers = ContactCardModifiers(),
    contact: Contact
) {

    Box(
        modifier = modifiers.cardModifier
            .height(250.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_lg)
        ) {

            ContactImage(
                uri = contact.imageUrl,
                modifier = modifiers.imageModifier
                    .size(Card.card_3xl)
                    .clip(CircleShape)
            ) {
                BoxCircle(
                    modifier = modifiers.imageModifier
                        .size(Card.card_3xl)
                        .background(
                            shape = CircleShape,
                            color = contact.colorDefault.copy(alpha = 0.6f)
                        ),
                    content = {
                        TitleLarge(
                            text = contact.name.first().toString().uppercase(),
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.zIndex(1f)
                        )
                    }
                )
            }
            TitleMedium(
                text = contact.name,
                modifier = modifiers.textModifier
            )
        }
    }
}
