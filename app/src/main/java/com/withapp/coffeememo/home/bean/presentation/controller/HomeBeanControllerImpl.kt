package com.withapp.coffeememo.home.bean.presentation.controller

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanSource
import com.withapp.coffeememo.home.bean.domain.use_case.GetHomeBeanDataUseCase
import com.withapp.coffeememo.home.bean.domain.use_case.UpdateFavoriteBeanUseCase
import javax.inject.Inject

class HomeBeanControllerImpl @Inject constructor()
    : HomeBeanController {
    @Inject
    lateinit var getHomeBeanDataUseCase: GetHomeBeanDataUseCase
    @Inject
    lateinit var updateFavoriteUseCase: UpdateFavoriteBeanUseCase

    override suspend fun getHomeBeanData(): HomeBeanSource {
        return getHomeBeanDataUseCase.handle()
    }

    override suspend fun updateBeanData(
        beanId: Long,
        isFavorite: Boolean
    ): HomeBeanSource {
        updateFavoriteUseCase.handle(beanId, isFavorite)
        return  getHomeBeanDataUseCase.handle()

    }
}