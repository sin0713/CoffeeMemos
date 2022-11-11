package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.Taste
import com.example.coffeememos.manager.RatingManager
import com.example.coffeememos.state.NewRecipeMenuState
import com.example.coffeememos.utilities.Util
import kotlinx.coroutines.launch

class NewRecipeViewModel(
    private val recipeDao: RecipeDao,
    private val beanDao: BeanDao,
    private val tasteDao: TasteDao
) : ViewModel() {
    // お気に入り
    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setFavoriteFlag(flag: Boolean) {
        _isFavorite.value = flag
    }


    // Rating 関連
    private var _ratingManager: RatingManager? = null
    val ratingManager: RatingManager
        get() = _ratingManager!!

    private var _recipeCurrentRating: MutableLiveData<Int> = MutableLiveData(1)
    val recipeCurrentRating: LiveData<Int> = _recipeCurrentRating

    private val _recipeStarList: MutableLiveData<List<RatingManager.Star>> = MutableLiveData(listOf())
    val recipeStarList: LiveData<List<RatingManager.Star>> = _recipeStarList

    fun updateRatingState(selectedRate: Int) {
        ratingManager.changeRatingState(selectedRate)

        _recipeCurrentRating.value = ratingManager.currentRating
        _recipeStarList.value      = ratingManager.starList
    }


    // Roast
    private val _currentRoast: MutableLiveData<Int> = MutableLiveData(0)
    val currentRoast: LiveData<Int> = _currentRoast

    fun setRoast(roast: Int) {
        _currentRoast.value = roast
    }


    // Grind Size
    private val _currentGrind: MutableLiveData<Int> = MutableLiveData(0)
    val currentGrind: LiveData<Int> = _currentGrind

    fun setGrind(grind: Int) {
        _currentGrind.value = grind
    }

    // 更新データ保持
    private var _sour                 : Int = 1
    private var _bitter               : Int = 1
    private var _sweet                : Int = 1
    private var _rich                 : Int = 1
    private var _flavor               : Int = 1
    private var _amountBeans          : Int = 0
    private var _temperature          : Int = 0
    private var _preInfusionTime      : Int = 0
    private var _extractionTimeMinutes: Int = 0
    private var _extractionTimeSeconds: Int = 0
    private var _amountExtraction     : Int = 0
    private var _tool                 : String = ""
    private var _comment              : String = ""

    fun setSour(sour: String)                                   { _sour =  Util.convertStringIntoIntIfPossible(sour) }
    fun setBitter(bitter: String)                               { _bitter =  Util.convertStringIntoIntIfPossible(bitter) }
    fun setSweet(sweet: String)                                 { _sweet =  Util.convertStringIntoIntIfPossible(sweet) }
    fun setFlavor(flavor: String)                               { _flavor = Util.convertStringIntoIntIfPossible(flavor) }
    fun setRich(rich: String)                                   { _rich = Util.convertStringIntoIntIfPossible(rich) }
    fun setAmountBeans(amountBeans: String)                     { _amountBeans = Util.convertStringIntoIntIfPossible(amountBeans) }
    fun setTemperature(temperature: String)                     { _temperature = Util.convertStringIntoIntIfPossible(temperature) }
    fun setPreInfusionTime(preInfusionTime: String)             { _preInfusionTime = Util.convertStringIntoIntIfPossible(preInfusionTime) }
    fun setExtractionTimeMinutes(extractionTimeMinutes: String) {_extractionTimeMinutes = Util.convertStringIntoIntIfPossible(extractionTimeMinutes) }
    fun setExtractionTimeSeconds(extractionTimeSeconds: String) {_extractionTimeSeconds = Util.convertStringIntoIntIfPossible(extractionTimeSeconds) }
    fun setAmountExtraction(amountExtraction: String)           {_amountExtraction = Util.convertStringIntoIntIfPossible(amountExtraction) }
    fun setTool(tool: String)                                   { _tool = tool }
    fun setComment(comment: String)                             { _comment = comment }


    /**
     * menuの状態管理フラグ
     */
    private var _isMenuOpened: MutableLiveData<NewRecipeMenuState> = MutableLiveData(NewRecipeMenuState.MENU_CLOSED)
    val isMenuOpened: LiveData<NewRecipeMenuState> = _isMenuOpened

    fun setMenuOpenedFlag(menuState: NewRecipeMenuState) {
        _isMenuOpened.value = menuState
    }


    // viewModel 初期化処理
    fun initialize(ratingManager: RatingManager) {
        if (_ratingManager != null) return
        _ratingManager = ratingManager
    }


    // 保存処理
    fun createNewRecipeAndTaste(beanId: Long) {
        viewModelScope.launch {
            // レシピ保存
            val createdAt = System.currentTimeMillis()
            recipeDao.insert(
                Recipe(
                    id                    = 0,
                    beanId                = beanId,
                    tool                  = _tool,
                    roast                 = _currentRoast.value ?: 2,
                    extractionTimeMinutes = _extractionTimeMinutes,
                    extractionTimeSeconds = _extractionTimeSeconds,
                    preInfusionTime       = _preInfusionTime,
                    amountExtraction      = _amountExtraction,
                    temperature           = _temperature,
                    grindSize             = _currentGrind.value ?: 3,
                    amountOfBeans         = _amountBeans,
                    comment               = _comment,
                    isFavorite            = _isFavorite.value ?: false,
                    rating                = _recipeCurrentRating.value!!,
                    createdAt             = createdAt
                )
            )

            // 上で保存したレシピのIDを取得
            val newestRecipeId = recipeDao.getNewestRecipeId()
            tasteDao.insert(
                Taste(
                    id       = 0,
                    recipeId = newestRecipeId,
                    sour     = _sour,
                    bitter   = _bitter,
                    sweet    = _sweet,
                    rich     = _rich ,
                    flavor   = _flavor
                )
            )
        }
    }
}

class NewRecipeViewModelFactory(
    private val recipeDao:RecipeDao,
    private val beanDao: BeanDao,
    private val tasteDao: TasteDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewRecipeViewModel::class.java)) {
                return NewRecipeViewModel(recipeDao, beanDao, tasteDao) as T
            }
            throw IllegalArgumentException("CANNOT_GET_NEWRECIPEVIEWMODEL")
        }
    }

