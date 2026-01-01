package com.example.connectify.domain.useCases

import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.repositories.ContactRepository
import kotlinx.coroutines.flow.Flow

class InsertContactUseCase(private val contactRepository: ContactRepository) {

    suspend operator fun invoke(contact: Contact) {
        contactRepository.insertContact(contact)
    }

}

class GetAllContactsUseCase(
    private val contactRepository: ContactRepository
) {
    operator fun invoke(): Flow<List<Contact>> {
        return contactRepository.getAllContacts()

    }

}

class GetContactById(
    private val contactRepository: ContactRepository
) {
    operator fun invoke(id: String): Flow<Contact?> {
        return contactRepository.getContactById(id)
    }

}


class UpdateContact(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        contactRepository.updateContact(contact)
    }
}


class DeleteContact(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        contactRepository.deleteContactById(contact)
    }
}

class DeleteAllContacts(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke() {
        contactRepository.deleteAllContacts()
    }
}

data class ContactUseCases (
    val insertContact: InsertContactUseCase,
    val getAllContacts: GetAllContactsUseCase,
    val getContactById: GetContactById,
    val updateContact: UpdateContact,
    val deleteContact: DeleteContact,
    val deleteAllContacts: DeleteAllContacts
)