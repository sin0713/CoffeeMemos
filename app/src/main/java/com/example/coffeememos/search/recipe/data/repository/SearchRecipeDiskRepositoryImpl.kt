package com.example.coffeememos.search.recipe.data.repository

import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.recipe.data.mapper.SearchRecipeModelMapper
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel
import com.example.coffeememos.search.recipe.domain.repository.SearchRecipeDiskRepository
import javax.inject.Inject

class SearchRecipeDiskRepositoryImpl @Inject constructor()
    : SearchRecipeDiskRepository {
    @Inject
    lateinit var recipeDao: RecipeDao
    @Inject
    lateinit var mapper: SearchRecipeModelMapper

    override suspend fun searchRecipeByFreeWord(freeWord: String): List<SearchRecipeModel> {
        val recipeWithTasteList = recipeDao.getRecipeWithTasteByKeyword(freeWord)

        return getSearchRecipeModelList(recipeWithTasteList)
    }

    override suspend fun getAllRecipe(): List<SearchRecipeModel> {
       val recipeWithTasteList = recipeDao.getRecipeWithTaste()
       return getSearchRecipeModelList(recipeWithTasteList)
    }

    override suspend fun updateFavorite(recipeId: Long, isFavorite: Boolean) {
        recipeDao.updateFavoriteByRecipeId(recipeId, isFavorite)
    }

    private fun getSearchRecipeModelList(
        recipeWithTasteList: List<RecipeWithTaste>
    ): List<SearchRecipeModel> {
        val result: MutableList<SearchRecipeModel> = mutableListOf()
        for (recipeWithTaste in recipeWithTasteList) {
            result.add(
                mapper.execute(recipeWithTaste)
            )
        }

        return result
    }
}