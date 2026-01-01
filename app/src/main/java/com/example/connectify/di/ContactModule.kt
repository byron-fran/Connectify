package com.example.connectify.di

import com.example.connectify.data.local.dao.ContactDao
import com.example.connectify.data.repositories.ContactRepositoryImpl
import com.example.connectify.domain.repositories.ContactRepository
import com.example.connectify.domain.useCases.ContactUseCases
import com.example.connectify.domain.useCases.DeleteAllContacts
import com.example.connectify.domain.useCases.DeleteContact
import com.example.connectify.domain.useCases.GetAllContactsUseCase
import com.example.connectify.domain.useCases.GetContactById
import com.example.connectify.domain.useCases.InsertContactUseCase
import com.example.connectify.domain.useCases.UpdateContact
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ContactModule {


    @Provides
    fun provideInsertContactUseCase(contactRepository: ContactRepository): ContactUseCases {
        return  ContactUseCases(
            insertContact = InsertContactUseCase(contactRepository),
            getAllContacts = GetAllContactsUseCase(contactRepository),
            getContactById = GetContactById(contactRepository),
            updateContact = UpdateContact(contactRepository),
            deleteContact = DeleteContact(contactRepository),
            deleteAllContacts = DeleteAllContacts(contactRepository)
        )
    }

    @Provides
    fun provideContactRepositoryImpl(contactDao : ContactDao): ContactRepository {
        return ContactRepositoryImpl(contactDao)
    }

}