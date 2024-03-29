package com.withapp.coffeememo.di.search.bean.repository

import com.withapp.coffeememo.search.bean.data.repository.SearchBeanDiskRepositoryImpl
import com.withapp.coffeememo.search.bean.domain.repository.SearchBeanDiskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchBeanRepositoryModule {

    @Binds
    abstract fun bindSearchBeanRepository(
        SearchBeanRepositoryImpl: SearchBeanDiskRepositoryImpl
    ): SearchBeanDiskRepository
}