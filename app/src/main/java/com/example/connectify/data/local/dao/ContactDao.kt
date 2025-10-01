package com.example.connectify.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.connectify.data.local.entities.ContactEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE id = :id")
    fun getContactById(id: String): Flow<ContactEntity?>

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Update
    suspend fun updateContact(contact: ContactEntity)

}