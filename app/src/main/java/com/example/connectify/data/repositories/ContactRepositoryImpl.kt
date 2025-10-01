package com.example.connectify.data.repositories

import com.example.connectify.data.local.dao.ContactDao
import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.repositories.ContactRepository
import com.example.connectify.utils.toContact
import com.example.connectify.utils.toContactEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepositoryImpl(private val contactDao: ContactDao) : ContactRepository {

    override suspend fun insertContact(contact: Contact) {
        contactDao.insertContact(contact.toContactEntity())
    }

    override fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts().map { ls ->
            ls.map { it.toContact() }
        }
    }

    override fun getContactById(id: String): Flow<Contact?> {
        return contactDao.getContactById(id).map { it?.toContact() }
    }

    override suspend fun deleteContactById(contact:  Contact) {
        contactDao.deleteContact(contact.toContactEntity())
    }

    override suspend fun updateContact(contact : Contact) {
        contactDao.updateContact(contact.toContactEntity())
    }
}

