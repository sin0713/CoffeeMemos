package com.withapp.coffeememo.search.recipe.data.cache

import com.withapp.coffeememo.search.recipe.domain.cache.RecipeMemoryCache

/**
 * シングルトン
 * メモリキャッシュクラス
 */
object RecipeMemoryCacheImpl : RecipeMemoryCache {
    private val mCache: MutableMap<String, String> = mutableMapOf()
    val cacheData: Map<String, String>
        get() = mCache

    override fun setData(key: String, value: String) {
        if (key.isEmpty()) return

        mCache[key] = value
    }

    override fun removeData(key: String): String? {
        if (key.isEmpty()) return null

        return mCache.remove(key)
    }

    override fun getData(key: String): String {
        if (key.isEmpty()) return ""

        return mCache[key] ?: ""
    }
}