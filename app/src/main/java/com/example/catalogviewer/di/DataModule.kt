package com.example.catalogviewer.di

import android.content.Context
import androidx.room.Room
import com.example.catalogviewer.data.local.AppDatabase
import com.example.catalogviewer.data.remote.JsonProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "catalog.db").build()
    }

    @Provides
    fun provideBookDao(db: AppDatabase) = db.bookDao()

    @Provides
    @Singleton
    fun provideJsonProvider(): JsonProvider {
        return JsonProvider()
    }
}
