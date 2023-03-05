package com.example.coffeememos.di.search.recipe.use_case

import com.example.coffeememos.search.recipe.domain.iterator.FilterRecipeIterator
import com.example.coffeememos.search.recipe.domain.use_case.FilterRecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FilterSearchRecipeUseCaseModule {

    @Binds
    abstract fun bindFilterSearchRecipeUseCase(
        filterSearchUseCaseImpl: FilterRecipeIterator
    ): FilterRecipeUseCase
}