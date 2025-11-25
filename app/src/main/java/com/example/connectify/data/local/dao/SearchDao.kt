package com.example.connectify.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.connectify.data.local.entities.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("""
        SELECT * FROM contacts 
        WHERE name LIKE '%' || :query || '%' COLLATE NOCASE
           OR CAST(phone_number AS TEXT) LIKE '%' || :query || '%'
           OR email LIKE '%' || :query || '%' COLLATE NOCASE
    """)
    fun searchContacts(query : String) : Flow<List<ContactEntity>>


}