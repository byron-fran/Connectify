package com.example.connectify.presentation.screens.contact

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactForm
import com.example.connectify.presentation.components.contact.ContactFormImage
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.states.ContactUiEvent
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.FileUtils

@Composable
fun ContactEditScreen(
    contactId: String?,
    onEvent : (ContactUiEvent) -> Unit,
    contactUiState : ContactUIState,
    onNavigateBack: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val toast = Toast.makeText(context, R.string.successful_created, Toast.LENGTH_SHORT)
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val newUri = FileUtils.copiUtiToInternalStorage(context, uri)
                imageUri = newUri
            }
        }
    )
    LaunchedEffect(contactUiState.contact) {
        contactUiState.contact?.let {
            name = it.name
            email = it.email ?: ""
            phoneNumber = it.phoneNumber.toString()
            imageUri = it.imageUrl?.toUri()
        }
    }

    LaunchedEffect(contactId) {
        contactId?.let {
            onEvent(ContactUiEvent.GetContact(contactId))

        }
    }

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = { TitleMedium(stringResource(R.string.edit_contact)) }
            ) {
                onNavigateBack()
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            contactUiState.contact?.let {
                ContactFormImage(
                    imageUri = imageUri.toString(),
                    onImageChange = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    onClearImage = { imageUri = null }
                )
                ContactForm(
                    modifier = Modifier.padding(
                        vertical = Spacing.spacing_md,
                        horizontal = Spacing.spacing_sm
                    ),
                    name = name,
                    phoneNumber = phoneNumber,
                    email = email,
                    onNameChange = { newValue ->
                        name = newValue
                    },
                    onEmailChange = { newValue ->
                        email = newValue
                    },
                    onPhoneNumberChange = { newValue ->
                        phoneNumber = newValue
                    },
                    isButtonEnabled = validateContact(name, phoneNumber)
                ) {
                    val updatedContact = it.copy(
                        name = name,
                        email = email,
                        phoneNumber = phoneNumber.toLong(),
                        imageUrl = imageUri.toString()
                    )
                    onEvent(ContactUiEvent.UpdateContact(updatedContact))
                    onNavigateBack()
                    toast.show()
                }
            }
        }
    }
}