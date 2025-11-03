package com.example.connectify.presentation.components.contact


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.connectify.R
import com.example.connectify.presentation.components.global.ButtonPrimary
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.components.global.CustomImage
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.RoundedCorner
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.customTextFieldColors

@Composable
fun ContactForm(
    name: String,
    phoneNumber: String,
    email: String,
    imageUri: String?,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onImageChange: () -> Unit,
    onClearImage: () -> Unit,
    onSubmit: () -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing_lg)
    ) {
        TextField(
            value = name,
            onValueChange = { newName ->
                onNameChange(newName)
            },
            label = { Text(stringResource(R.string.name)) },
            colors = customTextFieldColors(),
            shape = RoundedCornerShape(RoundedCorner.rounded_corner_md),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = phoneNumber,
            onValueChange = { newNumber ->
                onPhoneNumberChange(newNumber)
            },
            label = { Text(stringResource(R.string.phone_number)) },
            colors = customTextFieldColors(),
            shape = RoundedCornerShape(RoundedCorner.rounded_corner_md),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = email,
            onValueChange = { newEmail ->
                onEmailChange(newEmail)
            },
            label = { Text(stringResource(R.string.email)) },
            colors = customTextFieldColors(),
            shape = RoundedCornerShape(RoundedCorner.rounded_corner_md),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(Card.card_xxl)
                    .clickable { onImageChange() }
            ) {
                if (imageUri.isNullOrEmpty()) {
                    CustomIcon(
                        icon = R.drawable.icon_image,
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surface
                    )
                } else {
                    CustomImage(imageUri, modifier = Modifier.fillMaxSize())
                }
            }
            if (imageUri?.isNotEmpty() == true) {
                CustomIconButton(
                    icon = R.drawable.icon_close,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    onClearImage()
                }
            }
        }
        ButtonPrimary(
            text = stringResource(R.string.save),
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled,
        ) {
            onSubmit()
        }
    }
}