package com.example.coffeememos.viewModel

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.search.*
import com.example.coffeememos.search.domain.model.RecipeSortType
import com.example.coffeememos.search.presentation.controller.SearchRecipeController
import com.example.coffeememos.utilities.ViewUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipeViewModel @Inject constructor(val recipeDao: RecipeDao) : ViewModel() {
    @Inject
    lateinit var searchRecipeController: SearchRecipeController

    // 検索結果
    private val _searchResult: MutableLiveData<List<SearchRecipeModel>> = MutableLiveData(listOf())

    val recipeCount: LiveData<Int> = _searchResult.map { list ->
        return@map list.size
    }

    // Sort 状態
    private val _currentSortType: MutableLiveData<RecipeSortType> = MutableLiveData(RecipeSortType.NEW)
    val currentSortType: LiveData<RecipeSortType> = _currentSortType

    val sortedSearchResult: MutableLiveData<List<SearchRecipeModel>> = MediatorLiveData<List<SearchRecipeModel>>().apply {
        // 検索結果が更新されたら、ソート
        addSource(_searchResult) { searchResult ->
            value = searchRecipeController.sortRecipe(_currentSortType.value!!, searchResult)
        }
        // 現在のソートが更新されたら、ソート
        addSource(_currentSortType) { sortType ->
            value = searchRecipeController.sortRecipe(sortType, _searchResult.value!!)
        }
    }

    // filter 管理
    lateinit var filterManager: SearchFilterManager

    // BottomSheet 状態監視
    private val _isOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpened: LiveData<Boolean> = _isOpened

    fun changeBottomSheetState() {
        _isOpened.value = !(_isOpened.value!!)
    }

    // キーワード検索
    fun freeWordSearch(keyWord: SearchKeyWord) {
        viewModelScope.launch {
            val result: List<SearchRecipeModel> =
                searchRecipeController.freeWordSearch(keyWord) ?:
                return@launch

            _searchResult.postValue(result)
        }
    }

    // sortセット
    fun setCurrentSortType(sortType: RecipeSortType) {
        _currentSortType.value = sortType
    }

    fun updateFavoriteIcon(clickedFavoriteIcon: View, recipeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (clickedFavoriteIcon.tag.equals(ViewUtil.IS_FAVORITE_TAG_NAME)) {
                // db更新
                recipeDao.updateFavoriteByRecipeId(recipeId, false)
                // view 更新
                if (clickedFavoriteIcon is ImageView) ViewUtil.setTagAndFavoriteIcon(clickedFavoriteIcon, false)
            } else {
                // db更新
                recipeDao.updateFavoriteByRecipeId(recipeId, true)
                // view 更新
                if (clickedFavoriteIcon is ImageView) ViewUtil.setTagAndFavoriteIcon(clickedFavoriteIcon, true)
            }
        }
    }

    // filter
    fun filterSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                filterManager.searchAndFilter()
            )
        }
    }

    fun resetResult() {
        filterManager = SearchFilterManager(recipeDao, CustomRecipeMapper())
        _currentSortType.value = RecipeSortType.NEW
        initSearchResult()
    }

    init {
        filterManager = SearchFilterManager(recipeDao, CustomRecipeMapper())

        initSearchResult()
    }

    private fun initSearchResult() {
        viewModelScope.launch {
            _searchResult.postValue(
                filterManager.initSearchResult()
            )
        }
    }


    class SearchRecipeViewModelFactory(
        private val recipeDao: RecipeDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchRecipeViewModel::class.java)) {
                return SearchRecipeViewModel(recipeDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_HOMEVIEWMODEL")
        }
    }
}