package com.example.coffeememos.search.data.cache

import com.example.coffeememos.search.domain.cache.RecipeMemoryCache

/**
 * シングルトン
 * メモリキャッシュクラス
 */
object RecipeMemoryCacheImpl : RecipeMemoryCache {
    private val cacheData: MutableMap<String, String> = mutableMapOf()

    override fun setData(key: String, value: String) {
        if (key.isEmpty()) return

        cacheData[key] = value
    }

    override fun removeData(key: String) {
        if (key.isEmpty()) return

        cacheData.remove(key)
    }

    override fun getData(key: String): String {
        if (key.isEmpty()) return ""

        return cacheData[key] ?: ""
    }
}