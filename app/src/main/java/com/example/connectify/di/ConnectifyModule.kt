package com.example.connectify.di

import android.content.Context
import androidx.room.Room
import com.example.connectify.data.local.dao.ContactDao
import com.example.connectify.data.local.database.ConnectifyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectifyModule {

    @Provides
    @Singleton
    fun provideConnectifyDB(@ApplicationContext applicationContext: Context): ConnectifyDatabase {
        return Room.databaseBuilder(
            applicationContext,
            klass = ConnectifyDatabase::class.java,
            name = "connectify_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()

    }

    @Singleton
    @Provides
    fun provideContactDao(connectifyDataBase: ConnectifyDatabase) : ContactDao {
        return connectifyDataBase.contactDao()
    }

}