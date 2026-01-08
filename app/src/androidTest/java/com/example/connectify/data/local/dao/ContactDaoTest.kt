package com.example.connectify.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.connectify.data.local.database.ConnectifyDatabase
import com.example.connectify.data.local.entities.ContactEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class ContactDaoTest {

    private val contactId1: String = UUID.randomUUID().toString()
    private val contactId2: String = UUID.randomUUID().toString()

    private val contact1 = ContactEntity(
        id = contactId1,
        name = "Byron",
        phoneNumber = "753433",
        email = "byron99@gmail.com",
    )
    private val contact2 = ContactEntity(
        id = contactId2,
        name = "Francisco",
        phoneNumber = "235346",
        email = "francisco99@gmail.com",
    )

    private lateinit var contactDao: ContactDao
    private lateinit var connectifyDatabase: ConnectifyDatabase

    private suspend fun addOneContact() {
        contactDao.insertContact(contact1)
    }

    private suspend fun addTwoContacts() {
        contactDao.insertContact(contact1)
        contactDao.insertContact(contact2)
    }


    @Before
    fun createDB() {
        val context: Context = ApplicationProvider.getApplicationContext()

        connectifyDatabase = Room
            .inMemoryDatabaseBuilder(context, ConnectifyDatabase::class.java)
            .allowMainThreadQueries()
            .build()


        contactDao = connectifyDatabase.contactDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        connectifyDatabase.close()
    }


    @Test
    @Throws(Exception::class)
    fun daoInsertContact_insertsContactIntoDB() = runBlocking {

        addOneContact()
        val allContacts = contactDao.getAllContacts().first()

        assertEquals(1, allContacts.size)
        assertEquals(contact1, allContacts[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllContacts_returnsAllContactsFromDB() = runBlocking {

        addTwoContacts()
        val allContacts = contactDao.getAllContacts().first()

        assertEquals(2, allContacts.size)
        assertEquals(contact1, allContacts[0])
        assertEquals(contact2, allContacts[1])
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateContacts_updatesContactsInDB() = runBlocking {

        addTwoContacts()
        
        val updatedContact1 = contact1.copy(name = "Byron Chanax")
        val updatedContact2 = contact2.copy(name = "Francisco Itzep")
        
        contactDao.updateContact(updatedContact1)
        contactDao.updateContact(updatedContact2)

        val allContacts = contactDao.getAllContacts().first()
        assertEquals(2, allContacts.size)
        assertEquals(updatedContact1, allContacts.find { it.id == contactId1 })
        assertEquals(updatedContact2, allContacts.find { it.id == contactId2 })
        
        val retrievedContact1 = allContacts.find { it.id == contactId1 }
        assertEquals(contact1.phoneNumber, retrievedContact1?.phoneNumber)
        assertEquals(contact1.email, retrievedContact1?.email)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteContacts_deletesAllContactsFromDB() = runBlocking {

        addTwoContacts()
        contactDao.deleteContact(contact1)
        contactDao.deleteContact(contact2)

        val allContacts = contactDao.getAllContacts().first()

        assertTrue(allContacts.isEmpty())

    }

    @Test
    @Throws(Exception::class)
    fun daoGetContact_returnsContactFromDB() = runBlocking {

        addOneContact()
        val contact = contactDao.getContactById(contactId1).first()
        
        assertEquals(contact1, contact)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetContact_returnsNullWhenContactNotFound() = runBlocking {
        val nonExistentId = UUID.randomUUID().toString()
        val contact = contactDao.getContactById(nonExistentId).first()
        
        assertEquals(null, contact)
    }
}