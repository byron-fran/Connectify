package com.example.connectify.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.connectify.data.local.dao.ContactDao
import com.example.connectify.data.local.dao.SearchDao
import com.example.connectify.data.local.entities.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 2
)
abstract class ConnectifyDatabase : RoomDatabase() {
    abstract fun contactDao() : ContactDao
    abstract fun searchDao() : SearchDao

}
