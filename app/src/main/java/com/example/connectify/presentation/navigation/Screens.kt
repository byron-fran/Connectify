package com.example.connectify.presentation.navigation

import kotlinx.serialization.Serializable

object Screens {

    @Serializable
    object Search


    @Serializable
    object Contacts

    @Serializable
    data class ContactDetail(val contactId : String)

}