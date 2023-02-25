package com.example.coffeememos.viewModel

import androidx.lifecycle.*
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel

class SelectBeanViewModel(
    val beanDao: BeanDao
) : ViewModel() {
    val beanList: LiveData<List<SearchBeanModel>> = beanDao.getCustomBeanByFlow().asLiveData()

}

class SelectBeanViewModelFactory(private val beanDao: BeanDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectBeanViewModel::class.java)) {
            return SelectBeanViewModel(beanDao) as T
        }
        throw IllegalArgumentException("CANNOT_GET_NEWRECIPEVIEWMODEL")
    }
}