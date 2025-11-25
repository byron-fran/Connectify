package com.example.connectify.domain.repositories

import com.example.connectify.domain.models.Contact
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

   fun getSearchResult(query : String) : Flow<List<Contact>>
}