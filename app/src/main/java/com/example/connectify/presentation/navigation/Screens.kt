package com.example.connectify.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    object Search : Screens()

    @Serializable
    object Contacts : Screens()

    @Serializable
    data class ContactDetail(val contactId : String) : Screens()

    @Serializable
    object AddContact : Screens()

    @Serializable
    data class EditContact(val contactId : String) : Screens()
}