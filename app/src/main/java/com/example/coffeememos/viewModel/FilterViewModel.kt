package com.example.coffeememos.viewModel

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.coffeememos.Constants
import com.example.coffeememos.R
import com.example.coffeememos.state.MenuState
import com.example.coffeememos.utilities.StringUtil

class FilterViewModel : ViewModel() {
    private val _roastMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val roastMenuState: LiveData<MenuState> = _roastMenuState

    fun setRoastState(isOpen: MenuState) {
        _roastMenuState.value = isOpen
    }

    private val _grindSizeMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val grindSizeMenuState: LiveData<MenuState> = _grindSizeMenuState

    fun setGrindSizeState(isOpen: MenuState) {
        _grindSizeMenuState.value = isOpen
    }

    private val _countryMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val countryMenuState: LiveData<MenuState> = _countryMenuState

    private val _inputCountriesText: MutableLiveData<String> = MutableLiveData("")
    val inputCountriesText: LiveData<String> = _inputCountriesText

    fun setCountryState(isOpen: MenuState) {
        _countryMenuState.value = isOpen
    }

    fun updateInputCountriesText(updatedList: List<String>){
        if (updatedList.isEmpty()) {
            _inputCountriesText.value = ""
            return
        }

        _inputCountriesText.value = updatedList.joinToString(", ")
    }

    private val _toolMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val toolMenuState: LiveData<MenuState> = _toolMenuState

    private val _inputToolsText: MutableLiveData<String> = MutableLiveData("")
    val inputToolsText: LiveData<String> = _inputToolsText

    fun setToolState(isOpen: MenuState) {
        _toolMenuState.value = isOpen
    }

    fun updateInputToolsText(updatedList: List<String>) {
        if (updatedList.isEmpty()) {
            _inputToolsText.value = ""
            return
        }

        _inputToolsText.value = updatedList.joinToString(", ")
    }

    private val _ratingMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val ratingMenuState: LiveData<MenuState> = _ratingMenuState

    private val _ratingRadioBtnState: MutableLiveData<MutableList<Boolean>> = MutableLiveData(MutableList(5) {false})
    val ratingRadioBtnState: LiveData<MutableList<Boolean>> = _ratingRadioBtnState

