package com.example.connectify.presentation.screens.contact

import app.cash.turbine.test
import com.example.connectify.domain.fakes.FakeContactRepository
import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.useCases.ContactUseCases
import com.example.connectify.domain.useCases.DeleteContact
import com.example.connectify.domain.useCases.GetAllContactsUseCase
import com.example.connectify.domain.useCases.GetContactById
import com.example.connectify.domain.useCases.InsertContactUseCase
import com.example.connectify.domain.useCases.UpdateContact
import com.example.connectify.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class ContactViewModelTest {
    private val contactId1 = UUID.randomUUID().toString()
    private val contactId2 = UUID.randomUUID().toString()

    private val contactOne = Contact(
        id = contactId1,
        name = "juan",
        email = "juan@gmail.com"
    )
    private val contactTwo = Contact(
        id = contactId2,
        name = "byron",
        email = "byron@gmail.com"
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ContactViewModel
    private lateinit var fakeRepository: FakeContactRepository
    private lateinit var contactUseCases: ContactUseCases

    private suspend fun insertContacts(vararg contacts: Contact) {
        fakeRepository.shouldThrowOnInsert = false
        contacts.forEach { viewModel.insertContact(it) }
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
    }

    @Before
    fun setup() {

        fakeRepository = FakeContactRepository()

        contactUseCases =
            ContactUseCases(
                insertContact = InsertContactUseCase(fakeRepository),
                getAllContacts = GetAllContactsUseCase(fakeRepository),
                getContactById = GetContactById(fakeRepository),
                updateContact = UpdateContact(fakeRepository),
                deleteContact = DeleteContact(fakeRepository)
            )

        viewModel = ContactViewModel(contactUseCases)
        fakeRepository.clear()

        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun contactViewModel_initialization_notNull() {
        assertNotNull(viewModel)
    }

    @Test
    fun viewModel_getContactById_returnCorrectContact() = runTest {

        insertContacts(contactOne)
        viewModel.getContactById(contactId1)

        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.contactState.value
        assertEquals(contactOne, currentState.contact)
    }

    @Test
    fun contactViewModel_insertContact() = runTest {
        viewModel.contactState.test {
            assertEquals(emptyList<Contact>(), awaitItem().contacts)
            insertContacts(contactOne)
            assertEquals(listOf(contactOne), awaitItem().contacts)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun contactViewModel_updateContact() = runTest {

        insertContacts(contactOne, contactTwo)
        val contactUpdated = contactTwo.copy(
            name = "Juanito",
            email = "juanito99@gmail.com"
        )
        viewModel.updateContact(contactUpdated)
        val currentState = viewModel.contactState.value
        assertEquals(listOf(contactOne, contactUpdated), currentState.contacts)

    }

    @Test
    fun contactViewModel_getAllContacts() = runTest {

        insertContacts(contactOne, contactTwo)
        val currentState = viewModel.contactState.value
        assertEquals(listOf(contactOne, contactTwo), currentState.contacts)
    }

    @Test
    fun contactViewModel_getEmptyListWhenNoContacts() = runTest {
        val currentState = viewModel.contactState.value
        assertEquals(emptyList<Contact>(), currentState.contacts)
    }

    @Test
    fun contactViewModel_getEmptyContactWhenContactNotFound() = runTest {
        val currentState = viewModel.contactState.value
        assertEquals(null, currentState.contact)
    }

    @Test
    fun contactViewModel_deleteContactById() = runTest {

        insertContacts(contactOne, contactTwo)
        viewModel.deleteContact(contactTwo)
        val currentState = viewModel.contactState.value
        assertEquals(listOf(contactOne), currentState.contacts)
    }

    @Test
    fun contactViewModel_deleteAllContacts() = runTest {

        insertContacts(contactOne, contactTwo)
        viewModel.deleteContact(contactOne)
        viewModel.deleteContact(contactTwo)
        assertEquals(
            emptyList<Contact>(),
            viewModel.contactState.value.contacts
        )

    }

    @Test
    fun contactViewModel_toggleFavorite() = runTest {

        insertContacts(contactOne)
        val contactUpdated = contactOne.copy(isFavorite = true)
        viewModel.toggleFavorite(contactOne)
        val currentState = viewModel.contactState.value
        assertEquals(contactUpdated, currentState.contacts[0])
    }


    @Test
    fun contactViewModel_insertContact_setErrorOnFailure() = runTest {
        val expectedErrorMessage = "Error inserting contact"
        fakeRepository.shouldThrowOnInsert = true
        val contactFalse = Contact()

        viewModel.insertContact(contactFalse)
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedErrorMessage, viewModel.contactState.value.error)
        assertEquals(emptyList<Contact>(), viewModel.contactState.value.contacts)
    }

    @Test
    fun contactViewModel_getAllContacts_setErrorOnFailure() = runTest {
        val expectedErrorMessage = "Error fetching contacts"
        
        fakeRepository.shouldThrowOnGetAll = true
        
        val tempContact = Contact()
        fakeRepository.shouldThrowOnInsert = false

        fakeRepository.insertContact(tempContact)
        fakeRepository.shouldThrowOnDelete = false
        fakeRepository.deleteContactById(tempContact)
        
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(emptyList<Contact>(), viewModel.contactState.value.contacts)
        assertEquals(expectedErrorMessage, viewModel.contactState.value.error)

    }

    @Test
    fun contactViewModel_getContactById_setErrorOnFailure() = runTest {
        // Arrange
        val expectedErrorMessage = "Error getting contact by id"
        insertContacts(contactOne)
        fakeRepository.shouldThrowOnGetById = true

        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        // Act
        viewModel.getContactById("")
        // Assert
        assertEquals(expectedErrorMessage, viewModel.contactState.value.error)
        assertEquals(null, viewModel.contactState.value.contact)
    }

    @Test
    fun contactViewModel_updateContact_setErrorOnFailure() = runTest {
        // Arrange
        val expectedErrorMessage = ("Error updating contact")
        insertContacts(contactOne, contactTwo)
        val contactToUpdate = contactTwo.copy(name = "Juanito updated")
        fakeRepository.shouldThrowOnUpdate = true
        // Act
        viewModel.updateContact(contactToUpdate)
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        // Assert
        assertEquals(expectedErrorMessage, viewModel.contactState.value.error)
        assertEquals(listOf(contactOne, contactTwo), viewModel.contactState.value.contacts)
    }

    @Test
    fun contactViewModel_deleteContact_setErrorOnFailure() = runTest {
        // Arrange
        val expectedErrorMessage = "Error deleting contact"
        insertContacts(contactOne)
        fakeRepository.shouldThrowOnDelete = true
        // Act
        viewModel.deleteContact(contactOne)
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        // Assert
        assertEquals(expectedErrorMessage, viewModel.contactState.value.error)
        assertEquals(listOf(contactOne), viewModel.contactState.value.contacts)
    }

    @Test
    fun contactViewModel_toggleFavorite_revertsOptimisticUpdatedOnFailure() = runTest {

        insertContacts(contactOne)
        val initContact = viewModel.contactState.value.contacts.first()
        assertFalse(initContact.isFavorite)

        fakeRepository.shouldThrowOnUpdate = true

        viewModel.toggleFavorite(initContact)
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.togglingFavoriteId.value)

        val finalContact = viewModel.contactState.value.contacts.first()
        assertEquals(false, finalContact.isFavorite)
    }
}