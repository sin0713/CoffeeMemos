package com.withapp.coffeememo.search.bean.domain.iterator

import com.withapp.coffeememo.search.bean.domain.model.FilterBeanInputData
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.domain.presenter.SearchBeanPresenter
import com.withapp.coffeememo.search.bean.domain.repository.SearchBeanDiskRepository
import com.withapp.coffeememo.search.bean.domain.use_case.FilterBeanUseCase
import javax.inject.Inject

class FilterBeanIterator @Inject constructor() : FilterBeanUseCase {
    @Inject
    lateinit var searchBeanDiskRepository: SearchBeanDiskRepository
    @Inject
    lateinit var searchBeanPresenter: SearchBeanPresenter

    override suspend fun filterBean(inputData: FilterBeanInputData): List<SearchBeanModel> {
        val beans: List<SearchBeanModel> =
            if (inputData.keyWord.isNotEmpty()) {
                 searchBeanDiskRepository.searchBeanByFreeWord(inputData.keyWord)
            } else {
                 searchBeanDiskRepository.getAllBean()
            }

        if (beans.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(beans)
        }

        // 原産地
        var result = getMatchedBeans(beans, inputData.countries) {
                bean, value -> bean.country.contains(value) }
        if (result.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(result)
        }
        // 農園
        result = getMatchedBeans(result, inputData.farms) {
            bean, data -> bean.farm.contains(data)
        }
        if (result.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(result)
        }
        // 地区
        result = getMatchedBeans(result, inputData.districts) {
            bean, data -> bean.district.contains(data)
        }
        if (result.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(result)
        }
        // お店
        result = getMatchedBeans(result, inputData.stores) {
            bean, data -> bean.store.contains(data)
        }
        if (result.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(result)
        }
        // 品種
        result = getMatchedBeans(result, inputData.species) {
            bean, data -> bean.species.contains(data)
        }
        if (result.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(result)
        }
        // 評価
        result = getMatchedBeans(result, inputData.rating) {
            bean, data -> bean.rating == data
        }
        if (result.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(result)
        }
        // 精製法
        result = getMatchedBeans(result, inputData.process) {
            bean, data -> bean.process == data
        }
        if (result.isEmpty()) {
            return searchBeanPresenter.presentFilterResult(result)
        }

        return searchBeanPresenter.presentFilterResult(result)
    }

    private fun <T> getMatchedBeans(
        beans: List<SearchBeanModel>,
        filteringData: List<T>,
        isMatch: (bean: SearchBeanModel, value: T) -> Boolean
    ): List<SearchBeanModel> {
        if (filteringData.isEmpty()) return beans

        val res: MutableList<SearchBeanModel> = mutableListOf()
        for (bean in beans) {
            for (value in filteringData) {
                if (isMatch(bean, value)) {
                    res.add(bean)
                }
            }
        }

        return res
    }
}