    val selectedRatingText: LiveData<String> = _ratingRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatValue(index) }
    }

    fun setRatingRadioBtnState(selectedIndex: Int) {
        _ratingRadioBtnState.value = updateList(selectedIndex, _ratingRadioBtnState.value!!)
    }

    fun setRatingMenuState(isOpen: MenuState) {
        _ratingMenuState.value = isOpen
    }

    private val _sourMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val sourMenuState: LiveData<MenuState> = _sourMenuState

    private val _sourRadioBtnState: MutableLiveData<MutableList<Boolean>> = MutableLiveData(MutableList(5) {false})
    val sourRadioBtnState: LiveData<MutableList<Boolean>> = _sourRadioBtnState

    val selectedSourText: LiveData<String> = _sourRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatValue(index) }
    }

    fun setSourRadioBtnState(selectedIndex: Int) {
        _sourRadioBtnState.value = updateList(selectedIndex, _sourRadioBtnState.value!!)
    }

    fun setSourState(isOpen: MenuState) {
        _sourMenuState.value = isOpen
    }

    private val _bitterMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val bitterMenuState: LiveData<MenuState> = _bitterMenuState

    private val _bitterRadioBtnState: MutableLiveData<MutableList<Boolean>> = MutableLiveData(MutableList(5) {false})
    val bitterRadioBtnState: LiveData<MutableList<Boolean>> = _bitterRadioBtnState

    val selectedBitterText: LiveData<String> = _bitterRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatValue(index) }
    }

    fun setBitterRadioBtnState(selectedIndex: Int) {
        _bitterRadioBtnState.value = updateList(selectedIndex, _bitterRadioBtnState.value!!)
    }

    fun setBitterState(isOpen: MenuState) {
        _bitterMenuState.value = isOpen
    }

    private val _sweetMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val sweetMenuState: LiveData<MenuState> = _sweetMenuState

    private val _sweetRadioBtnState: MutableLiveData<MutableList<Boolean>> = MutableLiveData(MutableList(5) {false})
    val sweetRadioBtnState: LiveData<MutableList<Boolean>> = _sweetRadioBtnState

    val selectedSweetText: LiveData<String> = _sweetRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatValue(index) }
    }

    fun setSweetRadioBtnState(selectedIndex: Int) {
        _sweetRadioBtnState.value = updateList(selectedIndex, _sweetRadioBtnState.value!!)
    }

    fun setSweetState(isOpen: MenuState) {
        _sweetMenuState.value = isOpen
    }

    private val _flavorMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val flavorMenuState: LiveData<MenuState> = _flavorMenuState

    private val _flavorRadioBtnState: MutableLiveData<MutableList<Boolean>> = MutableLiveData(MutableList(5) {false})
    val flavorRadioBtnState: LiveData<MutableList<Boolean>> = _flavorRadioBtnState

    val selectedFlavorText: LiveData<String> = _flavorRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatValue(index) }
    }

    fun setFlavorRadioBtnState(selectedIndex: Int) {
        _flavorRadioBtnState.value = updateList(selectedIndex, _flavorRadioBtnState.value!!)
    }

    fun setFlavorState(isOpen: MenuState) {
        _flavorMenuState.value = isOpen
    }

    private val _richMenuState: MutableLiveData<MenuState> = MutableLiveData(null)
    val richMenuState: LiveData<MenuState> = _richMenuState

    private val _richRadioBtnState: MutableLiveData<MutableList<Boolean>> = MutableLiveData(MutableList(5) {false})
    val richRadioBtnState: LiveData<MutableList<Boolean>> = _richRadioBtnState

    val selectedRichText: LiveData<String> = _richRadioBtnState.map { list ->
        buildSelectedText(list) { index ->  formatValue(index) }
    }

    fun setRichRadioBtnState(selectedIndex: Int) {
        _richRadioBtnState.value = updateList(selectedIndex, _richRadioBtnState.value!!)
    }

    fun setRichState(isOpen: MenuState) {
        _richMenuState.value = isOpen
    }

    private fun formatValue(index: Int): String = "${index + 1}.0,  "

    private fun buildSelectedText(list: MutableList<Boolean>, processData: (Int) -> String): String {
        var resultText = ""
        for ((i, isSelected) in list.withIndex()) {
            if (isSelected) {
                resultText += processData(i)
            }
        }
        // 未選択の場合、空文字列を返す
        if (resultText.isEmpty()) return resultText

        return StringUtil.subStringLastSeparator(resultText, ",")
    }

    private val  _roastBtnStateList: MutableLiveData<MutableList<Boolean>> =
        MutableLiveData(MutableList(Constants.roastList.size) {false})
    val roastBtnStateList: LiveData<MutableList<Boolean>> = _roastBtnStateList

    val selectedRoastText: LiveData<String> = _roastBtnStateList.map { list ->
        buildSelectedText(list) { index -> "${Constants.roastList[index]},  "}
    }

    fun setRoastRadioBtnState(selectedIndex: Int) {
        _roastBtnStateList.value = updateList(selectedIndex, _roastBtnStateList.value!!)
    }

    private val  _grindSizeBtnStateList: MutableLiveData<MutableList<Boolean>> =
        MutableLiveData(MutableList(Constants.grindSizeList.size) {false})
    val grindSizeBtnStateList: LiveData<MutableList<Boolean>> = _grindSizeBtnStateList

    val selectedGrindSizeText: LiveData<String> = _grindSizeBtnStateList.map { list ->
        buildSelectedText(list) { index ->
            "${Constants.grindSizeList[index]},  "
        }
    }

    fun setGrindSizeRadioBtnState(selectedIndex: Int) {
        _grindSizeBtnStateList.value = updateList(selectedIndex, _grindSizeBtnStateList.value!!)
    }


    private fun updateList(selectedIndex: Int, currentList: MutableList<Boolean>): MutableList<Boolean> {
        val updatedList = MutableList(currentList.size) { i -> currentList[i]}

        for ((i, state) in currentList.withIndex()) {
            if (i != selectedIndex) continue

            updatedList[i] = !state
        }
        return updatedList
    }

    // 現在開いているメニューのタグを保持する
    private var currentOpenViewTag: String? = null

    fun updateMenuState(clickedView: View, context: Activity) {
        if (notExistCurrentOpenedView()) {
            currentOpenViewTag = clickedView.tag.toString()
            return
        }

        if (isSameAsCurrentOpenedView(clickedView)) {
            currentOpenViewTag = null
        } else {
            // 現在開かれているViewとは別のViewがクリックされたとき
            when (currentOpenViewTag) {
                context.getString(R.string.roast) -> _roastMenuState.value = MenuState.CLOSE
                context.getString(R.string.grind_size) -> _grindSizeMenuState.value = MenuState.CLOSE
                context.getString(R.string.country) -> _countryMenuState.value = MenuState.CLOSE
                context.getString(R.string.tool)   -> _toolMenuState.value = MenuState.CLOSE
                context.getString(R.string.review) -> _ratingMenuState.value = MenuState.CLOSE
                context.getString(R.string.sour)   -> _sourMenuState.value = MenuState.CLOSE
                context.getString(R.string.bitter) -> _bitterMenuState.value = MenuState.CLOSE
                context.getString(R.string.sweet)  -> _sweetMenuState.value = MenuState.CLOSE
                context.getString(R.string.flavor) -> _flavorMenuState.value = MenuState.CLOSE
                context.getString(R.string.rich)   -> _richMenuState.value = MenuState.CLOSE
            }

            currentOpenViewTag = clickedView.tag.toString()
        }
    }

    private fun isSameAsCurrentOpenedView(clickedView: View): Boolean {
        return clickedView.tag.toString() == currentOpenViewTag
    }

    private fun notExistCurrentOpenedView(): Boolean {
        return currentOpenViewTag == null
    }
}