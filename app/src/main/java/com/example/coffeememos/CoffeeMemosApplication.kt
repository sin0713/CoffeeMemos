package com.example.coffeememos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeMemosApplication : Application() {
    val database :CoffeeMemosDatabase by lazy {
        CoffeeMemosDatabase.getDatabase(this)
    }
}