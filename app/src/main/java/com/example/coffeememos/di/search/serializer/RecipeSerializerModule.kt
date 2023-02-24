package com.example.coffeememos.di.search.serializer

import com.example.coffeememos.search.recipe.domain.serialization.RecipeSerializer
import com.example.coffeememos.search.recipe.domain.serialization.RecipeSerializerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecipeSerializerModule {

    @Binds
    abstract fun bindRecipeSerializer(
        recipeSerializerImpl: RecipeSerializerImpl
    ): RecipeSerializer
}