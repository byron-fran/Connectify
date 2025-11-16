package com.example.connectify.presentation.components.contact


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.connectify.R
import com.example.connectify.presentation.components.global.ButtonPrimary
import com.example.connectify.presentation.components.global.ContactImage
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.components.global.LabelMedium
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.RoundedCorner
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.customTextFieldColors

@Composable
fun ContactForm(
    name: String,
    phoneNumber: String,
    email: String,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSubmit: () -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing_lg)
    ) {

        ContactTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = stringResource(R.string.name)
        )

        ContactTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = stringResource(R.string.phone_number),
            keyboardType = KeyboardType.Number

        )
        ContactTextField(
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.email),
            keyboardType = KeyboardType.Email
        )
        ButtonPrimary(
            text = stringResource(R.string.save),
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled,
        ) {
            onSubmit()
        }
    }
}


@Composable
fun ContactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { LabelMedium(label) },
        colors = customTextFieldColors(),
        shape = RoundedCornerShape(RoundedCorner.rounded_corner_md),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = modifier.fillMaxWidth()
    )

}

@Composable
fun ContactFormImage(
    imageUri: String?,
    onImageChange: () -> Unit,
    onClearImage: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(Card.card_3xl)
                .clip(CircleShape)
                .clickable { onImageChange() },

            ) {

            ContactImage(imageUri, modifier = Modifier.fillMaxSize()) {
                CustomIcon(
                    icon = R.drawable.icon_image,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }
        imageUri?.let {
            CustomIconButton(
                icon = R.drawable.icon_close,
                color = MaterialTheme.colorScheme.surface,
            ) {
                onClearImage()
            }
        }
    }
}