package com.example.catalogviewer.di

import com.example.catalogviewer.data.repository.CatalogRepositoryImpl
import com.example.catalogviewer.domain.repository.CatalogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(impl: CatalogRepositoryImpl): CatalogRepository
}