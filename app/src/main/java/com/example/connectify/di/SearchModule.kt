package com.example.connectify.di

import com.example.connectify.data.local.dao.SearchDao
import com.example.connectify.data.repositories.SearchRepositoryImpl
import com.example.connectify.domain.repositories.SearchRepository
import com.example.connectify.domain.useCases.GetSearchResultUseCases
import com.example.connectify.domain.useCases.SearchUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {


    @Provides
    fun searchRepositoryImplProvider(searchDao: SearchDao) : SearchRepository {
        return SearchRepositoryImpl(searchDao)
    }

    @Provides
    fun searchUseCasesProvider(searchRepository: SearchRepository) : SearchUseCases {
        return SearchUseCases(
            getSearchResult = GetSearchResultUseCases(searchRepository)
        )
    }
}