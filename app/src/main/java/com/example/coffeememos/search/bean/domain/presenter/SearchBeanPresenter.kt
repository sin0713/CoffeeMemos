package com.example.coffeememos.search.bean.domain.presenter

import com.example.coffeememos.search.bean.domain.model.FilterBeanInputData
import com.example.coffeememos.search.bean.domain.model.FilterBeanOutputData
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel

interface SearchBeanPresenter {
    fun presentAllBeans(beans: List<SearchBeanModel>): List<SearchBeanModel>
    fun presentFreeWordSearchRes(beans: List<SearchBeanModel>): List<SearchBeanModel>
    fun presentFilterResult(beans: List<SearchBeanModel>): List<SearchBeanModel>
    fun presentFilterOutputData(inputData: FilterBeanInputData): FilterBeanOutputData
}