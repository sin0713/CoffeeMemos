package com.withapp.coffeememo.favorite.bean.data.repository

import com.withapp.coffeememo.core.data.dao.BeanDao
import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor()
    : StorageRepository{
    @Inject
    lateinit var beanDao: BeanDao

    override suspend fun getFavoriteBeans(): List<Bean> {
        return beanDao.getFavoriteBean()
    }

    override suspend fun deleteFavoriteBean(id: Long) {
        beanDao.deleteFavoriteBean(id)
    }
}