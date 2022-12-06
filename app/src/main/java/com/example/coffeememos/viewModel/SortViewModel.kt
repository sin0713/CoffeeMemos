package com.example.coffeememos.viewModel

import androidx.lifecycle.ViewModel
import com.example.coffeememos.search.SortItem
import com.example.coffeememos.search.SortType

class SortViewModel : ViewModel() {
    // listview data
    lateinit var sortItemList: List<SortItem>

    fun setSortItemList(sortName: String) {
        sortItemList = makeSortData(sortName)
    }

    private fun makeSortData(sortName: String): List<SortItem> {
        val result: MutableList<SortItem> = mutableListOf()

        for (sortType in SortType.values()) {
            val isSelected: Boolean = sortType.getSortName() == sortName
            result.add(SortItem(sortType, isSelected))
        }
        return result
    }

    fun changeData(index: Int) {
        for ((i, item) in sortItemList.withIndex()) {
            item.isSelected = i == index
        }
    }

    fun getSelectedSortType(): SortType {
        for (sortItem in sortItemList) {
            if (sortItem.isSelected) {
                return sortItem.type
            }
        }
        // 見つからなかったとき(デフォルト値のようなもの)
        return SortType.NEW
    }
}