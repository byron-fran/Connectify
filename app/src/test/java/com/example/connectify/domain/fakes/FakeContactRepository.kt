package com.example.connectify.domain.fakes

import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.repositories.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class FakeContactRepository : ContactRepository {
    private val contacts = MutableStateFlow<List<Contact>>(emptyList())
    var shouldThrowOnInsert = false
    var shouldThrowOnGetAll = false
    var shouldThrowOnGetById = false
    var shouldThrowOnUpdate = false
    var shouldThrowOnDelete = false

    override suspend fun insertContact(contact: Contact) {
        if(shouldThrowOnInsert) {
            throw Exception("Error inserting contact")
        }
        val currentList = contacts.value.toMutableList()
        currentList.add(contact)
        contacts.value = currentList
    }

    override fun getAllContacts(): Flow<List<Contact>> = contacts
        .onStart {
            if (shouldThrowOnGetAll) {
                throw Exception("Error fetching contacts")
            }
        }
        .map { contactList ->
            if (shouldThrowOnGetAll) {
                throw Exception("Error fetching contacts")
            }
            contactList
        }

    override fun getContactById(id: String): Flow<Contact?> = contacts.map { list ->
        if (shouldThrowOnGetById) {
            throw Exception("Error getting contact by id")
        }
        list.find { it.id == id }
    }

    override suspend fun deleteContactById(contact: Contact) {
        if(shouldThrowOnDelete) {
            throw Exception("Error deleting contact")
        }
        val currentList = contacts.value.toMutableList()
        currentList.removeAll { it.id == contact.id }
        contacts.value = currentList
    }

    override suspend fun updateContact(contact: Contact) {
        if(shouldThrowOnUpdate) {
            throw Exception("Error updating contact")
        }
        val currentList = contacts.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == contact.id }
        if (index != -1) {
            currentList[index] = contact
            contacts.value = currentList
        }
    }

    override suspend fun deleteAllContacts() {
        if(shouldThrowOnDelete) {
            throw Exception("Error deleting contacts")
        }
        contacts.value = emptyList()
    }

    fun clear() {
        contacts.value = emptyList()
        shouldThrowOnInsert = false
        shouldThrowOnGetAll = false
        shouldThrowOnGetById = false
        shouldThrowOnUpdate = false
        shouldThrowOnDelete = false

    }
}