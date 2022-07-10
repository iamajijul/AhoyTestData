package com.ajijul.ahoytestdata.di

import android.content.Context
import com.ajijul.ahoytestdata.store.DataStoreRepository
import com.ajijul.ahoytestdata.store.DataStoreRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context
    ): DataStoreRepository = DataStoreRepositoryImplementation(app)

}