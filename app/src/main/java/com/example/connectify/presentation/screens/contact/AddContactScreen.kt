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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.contact.ContactForm
import com.example.connectify.presentation.components.contact.ContactFormImage
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.states.ContactUiEvent
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.FileUtils
import com.example.connectify.utils.Tag.ADD_CONTACT_SCREEN

@Composable
fun AddContactScreen(
    onEvent: (ContactUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
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


    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = { TitleMedium(stringResource(id = R.string.add_contact)) }
            ) {
                onNavigateBack()
            }
        },
        modifier = Modifier.testTag(ADD_CONTACT_SCREEN)
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            ContactFormImage(
                imageUri = imageUri?.toString(),
                onImageChange = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onClearImage = { imageUri = null }
            )
            ContactForm(
                name = name,
                phoneNumber = phoneNumber,
                email = email,
                isButtonEnabled = validateContact(name, phoneNumber),
                onNameChange = { name = it },
                onPhoneNumberChange = { phoneNumber = it },
                onEmailChange = { email = it },
                modifier = Modifier.padding(
                    horizontal = Spacing.spacing_sm,
                    vertical = Spacing.spacing_lg
                )
            ) {
                onEvent(
                    ContactUiEvent.InsertContact(
                        Contact(
                            name = name,
                            email = email,
                            phoneNumber = phoneNumber,
                            imageUrl = imageUri?.toString()
                        )

                    )
                )
                onNavigateBack()
                toast.show()
            }
        }
    }
}

internal fun validateContact(name: String, phoneNumber: String): Boolean {
    return name.trim().isNotEmpty() && phoneNumber.trim().isNotEmpty()
}