package com.example.connectify.data.local.database

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.connectify.data.local.dao.ContactDao
import com.example.connectify.data.local.entities.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 2
)
abstract class ConnectifyDatabase : RoomDatabase() {
    abstract fun contactDao() : ContactDao

}
