package com.example.connectify.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.connectify.data.local.database.ConnectifyDatabase
import com.example.connectify.data.local.entities.ContactEntity
import java.io.IOException
import java.util.UUID
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchDaoTest {
    private val contactId1: String = UUID.randomUUID().toString()
    private val contactId2: String = UUID.randomUUID().toString()
    private val contactOne = ContactEntity(
        id = contactId1,
        name = "Byron",
        phoneNumber = 123456789,
        email = "byron99@gmail.com",
    )
    private val contactTwo = ContactEntity(
        id = contactId2,
        name = "Francisco",
        phoneNumber = 987654321,
        email = "francisco99@gmail.com",
    )

    private val contactsExpected = listOf(contactOne)

    private lateinit var searchDao: SearchDao
    private lateinit var contactDao: ContactDao
    private lateinit var connectifyDatabase: ConnectifyDatabase
    private suspend fun addContacts() {
        contactDao.insertContact(contactOne)
        contactDao.insertContact(contactTwo)
    }

    @Before
    fun createDB() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        connectifyDatabase =
            Room.inMemoryDatabaseBuilder(context, ConnectifyDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        searchDao = connectifyDatabase.searchDao()
        contactDao = connectifyDatabase.contactDao()
    }

    @Test
    @Throws(Exception::class)
    fun searchContact_byName() = runBlocking {
        addContacts()
        val contactsSearched = searchDao.searchContacts("byron").first()
        assertEquals(contactsExpected, contactsSearched)
    }

    @Test
    @Throws(Exception::class)
    fun searchContact_byPhoneNumber() = runBlocking {
        addContacts()
        val contactsSearched = searchDao.searchContacts("123456789").first()
        assertEquals(contactsExpected, contactsSearched)
    }

    @Test
    @Throws(Exception::class)
    fun searchContact_byEmail() = runBlocking {
        addContacts()
        val contactsSearched = searchDao.searchContacts("byron99").first()
        assertEquals(contactsExpected, contactsSearched)
    }

    @Test
    @Throws(Exception::class)
    fun searchContact_caseInsensitive() = runBlocking {
        addContacts()
        val contactsSearched = searchDao.searchContacts("BYRON").first()
        assertEquals(contactsExpected, contactsSearched)
    }

    @Test
    @Throws(Exception::class)
    fun searchContact_noResults() = runBlocking {
        addContacts()
        val contactsSearched = searchDao.searchContacts("Unknown").first()
        assertEquals(emptyList<ContactEntity>(), contactsSearched)
    }

    @Test
    @Throws(Exception::class)
    fun searchContact_partialMatch() = runBlocking {
        addContacts()
        val contactsSearched = searchDao.searchContacts("fran").first()
        assertEquals(listOf(contactTwo), contactsSearched)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        connectifyDatabase.close()
    }
}
