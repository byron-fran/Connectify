package com.example.connectify.domain.repositories

import com.example.connectify.domain.models.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun insertContact (contact : Contact)

    fun getAllContacts() : Flow<List<Contact>>

    fun getContactById(id : String) : Flow<Contact?>

    suspend fun deleteContactById(contact: Contact)

    suspend fun updateContact(contact: Contact)

    suspend fun deleteAllContacts()

}