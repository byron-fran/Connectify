package com.example.connectify.presentation.screens.contact

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactForm
import com.example.connectify.presentation.components.global.ConnectifyToAppBar

@Composable
fun ContactEditScreen(
    contactId: String?,
    contactViewModel: ContactViewModel = hiltViewModel<ContactViewModel>(),
    onNavigateBack: () -> Unit
) {

    val contactState = contactViewModel.contactState.collectAsState().value
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    LaunchedEffect(contactState.contact) {
        contactState.contact?.let {
            name = it.name
            email = it.email ?: ""
            phoneNumber = it.phoneNumber.toString()
        }
    }

    LaunchedEffect(contactId) {
        contactId?.let {
            contactViewModel.getContactById(contactId)

        }
    }

    Scaffold(
        topBar = {
            ConnectifyToAppBar(title = stringResource(R.string.edit_contact)) {
                onNavigateBack()
            }
        }
    ) { paddingValues ->
        contactState.contact?.let {
            ContactForm(
                modifier = Modifier.padding(paddingValues),
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
                    phoneNumber = phoneNumber.toLong()
                )
                contactViewModel.updateContact(updatedContact)
                onNavigateBack()
            }
        }
    }
}