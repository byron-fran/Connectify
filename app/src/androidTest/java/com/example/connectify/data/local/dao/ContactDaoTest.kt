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
        phoneNumber = 753433,
        email = "byron99@gmail.com",
    )
    private val contact2 = ContactEntity(
        id = contactId2,
        name = "Francisco",
        phoneNumber = 44422323,
        email = "francisco99@gmail.com",
    )

    private lateinit var contactDao: ContactDao
    private lateinit var connectifyDatabase: ConnectifyDatabase

    suspend fun addOneContact() {
        contactDao.insertContact(contact1)
    }

    suspend fun addTwoContacts() {
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

        assertEquals(allContacts[0], contact1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllContacts_returnsAllContactsFromDB() = runBlocking {

        addTwoContacts()
        val allContacts = contactDao.getAllContacts().first()

        assertEquals(allContacts[0], contact1)
        assertEquals(allContacts[1], contact2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateContacts_updatesContactsInDB() = runBlocking {

        addTwoContacts()
        contactDao.updateContact( ContactEntity( id = contactId1, name = "Byron Chanax"))
        contactDao.updateContact(ContactEntity(id = contactId2, name = "Francisco Itzep"))

        val allContacts = contactDao.getAllContacts().first()
        assertEquals(allContacts[0], ContactEntity(id = contactId1, name = "Byron Chanax"))
        assertEquals(allContacts[1], ContactEntity(id = contactId2, name = "Francisco Itzep"))
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
        assertEquals(contact, contact1)
    }
}