package com.example.connectify.data.repositories

import com.example.connectify.data.local.dao.SearchDao
import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.repositories.SearchRepository
import com.example.connectify.utils.toContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(private val searchDao: SearchDao) : SearchRepository {

    override  fun getSearchResult(query: String): Flow<List<Contact>> {
        return  searchDao.searchContacts(query).map { contactEntities ->
            contactEntities.map { it.toContact() }
        }
    }
}