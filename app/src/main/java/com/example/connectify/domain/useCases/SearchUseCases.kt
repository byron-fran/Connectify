package com.example.connectify.domain.useCases

import com.example.connectify.domain.models.Contact
import com.example.connectify.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow


class GetSearchResultUseCases(
    private val searchRepository: SearchRepository
) {
     operator fun invoke(query : String) : Flow< List<Contact> >{
        return searchRepository.getSearchResult(query)
    }
}



data class SearchUseCases(
    val getSearchResult: GetSearchResultUseCases
)